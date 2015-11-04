package dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.Project;
import ninja.jpa.UnitOfWork;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public class ProjectDao implements IBaseDao{
	Logger log=Logger.getLogger(ProjectDao.class.getName());
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
		entityManager.persist(project);
		
		return project.getId();
	}

	@Override
	@Transactional
	public <T> boolean saveOrUpdate(T object) {
		EntityManager em=entityManagerProvider.get();
		em.merge(object);
		return true;
	}

	@UnitOfWork
	public Project getLatestProjectFrontPage() {
		EntityManager em=entityManagerProvider.get();
		Query query=em.createQuery("SELECT x FROM Project x WHERE project_category='Project' ORDER BY x.id DESC");
		Project project = null;
		try{
			project=(Project) query.setMaxResults(1).getSingleResult();			
		}catch(Exception e){
			log.info("Project was not found while running the app for the first time");
		}
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
		Project project = null;
		try{
			project=(Project) q.setMaxResults(1).getSingleResult();
		}catch(Exception e){
			log.info("News and events not found while running the app for first time in class "+ProjectDao.class.getName());
		}
		return project;
	}

	@UnitOfWork
	public long countTotalProject() {
		EntityManager em=entityManagerProvider.get();
		Query q=em.createQuery("SELECT COUNT(*) FROM Project p");
		long count=(long) q.getSingleResult();
		return count;
	}

}
