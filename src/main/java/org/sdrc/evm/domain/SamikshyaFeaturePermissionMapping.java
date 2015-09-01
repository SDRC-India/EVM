package org.sdrc.evm.domain;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the samikshya_feature_permission_mapping database table.
 * 
 */
@Entity
@Table(name="samikshya_feature_permission_mapping")
@NamedQuery(name="SamikshyaFeaturePermissionMapping.findAll", query="SELECT s FROM SamikshyaFeaturePermissionMapping s")
public class SamikshyaFeaturePermissionMapping implements Serializable {
	private static final long serialVersionUID = 1L;
	private int featurePermissionId;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private SamikshyaFeature samikshyaFeature;
	private SamikshyaPemission samikshyaPemission;
	private List<SamikshyaRoleScheme> samikshyaRoleSchemes;

	public SamikshyaFeaturePermissionMapping() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="feature_permission_id")
	public int getFeaturePermissionId() {
		return this.featurePermissionId;
	}

	public void setFeaturePermissionId(int featurePermissionId) {
		this.featurePermissionId = featurePermissionId;
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


	//bi-directional many-to-one association to SamikshyaFeature
	@ManyToOne
	@JoinColumn(name="feature_id")
	public SamikshyaFeature getSamikshyaFeature() {
		return this.samikshyaFeature;
	}

	public void setSamikshyaFeature(SamikshyaFeature samikshyaFeature) {
		this.samikshyaFeature = samikshyaFeature;
	}


	//bi-directional many-to-one association to SamikshyaPemission
	@ManyToOne
	@JoinColumn(name="permission_id")
	public SamikshyaPemission getSamikshyaPemission() {
		return this.samikshyaPemission;
	}

	public void setSamikshyaPemission(SamikshyaPemission samikshyaPemission) {
		this.samikshyaPemission = samikshyaPemission;
	}


	//bi-directional many-to-one association to SamikshyaRoleScheme
	@OneToMany(fetch = FetchType.LAZY,mappedBy="samikshyaFeaturePermissionMapping",cascade = CascadeType.ALL)
	public List<SamikshyaRoleScheme> getSamikshyaRoleSchemes() {
		return this.samikshyaRoleSchemes;
	}

	public void setSamikshyaRoleSchemes(List<SamikshyaRoleScheme> samikshyaRoleSchemes) {
		this.samikshyaRoleSchemes = samikshyaRoleSchemes;
	}

	public SamikshyaRoleScheme addSamikshyaRoleScheme(SamikshyaRoleScheme samikshyaRoleScheme) {
		getSamikshyaRoleSchemes().add(samikshyaRoleScheme);
		samikshyaRoleScheme.setSamikshyaFeaturePermissionMapping(this);

		return samikshyaRoleScheme;
	}

	public SamikshyaRoleScheme removeSamikshyaRoleScheme(SamikshyaRoleScheme samikshyaRoleScheme) {
		getSamikshyaRoleSchemes().remove(samikshyaRoleScheme);
		samikshyaRoleScheme.setSamikshyaFeaturePermissionMapping(null);

		return samikshyaRoleScheme;
	}

}