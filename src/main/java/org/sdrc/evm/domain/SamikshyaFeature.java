package org.sdrc.evm.domain;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the samikshya_feature database table.
 * 
 */
@Entity
@Table(name="samikshya_feature")
@NamedQuery(name="SamikshyaFeature.findAll", query="SELECT s FROM SamikshyaFeature s")
public class SamikshyaFeature implements Serializable {
	private static final long serialVersionUID = 1L;
	private int featureId;
	private String description;
	private String featureCode;
	private String featureName;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private List<SamikshyaFeaturePermissionMapping> samikshyaFeaturePermissionMappings;

	public SamikshyaFeature() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="feature_id")
	public int getFeatureId() {
		return this.featureId;
	}

	public void setFeatureId(int featureId) {
		this.featureId = featureId;
	}


	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Column(name="feature_code")
	public String getFeatureCode() {
		return this.featureCode;
	}

	public void setFeatureCode(String featureCode) {
		this.featureCode = featureCode;
	}


	@Column(name="feature_name")
	public String getFeatureName() {
		return this.featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
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


	//bi-directional many-to-one association to SamikshyaFeaturePermissionMapping
	@OneToMany(fetch = FetchType.LAZY,mappedBy="samikshyaFeature",cascade = CascadeType.ALL)
	public List<SamikshyaFeaturePermissionMapping> getSamikshyaFeaturePermissionMappings() {
		return this.samikshyaFeaturePermissionMappings;
	}

	public void setSamikshyaFeaturePermissionMappings(List<SamikshyaFeaturePermissionMapping> samikshyaFeaturePermissionMappings) {
		this.samikshyaFeaturePermissionMappings = samikshyaFeaturePermissionMappings;
	}

	public SamikshyaFeaturePermissionMapping addSamikshyaFeaturePermissionMapping(SamikshyaFeaturePermissionMapping samikshyaFeaturePermissionMapping) {
		getSamikshyaFeaturePermissionMappings().add(samikshyaFeaturePermissionMapping);
		samikshyaFeaturePermissionMapping.setSamikshyaFeature(this);

		return samikshyaFeaturePermissionMapping;
	}

	public SamikshyaFeaturePermissionMapping removeSamikshyaFeaturePermissionMapping(SamikshyaFeaturePermissionMapping samikshyaFeaturePermissionMapping) {
		getSamikshyaFeaturePermissionMappings().remove(samikshyaFeaturePermissionMapping);
		samikshyaFeaturePermissionMapping.setSamikshyaFeature(null);

		return samikshyaFeaturePermissionMapping;
	}

}