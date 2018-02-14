package edu.stevens.cs548.clinic.service.ejb;

import javax.jms.JMSException;

import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.TreatmentNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientNotFoundExn;

public interface IProviderService {

		public class ProviderServiceExn extends Exception{
			private static final long serialVersionUID = 1L;
			public ProviderServiceExn (String m) {
				super(m);
			}
		}
		
		public class ProviderNotFoundExn extends ProviderServiceExn {
			private static final long serialVersionUID = 1L;
			public ProviderNotFoundExn (String m) {
				super(m);
			}
		}
		
		public long addProvider(ProviderDto pd) throws ProviderServiceExn;
		public ProviderDto getProvider(long id) throws ProviderNotFoundExn;
		public ProviderDto getProviderByPId(long pid) throws ProviderNotFoundExn;
		public TreatmentDto getTreatment(long id, long tid) throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn;
		public long addTreatmnet(TreatmentDto td, long prid, long paid) throws PatientNotFoundExn, ProviderServiceExn;
		public String siteInfo();
}
