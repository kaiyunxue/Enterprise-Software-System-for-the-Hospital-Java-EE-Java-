package edu.stevens.cs548.clinic.service.web.soap;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jws.WebService;

import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.TreatmentNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;

@WebService(
		endpointInterface = "edu.stevens.cs548.clinic.service.web.soap.IProviderWebService", 
		targetNamespace = "http://cs548.stevens.edu/clinic/service/web/soap", 
		serviceName = "ProviderWebService", portName = "ProviderWebPort")

public class ProviderWebService implements IProviderWebService {

	@Inject
	IProviderServiceLocal service;
	
	@Override
	public long addProvider(ProviderDto dto) throws ProviderServiceExn {
		return service.addProvider(dto);
	}

	@Override
	public ProviderDto getProvider(long id) throws ProviderNotFoundExn {
		return service.getProvider(id);
	}

	@Override
	public ProviderDto getProviderByPId(long pid) throws ProviderNotFoundExn {
		return service.getProviderByPId(pid);
	}

	@Override
	public TreatmentDto getTreatment(long id, long tid)
			throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn {
		return service.getTreatment(id, tid);
	}

	@Override
	public long addTreatment(TreatmentDto td, long prid, long paid) throws PatientNotFoundExn, ProviderServiceExn {
		return service.addTreatmnet(td, prid, paid);
	}

	@Override
	public String siteInfo() {
		return service.siteInfo();
	}

}
