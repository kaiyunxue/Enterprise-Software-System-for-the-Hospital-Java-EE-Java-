package edu.stevens.cs548.clinic.domain;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;



public class ProviderDAO implements IProviderDAO {

	private EntityManager em;
	private TreatmentDAO treatmentDAO;

	public ProviderDAO(EntityManager em) {
		this.em = em;
		this.treatmentDAO = new TreatmentDAO(em);
	}

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(ProviderDAO.class.getCanonicalName());

	@Override
	public long addProvider(Provider p) throws ProviderExn {
		long pid = p.getProvider_id();
		Query query = em.createNamedQuery("CountProviderByProviderId").setParameter("pid", pid);
		Long numExisting = (Long) query.getSingleResult();
		if (numExisting < 1) {
			em.persist(p);
			p.setTreatmentDAO(treatmentDAO);
			em.flush();
			Query q = em.createNamedQuery("getProPK").setParameter("pid", pid);
			return (long)q.getSingleResult();

		} else {
			throw new ProviderExn("Insertion: Provider with provider id (" + pid + ") already exists.");
		}
	}

	@Override
	public Provider getProviderByProviderId(long pid) throws ProviderExn {
		Query q = em.createNamedQuery("SearchProviderByProviderId").setParameter("pid", pid);
		Provider p = (Provider)q.getSingleResult();
		if(p!=null){
			p.setTreatmentDAO(treatmentDAO);
			return p;
		}else throw new ProviderExn("provider with provider id "+pid+" do not exist");	}

//retriProvider
	@Override
	public Provider getProvider(long id) throws ProviderExn {
		Provider p = em.find(Provider.class,id);
		if(p!=null){
			p.setTreatmentDAO(treatmentDAO);
			return p;
		}else throw new ProviderExn("provider with id "+id+" do not exist");
	}

}
