/**
 * Copyright (C) 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.Login;
import models.User;
import ninja.jpa.UnitOfWork;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import dto.UserDto;

public class UserDao implements IBaseDao {
	Logger log = Logger.getLogger(UserDao.class.getName());
	@Inject
	Provider<EntityManager> entityManagerProvider;

	@UnitOfWork
	public UserDto isUserAndPasswordValid(String email) {
		EntityManager entityManager = entityManagerProvider.get();
		User user = null;
		try {
			Query q = entityManager.createQuery("SELECT x FROM User x WHERE x.email = :emailParam");
			user = (User) q.setParameter("emailParam", email).getSingleResult();
			if (user != null) {
				Login login = null;
				try {
					Query loginQuery = entityManager.createQuery("SELECT x FROM Login x WHERE x.user = :userParam");
					loginQuery.setParameter("userParam", user);
					login = (Login) loginQuery.getSingleResult();
					UserDto userDto = new UserDto();
					userDto.setId(login.getUser().getId());
					userDto.setPassword(login.getPassword());
					userDto.setActive(login.isActive());
					userDto.setAdmin(login.isAdmin());
					userDto.setLoginId(login.getId());
					return userDto;
				} catch (Exception e) {
					log.warning("Login not found for email " + user.getEmail());
				}
			}
		} catch (Exception e) {
			log.warning("User not found for submitted email : " + email);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@UnitOfWork
	public <T> List<T> findAll() {
		EntityManager em = entityManagerProvider.get();
		Query q = em.createQuery("SELECT x FROM User x");
		List<User> list = q.getResultList();
		return (List<T>) list;
	}

	@Override
	@Transactional
	public <T> boolean delete(T object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Transactional
	public <T> int save(T object) {
		EntityManager em = entityManagerProvider.get();
		User user = null;
		try {
			user = (User) object;
			em.persist(user);
			return user.getId();

		} catch (Exception e) {
			log.warning("Duplicate entry for '" + user.getEmail() + "'");
		}
		return 0;
	}

	@Override
	@Transactional
	public <T> boolean saveOrUpdate(T object) {
		EntityManager em = entityManagerProvider.get();
		User user = (User) object;
		em.merge(user);
		return true;
	}

	@UnitOfWork
	public User getUser(int userId) {
		EntityManager em = entityManagerProvider.get();
		Query q = em.createQuery("SELECT x FROM User x WHERE x.id = :idParam");
		User user = (User) q.setParameter("idParam", userId).getSingleResult();
		return user;
	}

	@SuppressWarnings("unchecked")
	@UnitOfWork
	public List<User> findUserWithoutLoginCredentials() {
		EntityManager em=entityManagerProvider.get();
		Query q=em.createQuery("SELECT x FROM User x WHERE x.hasLoginCredentials=0");
		List<User> list=q.getResultList();
		return list;
	}
	
	@Transactional
	public boolean newLoginCredentials(Login login) {
		EntityManager em=entityManagerProvider.get();
		em.persist(login);
		return true;
	}

	@Transactional
	public void updateLoginCredentialsToUser(User user) {
		EntityManager em=entityManagerProvider.get();
		em.merge(user);
	}

	@Transactional
	public void updatePassword(Login login) {
		
		EntityManager em=entityManagerProvider.get();
		em.merge(login);
	}
}
