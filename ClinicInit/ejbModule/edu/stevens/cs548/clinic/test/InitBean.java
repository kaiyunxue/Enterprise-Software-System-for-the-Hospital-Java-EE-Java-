package edu.stevens.cs548.clinic.test;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.PatientDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceLocal;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;

/**
 * Session Bean implementation class TestBean
 */
@Singleton
@LocalBean
@Startup
public class InitBean {

	private static Logger logger = Logger.getLogger(InitBean.class.getCanonicalName());
	
	private static void info(String	m){
	    logger.info(m);
	}
	
	PatientDtoFactory patientDtoFactory;
	ProviderDtoFactory providerDtoFactory;
	TreatmentDtoFactory treatmentDtoFactory;

	public InitBean() {
		patientDtoFactory = new PatientDtoFactory();
		providerDtoFactory = new ProviderDtoFactory();
		treatmentDtoFactory = new TreatmentDtoFactory();
	}
    
	@Inject
	IPatientServiceLocal patientService;
	@Inject
	IProviderServiceLocal providerService;

	@PostConstruct
	private void init() {
/*		info("----------------------------------Connected----------------------------------");

		try
		{
			Calendar calendar = Calendar.getInstance();
			calendar.set(2000, 1, 1);
//			info("----------------------------------step 1: a patient----------------------------------");
//			PatientDto patientDto_1=patientDtoFactory.createPatientDto();
//			patientDto_1.setAge(17);
//			patientDto_1.setDob(calendar.getTime());
//			patientDto_1.setPatientId(123341);
//			patientDto_1.setName("patient1");
//			long ptid_1 = patientService.addPatient(patientDto_1);
//			info("patient with patnientId:"+ptid_1+" has been  to database");
//			
//			info("----------------------------------step 2:search the patient----------------------------------");
//			PatientDto patientDto_2 = patientService.getPatient(ptid_1);
//			info("get patient with pid:"+patientDto_2.getPatientId()+" and name:"+patientDto_2.getName()+" success");
//			PatientDto patientDto_3 =  patientService.getPatientByPatId(123341);
//			info("get patient with id:"+patientDto_3.getId()+" and name:"+patientDto_3.getName()+" success");
			
//			info("----------------------------------step 3: a provider----------------------------------");
//			ProviderDto providerDto_1 = providerDtoFactory.createProviderDto();
//			providerDto_1.setName("provider2");
//			providerDto_1.setSpec("spec");
//			providerDto_1.setProviderId(11322332);
//			long prid1 = providerService.addProvider(providerDto_1);
//			info("provider with providerId:"+prid1+" has been  to database");
//			
//			info("----------------------------------step 4:get the provider----------------------------------");
//			ProviderDto providerDto_2 = providerService.getProvider(52);
//			info("get provider with pid:"+providerDto_2.getProviderId()+" and name:"+providerDto_2.getName()+" success");
//			ProviderDto prd3 = providerService.getProviderByPId(11322332);
//			info("get provider with id:"+prd3.getId()+" and name:"+prd3.getName()+" success");
			
//			info("----------------------------------step 5: a Treatment----------------------------------");
//			TreatmentDto treatmentDto_1 = treatmentDtoFactory.createSurgeryDto();
//			treatmentDto_1.setDiagnosis("diagnosis222");
//			treatmentDto_1.getSurgery().setDate(new Date());
//			long id1 = providerService.addTreatmnet(treatmentDto_1, 5, 4);
//			logger.info("----id1:"+id1);
//			
//			TreatmentDto treatmentDto_2 = treatmentDtoFactory.createRadiologyDto();
//			treatmentDto_2.setDiagnosis("diagnosis333");
//			treatmentDto_2.getRadiology().getDate().add(new Date());
//			treatmentDto_2.getRadiology().getDate().add(new Date());
//			treatmentDto_2.getRadiology().getDate().add(new Date());
//			long id2 = providerService.addTreatmnet(treatmentDto_2, 5, 4);
//			logger.info("----id2:"+id2);
			
//			info("----------------------------------step 6:get treatment----------------------------------");
//			TreatmentDto treatmentDto_2 = patientService.getTreatment(51, 53);
//			info("get treatment "+53+" from patient "+51+" success");
//			TreatmentDto treatmentDto_3 = providerService.getTreatment(52, 53);
//			info("get treatment "+53+" from provider "+52+" success");	
//			info(treatmentDto_2.getDrugTreatment().getDosage()+treatmentDto_2.getDrugTreatment().getName());
			info("----------------------initial end-------------------------");
//			
//		} catch (PatientServiceExn e) {
//			info(e.getMessage());
//		} catch (ProviderServiceExn e){
//			info(e.getMessage());
//		}
//			
//	}
*/
	}
}
