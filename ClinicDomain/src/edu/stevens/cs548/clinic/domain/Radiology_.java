package edu.stevens.cs548.clinic.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-11-26T01:20:35.043-0500")
@StaticMetamodel(Radiology.class)
public class Radiology_ extends Treatment_ {
	public static volatile SingularAttribute<Radiology, String> type;
	public static volatile ListAttribute<Radiology, RadDate> date;
}
