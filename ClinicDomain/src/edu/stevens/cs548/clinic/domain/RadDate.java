package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * Entity implementation class for Entity: RadDate
 *
 */
@Entity
@Table(name="RadDate")
public class RadDate implements Serializable {

	@JoinColumn(name="radiology_fk",referencedColumnName="treatment_id",nullable=false)
	@ManyToOne
	private Radiology radiology_fk;   
	@Id
	@GeneratedValue
	private long id;
	@Column(name="date",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date date;
	private static final long serialVersionUID = 1L;

	public RadDate() {
		super();
	}   
	public Radiology getRadiology_fk() {
		return this.radiology_fk;
	}

	public void setRadiology_fk(Radiology radiology_fk) {
		this.radiology_fk = radiology_fk;
	}   
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}   
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
   
}
