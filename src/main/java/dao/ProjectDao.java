package dao;

import java.util.List;

import javax.persistence.EntityManager;

import models.Project;
import ninja.jpa.UnitOfWork;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public class ProjectDao implements IBaseDao{
	@Inject
	Provider<EntityManager> entityManagerProvider;

	@Override
	@UnitOfWork
	public <T> List<T> findAll() {
		// TODO Auto-generated method stub
		return null;
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
		EntityManager entityManager=entityManagerProvider.get();
		Project project=(Project)object;
		entityManager.persist(object);
		
		return project.getId();
	}

	@Override
	@Transactional
	public <T> boolean saveOrUpdate(T object) {
		// TODO Auto-generated method stub
		return false;
	}

}
