package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;
import java.util.Date;

/**
 * Entity implementation class for Entity: Surgery
 *
 */
@Entity
@DiscriminatorValue("surgery")
public class Surgery extends Treatment implements Serializable {

	@Column(name="surgery_type",nullable=true)
	private String type;
	@Temporal(TemporalType.DATE)
	@Column(name="surgery_date",nullable=true)
	private Date date;
	private static final long serialVersionUID = 1L;

	public Surgery() {
		super();
		this.setTreatmentType("surgery");
	}   
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}   
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	public <T> T export(ITreatmentExporter<T> visitor) {
		return visitor.exportSurgery(this.getId(), 
								   		   this.getDiagnosis(),
								   		   this.date);
	}
}
