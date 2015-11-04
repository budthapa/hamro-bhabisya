package dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.Picture;
import models.Project;
import models.User;
import ninja.jpa.UnitOfWork;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public class PictureDao implements IBaseDao{
	Logger log=Logger.getLogger(PictureDao.class.getName());
	
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
		EntityManager em=entityManagerProvider.get();
		em.detach(object);
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
		Picture picture=(Picture)object;
		Picture pic=em.find(Picture.class, picture.getId());
		em.merge(pic);					
		/*if(pic.getProject().equals("Project")){			
			Query q=em.createQuery("UPDATE Picture x SET x.pictureName = :param WHERE x.project = :paramObject");
			q.setParameter("param", pic.getPictureName()).setParameter("paramObject", pic.getProject()).executeUpdate();
		}else{
		}*/
		
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

	@UnitOfWork
	public Picture getUserPicture(User user) {
		EntityManager em=entityManagerProvider.get();
		Query q=em.createQuery("SELECT x FROM Picture x WHERE x.user = :userParam");
		Picture picture=null;
		try{
			picture=(Picture) q.setParameter("userParam", user).getSingleResult();			
		}catch (Exception e){
			log.warning("Picture not found for user id : "+user.getId());
		}
		return picture;
	}

}
