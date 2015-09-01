package org.sdrc.evm.domain;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the samikshya_block database table.
 * 
 */
@Entity
@Table(name="samikshya_block")
@NamedQuery(name="SamikshyaBlock.findAll", query="SELECT s FROM SamikshyaBlock s")
public class SamikshyaBlock implements Serializable {
	private static final long serialVersionUID = 1L;
	private int blockId;
	private String blockCode;
	private String blockName;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private SamikshyaDistrict samikshyaDistrict;
	private List<SamikshyaCluster> samikshyaClusters;
	private List<SamikshyaMonitoringForm> samikshyaMonitoringForms;

	public SamikshyaBlock() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="block_id")
	public int getBlockId() {
		return this.blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}


	@Column(name="block_code")
	public String getBlockCode() {
		return this.blockCode;
	}

	public void setBlockCode(String blockCode) {
		this.blockCode = blockCode;
	}


	@Column(name="block_name")
	public String getBlockName() {
		return this.blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
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


	//bi-directional many-to-one association to SamikshyaDistrict
	@ManyToOne
	@JoinColumn(name="district_id")
	public SamikshyaDistrict getSamikshyaDistrict() {
		return this.samikshyaDistrict;
	}

	public void setSamikshyaDistrict(SamikshyaDistrict samikshyaDistrict) {
		this.samikshyaDistrict = samikshyaDistrict;
	}


	//bi-directional many-to-one association to SamikshyaCluster
	@OneToMany(fetch = FetchType.LAZY,mappedBy="samikshyaBlock",cascade = CascadeType.ALL)
	public List<SamikshyaCluster> getSamikshyaClusters() {
		return this.samikshyaClusters;
	}

	public void setSamikshyaClusters(List<SamikshyaCluster> samikshyaClusters) {
		this.samikshyaClusters = samikshyaClusters;
	}

	public SamikshyaCluster addSamikshyaCluster(SamikshyaCluster samikshyaCluster) {
		getSamikshyaClusters().add(samikshyaCluster);
		samikshyaCluster.setSamikshyaBlock(this);

		return samikshyaCluster;
	}

	public SamikshyaCluster removeSamikshyaCluster(SamikshyaCluster samikshyaCluster) {
		getSamikshyaClusters().remove(samikshyaCluster);
		samikshyaCluster.setSamikshyaBlock(null);

		return samikshyaCluster;
	}


	//bi-directional many-to-one association to SamikshyaMonitoringForm
	@OneToMany(mappedBy="samikshyaBlock")
	public List<SamikshyaMonitoringForm> getSamikshyaMonitoringForms() {
		return this.samikshyaMonitoringForms;
	}

	public void setSamikshyaMonitoringForms(List<SamikshyaMonitoringForm> samikshyaMonitoringForms) {
		this.samikshyaMonitoringForms = samikshyaMonitoringForms;
	}

	public SamikshyaMonitoringForm addSamikshyaMonitoringForm(SamikshyaMonitoringForm samikshyaMonitoringForm) {
		getSamikshyaMonitoringForms().add(samikshyaMonitoringForm);
		samikshyaMonitoringForm.setSamikshyaBlock(this);

		return samikshyaMonitoringForm;
	}

	public SamikshyaMonitoringForm removeSamikshyaMonitoringForm(SamikshyaMonitoringForm samikshyaMonitoringForm) {
		getSamikshyaMonitoringForms().remove(samikshyaMonitoringForm);
		samikshyaMonitoringForm.setSamikshyaBlock(null);

		return samikshyaMonitoringForm;
	}

}