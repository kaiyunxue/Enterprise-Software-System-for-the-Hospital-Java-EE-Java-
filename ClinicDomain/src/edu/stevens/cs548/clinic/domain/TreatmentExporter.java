package edu.stevens.cs548.clinic.domain;

import java.util.Date;
import java.util.List;

public class TreatmentExporter implements ITreatmentExporter<Treatment> {
	@Override
	public Treatment exportDrugTreatment (long tid,
			 String diagnosis,
	   		 String drug,
	   		 float dosage){
		DrugTreatment t = new DrugTreatment();
		t.setId(tid);
		t.setTreatmentType("drug");
		t.setDiagnosis(diagnosis);
		t.setDrug(drug);
		t.setDosage(dosage);
		return t;
	}

public Treatment exportRadiology (long tid,
		 String diagnosis,
		 List<RadDate> dates){
	Radiology t = new Radiology();
	t.setDate(dates);
	t.setDiagnosis(diagnosis);
	t.setTreatmentType("radiology");
	t.setId(tid);
	t.setDiagnosis(diagnosis);
	return t;
}

public Treatment exportSurgery (long tid,
	   String diagnosis,
      Date date){
	Surgery t = new Surgery();
	t.setId(tid);
	t.setDiagnosis(diagnosis);
	t.setTreatmentType("surgery");
	t.setDate(date);
	return null;
}
}
