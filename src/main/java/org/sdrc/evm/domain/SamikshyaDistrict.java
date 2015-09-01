package org.sdrc.evm.domain;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the samikshya_district database table.
 * 
 */
@Entity
@Table(name="samikshya_district")
@NamedQuery(name="SamikshyaDistrict.findAll", query="SELECT s FROM SamikshyaDistrict s")
public class SamikshyaDistrict implements Serializable {
	private static final long serialVersionUID = 1L;
	private int districtId;
	private String districtCode;
	private String districtName;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private List<SamikshyaBlock> samikshyaBlocks;
	private SamikshyaState samikshyaState;
	private List<SamikshyaMonitoringForm> samikshyaMonitoringForms;

	public SamikshyaDistrict() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="district_id")
	public int getDistrictId() {
		return this.districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}


	@Column(name="district_code")
	public String getDistrictCode() {
		return this.districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}


	@Column(name="district_name")
	public String getDistrictName() {
		return this.districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}


	@Column(name="last_updated_by")
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}


	@Column(name="last_updated_date")
	public Timestamp getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}


	//bi-directional many-to-one association to SamikshyaBlock
	@OneToMany(fetch = FetchType.EAGER,mappedBy="samikshyaDistrict",cascade = CascadeType.ALL)
	public List<SamikshyaBlock> getSamikshyaBlocks() {
		return this.samikshyaBlocks;
	}

	public void setSamikshyaBlocks(List<SamikshyaBlock> samikshyaBlocks) {
		this.samikshyaBlocks = samikshyaBlocks;
	}

	public SamikshyaBlock addSamikshyaBlock(SamikshyaBlock samikshyaBlock) {
		getSamikshyaBlocks().add(samikshyaBlock);
		samikshyaBlock.setSamikshyaDistrict(this);

		return samikshyaBlock;
	}

	public SamikshyaBlock removeSamikshyaBlock(SamikshyaBlock samikshyaBlock) {
		getSamikshyaBlocks().remove(samikshyaBlock);
		samikshyaBlock.setSamikshyaDistrict(null);

		return samikshyaBlock;
	}


	//bi-directional many-to-one association to SamikshyaState
	@ManyToOne
	@JoinColumn(name="state_id")
	public SamikshyaState getSamikshyaState() {
		return this.samikshyaState;
	}

	public void setSamikshyaState(SamikshyaState samikshyaState) {
		this.samikshyaState = samikshyaState;
	}


	//bi-directional many-to-one association to SamikshyaMonitoringForm
	@OneToMany(fetch = FetchType.EAGER,mappedBy="samikshyaDistrict",cascade = CascadeType.ALL)
	public List<SamikshyaMonitoringForm> getSamikshyaMonitoringForms() {
		return this.samikshyaMonitoringForms;
	}

	public void setSamikshyaMonitoringForms(List<SamikshyaMonitoringForm> samikshyaMonitoringForms) {
		this.samikshyaMonitoringForms = samikshyaMonitoringForms;
	}

	public SamikshyaMonitoringForm addSamikshyaMonitoringForm(SamikshyaMonitoringForm samikshyaMonitoringForm) {
		getSamikshyaMonitoringForms().add(samikshyaMonitoringForm);
		samikshyaMonitoringForm.setSamikshyaDistrict(this);

		return samikshyaMonitoringForm;
	}

	public SamikshyaMonitoringForm removeSamikshyaMonitoringForm(SamikshyaMonitoringForm samikshyaMonitoringForm) {
		getSamikshyaMonitoringForms().remove(samikshyaMonitoringForm);
		samikshyaMonitoringForm.setSamikshyaDistrict(null);

		return samikshyaMonitoringForm;
	}

}