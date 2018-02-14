package edu.stevens.cs548.clinic.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-11-26T01:20:19.402-0500")
@StaticMetamodel(RadDate.class)
public class RadDate_ {
	public static volatile SingularAttribute<RadDate, Radiology> radiology_fk;
	public static volatile SingularAttribute<RadDate, Long> id;
	public static volatile SingularAttribute<RadDate, Date> date;
}
