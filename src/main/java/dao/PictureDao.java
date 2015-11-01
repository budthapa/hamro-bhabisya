package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.Picture;
import models.Project;
import ninja.jpa.UnitOfWork;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public class PictureDao implements IBaseDao{
	@Inject
	Provider<EntityManager> entityManagerProvider;
	
	@SuppressWarnings("unchecked")
	@Override
	@UnitOfWork
	public <T> List<T> findAll() {
		EntityManager em=entityManagerProvider.get();
		Query q=em.createQuery("SELECT x FROM Picture x ORDER BY x.id DESC");
		List<Picture> list=q.setMaxResults(4).getResultList();
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
		EntityManager em=entityManagerProvider.get();
		em.persist(object);
		return 0;
	}

	@Override
	@Transactional
	public <T> boolean saveOrUpdate(T object) {
		EntityManager em=entityManagerProvider.get();
		Picture pic=(Picture)object;
		//this is inserting new value instead of updating why
//		System.out.println("pic project id ; "+ pic.getProject().getId());
//		em.merge(object);
//		return true;
		Query q=em.createQuery("UPDATE Picture x SET x.pictureName = :param WHERE x.project = :paramObject");
		q.setParameter("param", pic.getPictureName()).setParameter("paramObject", pic.getProject()).executeUpdate();
		return true;
	}

	@UnitOfWork
	public Picture getLatestProjectPictureFrontPage(Project project) {
		EntityManager em=entityManagerProvider.get();
		Query q=em.createQuery("SELECT x FROM Picture x WHERE x.project = :param");
		Picture picture=null;
		try{
			picture=(Picture) q.setParameter("param", project).getSingleResult();
		}catch(Exception e){
			
		}
		return picture;
	}

}
