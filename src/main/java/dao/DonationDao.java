package dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.Donation;
import ninja.jpa.UnitOfWork;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public class DonationDao implements IBaseDao{
	Logger log=Logger.getLogger(DonationDao.class.getName());
	@Inject
	Provider<EntityManager> entityManagerProvider;
	
	@Override
	@UnitOfWork
	@SuppressWarnings("unchecked")
	public <T> List<T> findAll() {
		EntityManager em=entityManagerProvider.get();
		Query q=em.createQuery("SELECT x FROM Donation x ORDER BY x.id DESC");
		List<Donation> list=q.getResultList();
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
		em.persist((Donation)object);
		return 0;
	}

	@Override
	@Transactional
	public <T> boolean saveOrUpdate(T object) {
		EntityManager em=entityManagerProvider.get();
		Donation donation=(Donation)object;
		em.merge(donation);
		return true;
	}

	@UnitOfWork
	public Donation getDonation(int id) {
		EntityManager em=entityManagerProvider.get();
		Query q=em.createQuery("SELECT x FROM Donation x where x.id = :idParam");
		Donation donation=(Donation) q.setParameter("idParam", id).getSingleResult();
		return donation;
	}

	@SuppressWarnings("unchecked")
	@UnitOfWork
	public List<Donation> getLatestDonationFrontPage() {
		EntityManager em=entityManagerProvider.get();
		Query q=em.createQuery("SELECT x FROM Donation x ORDER BY x.id DESC");
		List<Donation> list=q.setMaxResults(5).getResultList();
		return list;
	}
	
	@UnitOfWork
	public long sumTotalDonation() {
		EntityManager em=entityManagerProvider.get();
		Query q=em.createQuery("SELECT SUM(amount) FROM Donation d");
		long sum=0;
		try{
			sum=(long) q.getSingleResult();
		}catch(Exception e){
			log.info("No Donation found");
		}
		return sum;
	}

}
