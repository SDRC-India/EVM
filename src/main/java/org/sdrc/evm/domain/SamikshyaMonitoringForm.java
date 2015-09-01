package org.sdrc.evm.domain;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the samikshya_monitoring_form database table.
 * 
 */
@Entity
@Table(name="samikshya_monitoring_form")
@NamedQuery(name="SamikshyaMonitoringForm.findAll", query="SELECT s FROM SamikshyaMonitoringForm s")
public class SamikshyaMonitoringForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String formCode;
	private String formPath;
	private String formType;
	private String isActive;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private String version;
	private SamikshyaBlock samikshyaBlock;
	private SamikshyaCluster samikshyaCluster;
	private SamikshyaDistrict samikshyaDistrict;
	private SamikshyaState samikshyaState;
	private List<SamikshyaMonitoringFormTran> samikshyaMonitoringFormTrans;

	public SamikshyaMonitoringForm() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Column(name="form_code")
	public String getFormCode() {
		return this.formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}


	@Column(name="form_path")
	public String getFormPath() {
		return this.formPath;
	}

	public void setFormPath(String formPath) {
		this.formPath = formPath;
	}


	@Column(name="form_type")
	public String getFormType() {
		return this.formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}


	@Column(name="is_active")
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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


	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}


	//bi-directional many-to-one association to SamikshyaBlock
	@ManyToOne
	@JoinColumn(name="block_id")
	public SamikshyaBlock getSamikshyaBlock() {
		return this.samikshyaBlock;
	}

	public void setSamikshyaBlock(SamikshyaBlock samikshyaBlock) {
		this.samikshyaBlock = samikshyaBlock;
	}


	//bi-directional many-to-one association to SamikshyaCluster
	@ManyToOne
	@JoinColumn(name="cluster_id")
	public SamikshyaCluster getSamikshyaCluster() {
		return this.samikshyaCluster;
	}

	public void setSamikshyaCluster(SamikshyaCluster samikshyaCluster) {
		this.samikshyaCluster = samikshyaCluster;
	}


	//bi-directional many-to-one association to SamikshyaDistrict
	@ManyToOne
	@JoinColumn(name="district_id")
	public SamikshyaDistrict getSamikshyaDistrict() {
		return this.samikshyaDistrict;
	}

	public void setSamikshyaDistrict(SamikshyaDistrict samikshyaDistrict) {
		this.samikshyaDistrict = samikshyaDistrict;
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


	//bi-directional many-to-one association to SamikshyaMonitoringFormTran
	@OneToMany(fetch = FetchType.LAZY,mappedBy="samikshyaMonitoringForm",cascade = CascadeType.ALL)
	public List<SamikshyaMonitoringFormTran> getSamikshyaMonitoringFormTrans() {
		return this.samikshyaMonitoringFormTrans;
	}

	public void setSamikshyaMonitoringFormTrans(List<SamikshyaMonitoringFormTran> samikshyaMonitoringFormTrans) {
		this.samikshyaMonitoringFormTrans = samikshyaMonitoringFormTrans;
	}

	public SamikshyaMonitoringFormTran addSamikshyaMonitoringFormTran(SamikshyaMonitoringFormTran samikshyaMonitoringFormTran) {
		getSamikshyaMonitoringFormTrans().add(samikshyaMonitoringFormTran);
		samikshyaMonitoringFormTran.setSamikshyaMonitoringForm(this);

		return samikshyaMonitoringFormTran;
	}

	public SamikshyaMonitoringFormTran removeSamikshyaMonitoringFormTran(SamikshyaMonitoringFormTran samikshyaMonitoringFormTran) {
		getSamikshyaMonitoringFormTrans().remove(samikshyaMonitoringFormTran);
		samikshyaMonitoringFormTran.setSamikshyaMonitoringForm(null);

		return samikshyaMonitoringFormTran;
	}

}