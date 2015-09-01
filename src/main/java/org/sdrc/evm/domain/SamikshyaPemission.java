package org.sdrc.evm.domain;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the samikshya_pemission database table.
 * 
 */
@Entity
@Table(name="samikshya_pemission")
@NamedQuery(name="SamikshyaPemission.findAll", query="SELECT s FROM SamikshyaPemission s")
public class SamikshyaPemission implements Serializable {
	private static final long serialVersionUID = 1L;
	private int permissionId;
	private String description;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private String permissionCode;
	private String permissionName;
	private List<SamikshyaFeaturePermissionMapping> samikshyaFeaturePermissionMappings;

	public SamikshyaPemission() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="permission_id")
	public int getPermissionId() {
		return this.permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}


	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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


	@Column(name="permission_code")
	public String getPermissionCode() {
		return this.permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}


	@Column(name="permission_name")
	public String getPermissionName() {
		return this.permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}


	//bi-directional many-to-one association to SamikshyaFeaturePermissionMapping
	@OneToMany(fetch = FetchType.LAZY,mappedBy="samikshyaPemission",cascade = CascadeType.ALL)
	public List<SamikshyaFeaturePermissionMapping> getSamikshyaFeaturePermissionMappings() {
		return this.samikshyaFeaturePermissionMappings;
	}

	public void setSamikshyaFeaturePermissionMappings(List<SamikshyaFeaturePermissionMapping> samikshyaFeaturePermissionMappings) {
		this.samikshyaFeaturePermissionMappings = samikshyaFeaturePermissionMappings;
	}

	public SamikshyaFeaturePermissionMapping addSamikshyaFeaturePermissionMapping(SamikshyaFeaturePermissionMapping samikshyaFeaturePermissionMapping) {
		getSamikshyaFeaturePermissionMappings().add(samikshyaFeaturePermissionMapping);
		samikshyaFeaturePermissionMapping.setSamikshyaPemission(this);

		return samikshyaFeaturePermissionMapping;
	}

	public SamikshyaFeaturePermissionMapping removeSamikshyaFeaturePermissionMapping(SamikshyaFeaturePermissionMapping samikshyaFeaturePermissionMapping) {
		getSamikshyaFeaturePermissionMappings().remove(samikshyaFeaturePermissionMapping);
		samikshyaFeaturePermissionMapping.setSamikshyaPemission(null);

		return samikshyaFeaturePermissionMapping;
	}

}