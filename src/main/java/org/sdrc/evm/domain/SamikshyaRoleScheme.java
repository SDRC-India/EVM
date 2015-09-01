package org.sdrc.evm.domain;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the samikshya_role_scheme database table.
 * 
 */
@Entity
@Table(name="samikshya_role_scheme")
@NamedQuery(name="SamikshyaRoleScheme.findAll", query="SELECT s FROM SamikshyaRoleScheme s")
public class SamikshyaRoleScheme implements Serializable {
	private static final long serialVersionUID = 1L;
	private int roleSchemeId;
	private String areaCode;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private String roleSchemeName;
	private SamikshyaFeaturePermissionMapping samikshyaFeaturePermissionMapping;
	private SamikshyaRole samikshyaRole;
	private List<SamikshyaUserRoleSchemeMapping> samikshyaUserRoleSchemeMappings;

	public SamikshyaRoleScheme() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="role_scheme_id")
	public int getRoleSchemeId() {
		return this.roleSchemeId;
	}

	public void setRoleSchemeId(int roleSchemeId) {
		this.roleSchemeId = roleSchemeId;
	}


	@Column(name="area_code")
	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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


	@Column(name="role_scheme_name")
	public String getRoleSchemeName() {
		return this.roleSchemeName;
	}

	public void setRoleSchemeName(String roleSchemeName) {
		this.roleSchemeName = roleSchemeName;
	}


	//bi-directional many-to-one association to SamikshyaFeaturePermissionMapping
	@ManyToOne
	@JoinColumn(name="feature_permission_id")
	public SamikshyaFeaturePermissionMapping getSamikshyaFeaturePermissionMapping() {
		return this.samikshyaFeaturePermissionMapping;
	}

	public void setSamikshyaFeaturePermissionMapping(SamikshyaFeaturePermissionMapping samikshyaFeaturePermissionMapping) {
		this.samikshyaFeaturePermissionMapping = samikshyaFeaturePermissionMapping;
	}


	//bi-directional many-to-one association to SamikshyaRole
	@ManyToOne
	@JoinColumn(name="role_id")
	public SamikshyaRole getSamikshyaRole() {
		return this.samikshyaRole;
	}

	public void setSamikshyaRole(SamikshyaRole samikshyaRole) {
		this.samikshyaRole = samikshyaRole;
	}


	//bi-directional many-to-one association to SamikshyaUserRoleSchemeMapping
	@OneToMany(fetch = FetchType.LAZY,mappedBy="samikshyaRoleScheme",cascade = CascadeType.ALL)
	public List<SamikshyaUserRoleSchemeMapping> getSamikshyaUserRoleSchemeMappings() {
		return this.samikshyaUserRoleSchemeMappings;
	}

	public void setSamikshyaUserRoleSchemeMappings(List<SamikshyaUserRoleSchemeMapping> samikshyaUserRoleSchemeMappings) {
		this.samikshyaUserRoleSchemeMappings = samikshyaUserRoleSchemeMappings;
	}

	public SamikshyaUserRoleSchemeMapping addSamikshyaUserRoleSchemeMapping(SamikshyaUserRoleSchemeMapping samikshyaUserRoleSchemeMapping) {
		getSamikshyaUserRoleSchemeMappings().add(samikshyaUserRoleSchemeMapping);
		samikshyaUserRoleSchemeMapping.setSamikshyaRoleScheme(this);

		return samikshyaUserRoleSchemeMapping;
	}

	public SamikshyaUserRoleSchemeMapping removeSamikshyaUserRoleSchemeMapping(SamikshyaUserRoleSchemeMapping samikshyaUserRoleSchemeMapping) {
		getSamikshyaUserRoleSchemeMappings().remove(samikshyaUserRoleSchemeMapping);
		samikshyaUserRoleSchemeMapping.setSamikshyaRoleScheme(null);

		return samikshyaUserRoleSchemeMapping;
	}

}