package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Treatment
 *
 */
@Entity
//strategy = InheritanceType.JOINED
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="treatmentType")
public abstract class Treatment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="treatment_id",nullable=false)
	private long id;
	@Column(name="diagnosis")
	private String diagnosis;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name="treatmentType")
	private String treatmentType;

	public String getTreatmentType() {
		return treatmentType;
	}

	public void setTreatmentType(String treatmentType) {
		this.treatmentType = treatmentType;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	@JoinColumn(name="patient",referencedColumnName="patient_id")
	@ManyToOne
	private Patient patient;

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	@JoinColumn(name="provider",referencedColumnName="provider_id")
	@ManyToOne
	private Provider provider;

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider p) {
		this.provider = p;
	}
	public abstract <T> T export(ITreatmentExporter<T> visitor);

	public Treatment() {
		super();
	}

}
