package edu.stevens.cs548.clinic.service.representations;

import java.util.Date;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;

import edu.stevens.cs548.clinic.domain.DrugTreatment;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.web.rest.data.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.web.rest.data.ObjectFactory;
import edu.stevens.cs548.clinic.service.web.rest.data.RadiologyType;
import edu.stevens.cs548.clinic.service.web.rest.data.SurgeryType;
import edu.stevens.cs548.clinic.service.web.rest.data.TreatmentType;
import edu.stevens.cs548.clinic.service.web.rest.data.dap.LinkType;

@XmlRootElement
public class TreatmentRepresentation extends TreatmentType {
	
	private ObjectFactory repFactory = new ObjectFactory();
	
	public LinkType getLinkPatient() {
		return this.getPatient();
	}
	
	public LinkType getLinkProvider() {
		return this.getProvider();
	}
	
	public static LinkType getTreatmentLink(long tid, UriInfo uriInfo) {
		UriBuilder ub = uriInfo.getBaseUriBuilder();
		ub.path("treatment");
		UriBuilder ubTreatment = ub.clone().path("{tid}");
		String treatmentURI = ubTreatment.build(Long.toString(tid)).toString();
	
		LinkType link = new LinkType();
		link.setUrl(treatmentURI);
		link.setRelation(Representation.RELATION_TREATMENT);
		link.setMediaType(Representation.MEDIA_TYPE);
		return link;
	}
	
	private TreatmentDtoFactory treatmentDtoFactory;
	
	public TreatmentRepresentation() {
		super();
		treatmentDtoFactory = new TreatmentDtoFactory();
	}
	
	public TreatmentRepresentation (TreatmentDto dto, UriInfo uriInfo) {
		this();
		this.id = getTreatmentLink(dto.getId(), uriInfo);
		this.patient =  PatientRepresentation.getPatientLink(dto.getPatient(), uriInfo);
		this.provider = ProviderRepresentation.getProviderLink(dto.getPatient(), uriInfo);		
		this.diagnosis = dto.getDiagnosis();
		
		if (dto.getDrugTreatment() != null) {
			DrugTreatmentType drugTreatment= new DrugTreatmentType();
			drugTreatment.setName(dto.getDrugTreatment().getName());
			drugTreatment.setDosage(dto.getDrugTreatment().getDosage());	
			this.setDrugTreatment(drugTreatment);
		} else if (dto.getSurgery() != null) {
			SurgeryType surgery = new SurgeryType();
			surgery.setDate(dto.getSurgery().getDate());
			this.setSurgery(surgery);
		} else if (dto.getRadiology() != null) {
			RadiologyType radiology = new RadiologyType();
			for (Date date : dto.getRadiology().getDate()) {
				radiology.getDate().add(date);				
			}
			this.setRadiology(radiology);
		}
	}

	public TreatmentDto getTreatment() {
		TreatmentDto m = null;
		if (this.getDrugTreatment() != null) {
			m = treatmentDtoFactory.createDrugTreatmentDto();
			m.setId(Representation.getId(id));
			m.setPatient(Representation.getId(patient));
			m.setProvider(Representation.getId(provider));
			m.setDiagnosis(diagnosis);
			m.getDrugTreatment().setDosage(drugTreatment.getDosage());
			m.getDrugTreatment().setName(drugTreatment.getName());
		} else if (this.getSurgery() != null) {
			m = treatmentDtoFactory.createSurgeryDto();
			m.setId(Representation.getId(id));
			m.setPatient(Representation.getId(patient));
			m.setProvider(Representation.getId(provider));
			m.setDiagnosis(diagnosis);
			m.getSurgery().setDate(surgery.getDate());
		} else if (this.getRadiology() != null) {
			m = treatmentDtoFactory.createRadiologyDto();
			m.setId(Representation.getId(id));
			m.setPatient(Representation.getId(patient));
			m.setProvider(Representation.getId(provider));
			m.setDiagnosis(diagnosis);
			for (Date date : radiology.getDate()) {
				m.getRadiology().getDate().add(date);
			}
		}
		return m;
	}

	
}
