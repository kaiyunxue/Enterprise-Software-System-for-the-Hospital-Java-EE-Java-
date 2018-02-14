package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Entity implementation class for Entity: DrugTreatment
 * 
 */
@Entity
@DiscriminatorValue("drug") 
public class DrugTreatment extends Treatment implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name="drug",nullable=true)
	private String drug;
	@Column(name="dosage",nullable=true)
	private float dosage;

	public String getDrug() {
		return drug;
	}

	public void setDrug(String drug) {
		this.drug = drug;
	}

	public float getDosage() {
		return dosage;
	}

	public void setDosage(float dosage) {
		this.dosage = dosage;
	}

	public <T> T export(ITreatmentExporter<T> visitor) {
		return visitor.exportDrugTreatment(this.getId(), 
								   		   this.getDiagnosis(),
								   		   this.drug, 
								   		   this.dosage);
	}

	public DrugTreatment() {
		super();
		this.setTreatmentType("drug");
	}

}
