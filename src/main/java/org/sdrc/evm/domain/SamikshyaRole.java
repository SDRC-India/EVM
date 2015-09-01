package org.sdrc.evm.domain;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the samikshya_role database table.
 * 
 */
@Entity
@Table(name="samikshya_role")
@NamedQuery(name="SamikshyaRole.findAll", query="SELECT s FROM SamikshyaRole s")
public class SamikshyaRole implements Serializable {
	private static final long serialVersionUID = 1L;
	private int roleId;
	private String description;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private String roleCode;
	private String roleName;
	private List<SamikshyaRoleScheme> samikshyaRoleSchemes;

	public SamikshyaRole() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="role_id")
	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
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


	@Column(name="role_code")
	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}


	@Column(name="role_name")
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	//bi-directional many-to-one association to SamikshyaRoleScheme
	@OneToMany(fetch = FetchType.LAZY,mappedBy="samikshyaRole",cascade = CascadeType.ALL)
	public List<SamikshyaRoleScheme> getSamikshyaRoleSchemes() {
		return this.samikshyaRoleSchemes;
	}

	public void setSamikshyaRoleSchemes(List<SamikshyaRoleScheme> samikshyaRoleSchemes) {
		this.samikshyaRoleSchemes = samikshyaRoleSchemes;
	}

	public SamikshyaRoleScheme addSamikshyaRoleScheme(SamikshyaRoleScheme samikshyaRoleScheme) {
		getSamikshyaRoleSchemes().add(samikshyaRoleScheme);
		samikshyaRoleScheme.setSamikshyaRole(this);

		return samikshyaRoleScheme;
	}

	public SamikshyaRoleScheme removeSamikshyaRoleScheme(SamikshyaRoleScheme samikshyaRoleScheme) {
		getSamikshyaRoleSchemes().remove(samikshyaRoleScheme);
		samikshyaRoleScheme.setSamikshyaRole(null);

		return samikshyaRoleScheme;
	}

}