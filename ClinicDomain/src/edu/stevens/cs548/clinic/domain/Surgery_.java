package edu.stevens.cs548.clinic.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-11-26T01:20:35.054-0500")
@StaticMetamodel(Surgery.class)
public class Surgery_ extends Treatment_ {
	public static volatile SingularAttribute<Surgery, String> type;
	public static volatile SingularAttribute<Surgery, Date> date;
}
