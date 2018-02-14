package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;
import java.util.List;

/**
 * Entity implementation class for Entity: Radiology
 *
 */
@Entity
@DiscriminatorValue("radiology")
public class Radiology extends Treatment implements Serializable {

	@Column(name="radiology_type",nullable=true)
	private String type;
	@OneToMany(cascade=CascadeType.ALL,mappedBy="radiology_fk")
	private List<RadDate> date;
	private static final long serialVersionUID = 1L;

	public Radiology() {
		super();
		this.setTreatmentType("radiology");
	}   
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}   
	public List<RadDate> getDate() {
		return this.date;
	}

	public void setDate(List<RadDate> date) {
		this.date = date;
	}
	
	public void addDate(RadDate d){
		this.date.add(d);
	}
	
	public <T> T export(ITreatmentExporter<T> visitor) {
		return visitor.exportRadiology(this.getId(), 
								   		   this.getDiagnosis(), 
								   		   this.date);
	}
}
