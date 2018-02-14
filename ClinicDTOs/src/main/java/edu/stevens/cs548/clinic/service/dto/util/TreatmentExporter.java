package edu.stevens.cs548.clinic.service.dto.util;

import java.util.Date;
import java.util.List;

import edu.stevens.cs548.clinic.domain.ITreatmentExporter;
import edu.stevens.cs548.clinic.domain.RadDate;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;

public class TreatmentExporter implements ITreatmentExporter<TreatmentDto> {
	private ObjectFactory factory = new ObjectFactory();

	@Override
	public TreatmentDto exportDrugTreatment(long tid, String diagnosis, String drug, float dosage) {
		TreatmentDto treatmentDto = factory.createTreatmentDto();
		treatmentDto.setId(tid);
		treatmentDto.setDiagnosis(diagnosis);
		DrugTreatmentType drugTreatmentType = factory.createDrugTreatmentType();
		drugTreatmentType.setDosage(dosage);
		drugTreatmentType.setName(drug);
		treatmentDto.setDrugTreatment(drugTreatmentType);
		return treatmentDto;
	}

	@Override
	public TreatmentDto exportSurgery(long tid, String diagnosis, Date date) {
		TreatmentDto treatmentDto = factory.createTreatmentDto();
		treatmentDto.setId(tid);
		treatmentDto.setDiagnosis(diagnosis);
		SurgeryType surgeryType = factory.createSurgeryType();
		surgeryType.setDate(date);
		treatmentDto.setSurgery(surgeryType);
		return treatmentDto;
	}

	@Override
	public TreatmentDto exportRadiology(long tid, String diagnosis, List<RadDate> dates) {
		TreatmentDto treatmentDto = factory.createTreatmentDto();
		treatmentDto.setId(tid);
		treatmentDto.setDiagnosis(diagnosis);
		RadiologyType radiologyType = factory.createRadiologyType();
		for (RadDate rd : dates)
			radiologyType.getDate().add(rd.getDate());
		treatmentDto.setRadiology(radiologyType);
		return treatmentDto;
	}
}
