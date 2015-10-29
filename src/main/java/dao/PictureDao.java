package dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public class PictureDao implements IBaseDao{
	@Inject
	Provider<EntityManager> entityManagerProvider;
	
	@Override
	public <T> List<T> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
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
	public <T> boolean saveOrUpdate(T object) {
		// TODO Auto-generated method stub
		return false;
	}

}
