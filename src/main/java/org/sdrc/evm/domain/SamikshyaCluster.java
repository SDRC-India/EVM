package org.sdrc.evm.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the samikshya_cluster database table.
 * 
 */
@Entity
@Table(name="samikshya_cluster")
@NamedQuery(name="SamikshyaCluster.findAll", query="SELECT s FROM SamikshyaCluster s")
public class SamikshyaCluster implements Serializable {
	private static final long serialVersionUID = 1L;
	private int clusterId;
	private String clusterCode;
	private String clusterName;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private SamikshyaBlock samikshyaBlock;
	private List<SamikshyaMonitoringForm> samikshyaMonitoringForms;

	public SamikshyaCluster() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cluster_id")
	public int getClusterId() {
		return this.clusterId;
	}

	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
	}


	@Column(name="cluster_code")
	public String getClusterCode() {
		return this.clusterCode;
	}

	public void setClusterCode(String clusterCode) {
		this.clusterCode = clusterCode;
	}


	@Column(name="cluster_name")
	public String getClusterName() {
		return this.clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
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
	@ManyToOne
	@JoinColumn(name="block_id")
	public SamikshyaBlock getSamikshyaBlock() {
		return this.samikshyaBlock;
	}

	public void setSamikshyaBlock(SamikshyaBlock samikshyaBlock) {
		this.samikshyaBlock = samikshyaBlock;
	}


	//bi-directional many-to-one association to SamikshyaMonitoringForm
	@OneToMany(mappedBy="samikshyaCluster")
	public List<SamikshyaMonitoringForm> getSamikshyaMonitoringForms() {
		return this.samikshyaMonitoringForms;
	}

	public void setSamikshyaMonitoringForms(List<SamikshyaMonitoringForm> samikshyaMonitoringForms) {
		this.samikshyaMonitoringForms = samikshyaMonitoringForms;
	}

	public SamikshyaMonitoringForm addSamikshyaMonitoringForm(SamikshyaMonitoringForm samikshyaMonitoringForm) {
		getSamikshyaMonitoringForms().add(samikshyaMonitoringForm);
		samikshyaMonitoringForm.setSamikshyaCluster(this);

		return samikshyaMonitoringForm;
	}

	public SamikshyaMonitoringForm removeSamikshyaMonitoringForm(SamikshyaMonitoringForm samikshyaMonitoringForm) {
		getSamikshyaMonitoringForms().remove(samikshyaMonitoringForm);
		samikshyaMonitoringForm.setSamikshyaCluster(null);

		return samikshyaMonitoringForm;
	}

}