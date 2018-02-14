package edu.stevens.cs548.clinic.billing.domain;

import java.util.Date;

import edu.stevens.cs548.clinic.billing.BillingRecord;
import edu.stevens.cs548.clinic.domain.Treatment;

public interface IBillingRecordFactory {
	
	public BillingRecord createBillingRecord();
	
}
