package edu.stevens.cs548.clinic.domain;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PatientDAO implements IPatientDAO {

	private EntityManager em;
	private TreatmentDAO treatmentDAO;
	
	public PatientDAO(EntityManager em) {
		this.em = em;
		this.treatmentDAO = new TreatmentDAO(em);
	}

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(PatientDAO.class.getCanonicalName());

	@Override
	public long addPatient(Patient patient) throws PatientExn {
		long pid = patient.getPatientId();
		Query query = em.createNamedQuery("CountPatientByPatientID").setParameter("pid", pid);
		Long numExisting = (Long) query.getSingleResult();
		if (numExisting < 1) {
			em.persist(patient);
			patient.setTreatmentDAO(treatmentDAO);
			em.flush();
			Query q = em.createNamedQuery("getPatPK").setParameter("pid", pid);
			return (long)q.getSingleResult();
			
		} else {
			throw new PatientExn("Insertion: Patient with patient id (" + pid + ") already exists.");
		}
	}

	@Override
	public Patient getPatient(long id) throws PatientExn {
		Patient p = em.find(Patient.class,id);
		if(p!=null){
			p.setTreatmentDAO(treatmentDAO);
			return p;
		}else throw new PatientExn("Patient with id "+id+" do not exist");
	}

	@Override
	public Patient getPatientByPatientId(long pid) throws PatientExn {
		Query q = em.createNamedQuery("SearchPatientByPatientID").setParameter("pid", pid);
		Patient p = (Patient)q.getSingleResult();
		if(p!=null){
			p.setTreatmentDAO(treatmentDAO);
			return p;
		}else throw new PatientExn("Patient with patient id "+pid+" do not exist");
	}
	
	@Override
	public void deletePatients() {
		Query update = em.createNamedQuery("RemoveAllPatients");
		update.executeUpdate();
	}

}
