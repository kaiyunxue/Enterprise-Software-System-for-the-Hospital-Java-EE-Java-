package edu.stevens.cs548.clinic.domain;

import java.util.List;
import java.util.Date;

public interface ITreatmentFactory {
	//ÎÞprovider&patient
	public Treatment createDrugTreatment (String diagnosis, String drug, float dosage);

	public Treatment createRadiology(String diagnosis, List<RadDate> date);

	public Treatment creatSurgery(String diagnosis, Date date);
}
