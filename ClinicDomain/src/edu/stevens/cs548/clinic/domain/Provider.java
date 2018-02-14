package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;

import java.util.LinkedList;
import java.util.List;

/**
 * Entity implementation class for Entity: Provider
 *
 */

@NamedQueries({
	@NamedQuery(name="SearchProviderByProviderId",
				query="select p from Provider p where p.provider_id = :pid"),
	@NamedQuery(name="CountProviderByProviderId",
				query="select count(p) from Provider p where p.provider_id = :pid"),
	@NamedQuery(
			name = "getProPK",
			query = "select p.id from Provider p where p.provider_id = :pid")
})
@Entity
@Table(name="provider")
public class Provider implements Serializable {


	@Id
	@GeneratedValue
	private long id;
	@Column(name="provider_id",nullable=false)
	private long provider_id;
	@Column(name="provider_name")
	private String name;
	@Column(name="specialization")
	private String spec;
	@OneToMany(cascade=CascadeType.ALL,mappedBy="provider")
	private List<Treatment> treatments;
	private static final long serialVersionUID = 1L;

	public Provider() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getProvider_id() {
		return this.provider_id;
	}

	public void setProvider_id(long provider_id) {
		this.provider_id = provider_id;
	}
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getSpec() {
		return this.spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}
	public List<Treatment> getTreatments() {
		return this.treatments;
	}

	public void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}

	@Transient
	private ITreatmentDAO treatmentDAO;
	public void setTreatmentDAO (ITreatmentDAO tdao) {
		this.treatmentDAO = tdao;
	}
	//
	public long addTreatment (Patient p, Treatment t) {
		this.getTreatments().add(t);
		long id = p.addTreatment(t);
		return id;
	}
	public List<Long> getTreatmentIds(){
		List<Long> idList = new LinkedList<Long>();
		for(Treatment t:this.treatments) idList.add(t.getId());
		return idList;
	}
	public <T> T exportTreatment(long tid, ITreatmentExporter<T> visitor) throws TreatmentExn {
		Treatment t = treatmentDAO.getTreatment(tid);
		if (t.getProvider() != this) {
			throw new TreatmentExn("Inappropriate treatment access: provider = " + id + ", treatment = " + tid);
		}
		return t.export(visitor);
	}
}
