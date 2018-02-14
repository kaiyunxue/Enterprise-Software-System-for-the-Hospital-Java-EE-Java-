package edu.stevens.cs548.clinic.billing.domain;

import java.util.Date;

import edu.stevens.cs548.clinic.billing.BillingRecord;
import edu.stevens.cs548.clinic.domain.Treatment;

public class BillingRecordFactory implements IBillingRecordFactory {

	@Override
	public BillingRecord createBillingRecord() {
		return new BillingRecord();
	}

}
