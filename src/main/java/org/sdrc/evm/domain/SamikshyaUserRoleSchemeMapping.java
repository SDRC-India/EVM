package org.sdrc.evm.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the samikshya_user_role_scheme_mapping database table.
 * 
 */
@Entity
@Table(name="samikshya_user_role_scheme_mapping")
@NamedQuery(name="SamikshyaUserRoleSchemeMapping.findAll", query="SELECT s FROM SamikshyaUserRoleSchemeMapping s")
public class SamikshyaUserRoleSchemeMapping implements Serializable {
	private static final long serialVersionUID = 1L;
	private int userRoleSchemeId;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private SamikshyaRoleScheme samikshyaRoleScheme;
	private SamikshyaUser samikshyaUser;

	public SamikshyaUserRoleSchemeMapping() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_role_scheme_id")
	public int getUserRoleSchemeId() {
		return this.userRoleSchemeId;
	}

	public void setUserRoleSchemeId(int userRoleSchemeId) {
		this.userRoleSchemeId = userRoleSchemeId;
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


	//bi-directional many-to-one association to SamikshyaRoleScheme
	@ManyToOne
	@JoinColumn(name="role_scheme_id")
	public SamikshyaRoleScheme getSamikshyaRoleScheme() {
		return this.samikshyaRoleScheme;
	}

	public void setSamikshyaRoleScheme(SamikshyaRoleScheme samikshyaRoleScheme) {
		this.samikshyaRoleScheme = samikshyaRoleScheme;
	}


	//bi-directional many-to-one association to SamikshyaUser
	@ManyToOne
	@JoinColumn(name="user_id")
	public SamikshyaUser getSamikshyaUser() {
		return this.samikshyaUser;
	}

	public void setSamikshyaUser(SamikshyaUser samikshyaUser) {
		this.samikshyaUser = samikshyaUser;
	}

}