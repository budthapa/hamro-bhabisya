package dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.ResetPassword;
import ninja.jpa.UnitOfWork;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public class ResetPasswordDao implements IBaseDao{
	Logger log=Logger.getLogger(ResetPasswordDao.class.getName());
	@Inject
	Provider<EntityManager> entityManagerProvider;
	
	@Override
	public <T> List<T> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@UnitOfWork
	public <T> boolean delete(T object) {
		EntityManager em=entityManagerProvider.get();
		em.detach(object);
		return true;
	}

	@Override
	@Transactional
	public <T> int save(T object) {
		EntityManager em=entityManagerProvider.get();
		ResetPassword resetPassword=(ResetPassword)object;
		em.persist(resetPassword);
		return resetPassword.getId();
	}

	@Override
	public <T> boolean saveOrUpdate(T object) {
		// TODO Auto-generated method stub
		return false;
	}

	@UnitOfWork
	public ResetPassword getToken(String resetToken) {
		EntityManager em=entityManagerProvider.get();
		Query q=em.createQuery("SELECT x FROM ResetPassword x WHERE x.randomToken = :tokenParam");
		ResetPassword rs=null;
		try{
			rs=(ResetPassword) q.setParameter("tokenParam", resetToken).getSingleResult();			
		}catch(Exception e){
			log.severe("Invalid password reset token requested");
		}
		return rs;
	}

}
