package org.sdrc.evm.domain;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the samikshya_state database table.
 * 
 */
@Entity
@Table(name="samikshya_state")
@NamedQuery(name="SamikshyaState.findAll", query="SELECT s FROM SamikshyaState s")
public class SamikshyaState implements Serializable {
	private static final long serialVersionUID = 1L;
	private int stateId;
	private String stateCode;
	private String stateName;
	private List<SamikshyaDistrict> samikshyaDistricts;
	private List<SamikshyaMonitoringForm> samikshyaMonitoringForms;

	public SamikshyaState() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="state_id")
	public int getStateId() {
		return this.stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}


	@Column(name="state_code")
	public String getStateCode() {
		return this.stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}


	@Column(name="state_name")
	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}


	//bi-directional many-to-one association to SamikshyaDistrict
	@OneToMany(fetch = FetchType.EAGER,mappedBy="samikshyaState",cascade = CascadeType.ALL)
	public List<SamikshyaDistrict> getSamikshyaDistricts() {
		return this.samikshyaDistricts;
	}

	public void setSamikshyaDistricts(List<SamikshyaDistrict> samikshyaDistricts) {
		this.samikshyaDistricts = samikshyaDistricts;
	}

	public SamikshyaDistrict addSamikshyaDistrict(SamikshyaDistrict samikshyaDistrict) {
		getSamikshyaDistricts().add(samikshyaDistrict);
		samikshyaDistrict.setSamikshyaState(this);

		return samikshyaDistrict;
	}

	public SamikshyaDistrict removeSamikshyaDistrict(SamikshyaDistrict samikshyaDistrict) {
		getSamikshyaDistricts().remove(samikshyaDistrict);
		samikshyaDistrict.setSamikshyaState(null);

		return samikshyaDistrict;
	}


	//bi-directional many-to-one association to SamikshyaMonitoringForm
	@OneToMany(fetch = FetchType.EAGER,mappedBy="samikshyaState",cascade = CascadeType.ALL)
	public List<SamikshyaMonitoringForm> getSamikshyaMonitoringForms() {
		return this.samikshyaMonitoringForms;
	}

	public void setSamikshyaMonitoringForms(List<SamikshyaMonitoringForm> samikshyaMonitoringForms) {
		this.samikshyaMonitoringForms = samikshyaMonitoringForms;
	}

	public SamikshyaMonitoringForm addSamikshyaMonitoringForm(SamikshyaMonitoringForm samikshyaMonitoringForm) {
		getSamikshyaMonitoringForms().add(samikshyaMonitoringForm);
		samikshyaMonitoringForm.setSamikshyaState(this);

		return samikshyaMonitoringForm;
	}

	public SamikshyaMonitoringForm removeSamikshyaMonitoringForm(SamikshyaMonitoringForm samikshyaMonitoringForm) {
		getSamikshyaMonitoringForms().remove(samikshyaMonitoringForm);
		samikshyaMonitoringForm.setSamikshyaState(null);

		return samikshyaMonitoringForm;
	}

}