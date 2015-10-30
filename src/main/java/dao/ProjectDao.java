package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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
	
	@SuppressWarnings("unchecked")
	@UnitOfWork
	public List<Project> findAllProject() {
		EntityManager em=entityManagerProvider.get();
		List<Project> list=em.createQuery("SELECT x FROM Project x WHERE x.projectCategory='Project' ORDER BY x.id DESC").getResultList();
		return list;
	}

	@Override
	@Transactional
	public <T> boolean delete(T object) {
		EntityManager em=entityManagerProvider.get();
		em.remove(object);
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
		EntityManager em=entityManagerProvider.get();
		Project project=(Project)object;
		em.merge(project);
		return false;
	}

	@UnitOfWork
	public Project getLatestProjectFrontPage() {
		EntityManager em=entityManagerProvider.get();
		Query query=em.createQuery("SELECT x FROM Project x WHERE project_category='Project' ORDER BY x.id DESC");
		Project project=(Project) query.setMaxResults(1).getSingleResult();
		return project;
	}

	@UnitOfWork
	public Project getProject(int projectId) {
		EntityManager em=entityManagerProvider.get();
		Query q=em.createQuery("SELECT x FROM Project x WHERE x.id = :param");
		Project project=(Project) q.setParameter("param", projectId).getSingleResult();
		return project;
	}

	@SuppressWarnings("unchecked")
	@UnitOfWork
	public List<Project> findAllNewsEvent() {
		EntityManager em=entityManagerProvider.get();
		List<Project> list=em.createQuery("SELECT x FROM Project x WHERE x.projectCategory='News & Events' ORDER BY x.id DESC").getResultList();
		return list;
	}

	@UnitOfWork
	public Project getLatestNewsEventFrontPage() {
		EntityManager em=entityManagerProvider.get();
		Query q=em.createQuery("SELECT x FROM Project x WHERE x.projectCategory = 'News & Events' ORDER BY x.id DESC");
		Project project=(Project) q.setMaxResults(1).getSingleResult();
		return project;
	}

}
