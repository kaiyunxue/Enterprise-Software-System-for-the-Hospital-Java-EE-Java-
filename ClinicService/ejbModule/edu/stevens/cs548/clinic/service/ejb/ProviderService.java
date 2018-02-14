package edu.stevens.cs548.clinic.service.ejb;

import edu.stevens.cs548.clinic.domain.IPatientDAO;
import edu.stevens.cs548.clinic.domain.IProviderDAO;
import edu.stevens.cs548.clinic.domain.IProviderFactory;
import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.ProviderDAO;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import edu.stevens.cs548.clinic.domain.RadDate;
import edu.stevens.cs548.clinic.domain.Radiology;
import edu.stevens.cs548.clinic.domain.Treatment;
import edu.stevens.cs548.clinic.domain.TreatmentFactory;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.cs548.clinic.domain.ITreatmentFactory;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientDAO;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentExporter;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientNotFoundExn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.persistence.EntityManager;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;

/**
 * Session Bean implementation class ProviderService
 */
@Stateless(name = "ProviderServiceBean")
public class ProviderService implements IProviderService, IProviderServiceLocal, IProviderServiceRemote {

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(ProviderService.class.getCanonicalName());
	
	@Inject
	@ClinicDomain
	EntityManager em;
	
	private IProviderFactory providerfactory;
	private ProviderDtoFactory providerdtofactory;
	private ITreatmentFactory treatmentfactory;
	private IProviderDAO providerDAO;
	private IPatientDAO patientDAO;
    /**
     * Default constructor. 
     */
    public ProviderService() {
    	providerfactory = new ProviderFactory();
    	providerdtofactory = new ProviderDtoFactory();
    	treatmentfactory = new TreatmentFactory();
    }
    
    @PostConstruct
	public void init(){
		providerDAO = new ProviderDAO(em);
		patientDAO = new PatientDAO(em);
	}
    
    @Resource(mappedName="jms/TreatmentPool")
    private ConnectionFactory treatmentConnFactory;
    
    @Resource(mappedName="jms/Treatment")
    private Topic treatmentTopic;
    
    
	/**
     * @see IProviderService#addProvider(ProviderDto)
     */
    @Override
    public long addProvider(ProviderDto pd) throws ProviderServiceExn {
        try{
        	Provider provider = providerfactory.creat(pd.getProviderId(), pd.getName(), pd.getSpec());
        	providerDAO.addProvider(provider);
        	return provider.getId();
        }catch(ProviderExn e){
        	throw new ProviderServiceExn(e.toString());
        }
    }

	/**
     * @throws ProviderServiceExn 
	 * @throws PatientNotFoundExn 
	 * @see IProviderService#addTreatmnet(TreatmentDto, long)
     */
    @Override
    public long addTreatmnet(TreatmentDto td, long prid, long paid) throws ProviderServiceExn, PatientNotFoundExn {
    	Connection treatmentConn = null;
    	try{
    		treatmentConn = treatmentConnFactory.createConnection();
    		Patient pa = patientDAO.getPatient(paid);
        	Treatment t;
           	Provider pr = providerDAO.getProvider(prid);
        	Session session = treatmentConn.createSession(true,Session.AUTO_ACKNOWLEDGE);
        	MessageProducer producer = session.createProducer(treatmentTopic);
        	ObjectMessage message = session.createObjectMessage();
        	
        	if(td.getDrugTreatment()!=null){ 
        		
        		t = treatmentfactory.createDrugTreatment(td.getDiagnosis(), 
        				td.getDrugTreatment().getName(), td.getDrugTreatment().getDosage());
            	t.setPatient(pa);
            	t.setProvider(pr);
            	TreatmentDto treatment = new TreatmentDtoFactory().createTreatmentDto(t);
                long tid = pr.addTreatment(pa, t);
                treatment.setId(tid);
                DrugTreatmentType drugTreatment = new DrugTreatmentType();
                drugTreatment.setDosage(td.getDrugTreatment().getDosage());
                drugTreatment.setName(td.getDrugTreatment().getName());
                treatment.setDrugTreatment(drugTreatment);
                
            	message.setObject(treatment);
            	message.setStringProperty("TreatmentType", "DrugTreatmentRecord");
            	producer.send(message);
            	return tid;
        		}
        	else if(td.getRadiology()!=null){
        		RadiologyType radiology = new RadiologyType();
        		List<RadDate> dates = new ArrayList<RadDate>();
        		for(Date d:td.getRadiology().getDate()){
        			RadDate rd = new RadDate();
        			rd.setDate(d);
        			dates.add(rd);
        			radiology.getDate().add(d);
        		}
        		t = treatmentfactory.createRadiology(td.getDiagnosis(), dates);
            	t.setPatient(pa);
            	t.setProvider(pr);
            	TreatmentDto treatment = new TreatmentDtoFactory().createTreatmentDto(t);
                long tid = pr.addTreatment(pa, t);
                treatment.setId(tid);
                treatment.setRadiology(radiology);
            	message.setObject(treatment);
            	producer.send(message);
            	return tid;
        	}
        	else if(td.getSurgery()!=null) {
        		
        		t = treatmentfactory.creatSurgery(td.getDiagnosis(), td.getSurgery().getDate());
            	t.setPatient(pa);
            	t.setProvider(pr);
            	TreatmentDto treatment = new TreatmentDtoFactory().createTreatmentDto(t);
            	SurgeryType surgery = new SurgeryType();
            	surgery.setDate(td.getSurgery().getDate());
                long tid = pr.addTreatment(pa, t);
                treatment.setId(tid);
                treatment.setSurgery(surgery);
            	message.setObject(treatment);
            	producer.send(message);
            	return tid;
        	}
        	else 
        		throw new ProviderServiceExn("undefined treatment type");
        }catch(PatientExn e){
        	throw new PatientNotFoundExn(e.toString());
        }catch(ProviderExn e){
        	throw new ProviderNotFoundExn(e.toString());
        }catch(JMSException e){
        	logger.severe("----------------JMS Error:"+e);
        	throw new ProviderNotFoundExn(e.toString());
        }finally{
        	try{
        		if(treatmentConn != null)
        			treatmentConn.close();
    		}catch (JMSException e) {
				logger.severe("Error closing JMS connection:" + e);
			}
        }
    }
	/**
     * @see IProviderService#getProvider(long)
     */
    public ProviderDto getProvider(long id) throws ProviderNotFoundExn {
    	try{
        	Provider provider = providerDAO.getProvider(id);
        	ProviderDto providerDto = providerdtofactory.createProviderDto(provider);
        	return providerDto;
        }catch(ProviderExn e){
        	throw new ProviderNotFoundExn(e.toString());
        }
    }
	/**
     * @see IProviderService#getProviderByPId(long)
     */
	public ProviderDto getProviderByPId(long NPI) throws ProviderNotFoundExn {
		try {
			Provider provider = providerDAO.getProviderByProviderId(NPI);
			ProviderDto providerDto = providerdtofactory.createProviderDto(provider);
			return providerDto;
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		}
	}


	/**
     * @throws ProviderServiceExn 
	 * @see IProviderService#getTreatment(long, long)
     */
    public TreatmentDto getTreatment(long id, long tid) throws ProviderServiceExn {
		try {
			Provider provider = providerDAO.getProvider(id);
			TreatmentExporter visitor = new TreatmentExporter();
			return provider.exportTreatment(tid, visitor);
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			throw new ProviderServiceExn(e.toString());
		}
    }

    @Resource(name="SiteInfo-pro")
	private String siteInformation;
    
	/**
     * @see IProviderService#siteInfo()
     */
    public String siteInfo() {
    	return siteInformation;
    }
}
