package dao;

import java.util.List;

import javax.persistence.EntityManager;

import ninja.jpa.UnitOfWork;
import models.ContactUs;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public class ContactUsDao implements IBaseDao{
	@Inject
	Provider<EntityManager> entityManagerProvider;
	
	@SuppressWarnings("unchecked")
	@Override
	@UnitOfWork
	public <T> List<T> findAll() {
		EntityManager entityManager=entityManagerProvider.get();
		List<ContactUs> list=entityManager.createQuery("select x from ContactUs x", ContactUs.class).getResultList();
		return (List<T>) list;
	}

	@Override
	public <T> boolean delete(T object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Transactional
	public <T> boolean save(T object) {
		EntityManager entityManager=entityManagerProvider.get();
		entityManager.persist(object);
		return true;
	}

	@Override
	public <T> boolean saveOrUpdate(T object) {
		// TODO Auto-generated method stub
		return false;
	}

}
