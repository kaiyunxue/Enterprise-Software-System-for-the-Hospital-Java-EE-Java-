package edu.stevens.cs548.clinic.service.dto.util;

import edu.stevens.cs548.clinic.domain.Treatment;
import edu.stevens.cs548.clinic.domain.DrugTreatment;
import edu.stevens.cs548.clinic.domain.Radiology;
import edu.stevens.cs548.clinic.domain.Surgery;
import edu.stevens.cs548.clinic.domain.RadDate;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;

public class TreatmentDtoFactory {
	
	ObjectFactory factory;
	
	public TreatmentDtoFactory() {
		factory = new ObjectFactory();
	}
	
	public TreatmentDto createDrugTreatmentDto(){
		TreatmentDto td = factory.createTreatmentDto();
		DrugTreatmentType dt = factory.createDrugTreatmentType();
		td.setDrugTreatment(dt);
		return td;
	}
	
	public TreatmentDto createRadiologyDto(){
		TreatmentDto td = factory.createTreatmentDto();
		RadiologyType rt = factory.createRadiologyType();
		td.setRadiology(rt);
		return td;
	}
	
	public TreatmentDto createSurgeryDto(){
		TreatmentDto td = factory.createTreatmentDto();
		SurgeryType st = factory.createSurgeryType();
		td.setSurgery(st);
		return td;
	}
	
	public TreatmentDto createDrugTreatmentDto (DrugTreatment t) {
		TreatmentDto td = createTreatmentDto(t);
		DrugTreatmentType dt = factory.createDrugTreatmentType();
		dt.setDosage(t.getDosage());
		dt.setName(t.getDrug());
		td.setDrugTreatment(dt);
		return td;
	}
	
	public TreatmentDto createRadiologyDto (Radiology t) {
		TreatmentDto td = createTreatmentDto(t);
		RadiologyType rt = factory.createRadiologyType();
		for(RadDate rd:t.getDate()) rt.getDate().add(rd.getDate());
		td.setRadiology(rt);
		return td;
	}
	
	public TreatmentDto createSurgeryDto (Surgery t) {
		TreatmentDto td = createTreatmentDto(t);
		SurgeryType st = factory.createSurgeryType();
		st.setDate(t.getDate());
		td.setSurgery(st);
		return td;
	}
	public TreatmentDto createTreatmentDto (Treatment t) {
		/*
		 * TODO: Initialize the DTO from the drug treatment.
		 */
		TreatmentDto td = factory.createTreatmentDto();
		td.setId(t.getId());
		td.setDiagnosis(t.getDiagnosis());
		return td;
	}
	

}
