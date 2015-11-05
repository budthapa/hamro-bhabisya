package dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.FinancialReport;
import ninja.jpa.UnitOfWork;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public class FinancialReportDao implements IBaseDao{
	Logger log=Logger.getLogger(FinancialReportDao.class.getName());
	@Inject
	Provider<EntityManager> entityManagerProvider;
	
	@Override
	@UnitOfWork
	@SuppressWarnings("unchecked")
	public <T> List<T> findAll() {
		EntityManager em=entityManagerProvider.get();
		Query q=null;
		try{
			q=em.createQuery("SELECT x FROM FinancialReport x ORDER BY id DESC");
		}catch(Exception e){
			log.info("Report not found");
		}
		List<FinancialReport> list=q.getResultList();
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
		FinancialReport project=(FinancialReport)object;
		em.persist(object);
		return project.getId();
	}

	@Override
	@Transactional
	public <T> boolean saveOrUpdate(T object) {
		// TODO Auto-generated method stub
		return false;
	}

}
