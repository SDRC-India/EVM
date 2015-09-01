package org.sdrc.odkaggregate.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the ripas_xform table.
 * 
 */
@Entity
@Table(name="xform")
@NamedQuery(name="RipasForm.findAll", query="SELECT s FROM XForm s")
public class XForm {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="xform_id")
	private int id;
	
	@Column(name="server_url")
	private String server_url;
	
	@Column(name="form_id")
	private String form_id;
	
	@Column(name="transform_result_title")
	private String transform_result_title;
	
	@Column(name="url")
	private String url;
	
	@Column(name="area_code")
	private String area_code;
	
	@Column(name="status")
	private String status;
	
	@Column(name="version")
	private String version;
	
	@Column(name="last_updated_by")
	private String last_updated_by;
	
	@Column(name="last_updated_time")
	private Timestamp last_updated_time;
	
	@Column(name="active")
	private Boolean active;
	
	@Column(name="level")
	private int level;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="enketo_api_token")
	private String enketoApiToken;
	
	/**
	 * @return the enketoApiToken
	 */
	public String getEnketoApiToken() {
		return enketoApiToken;
	}

	/**
	 * @param enketoApiToken the enketoApiToken to set
	 */
	public void setEnketoApiToken(String enketoApiToken) {
		this.enketoApiToken = enketoApiToken;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "xform_indicator_mapping", joinColumns = { 
			@JoinColumn(name = "xform_id", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "evm_requirement_id", 
					nullable = false, updatable = false) })
	private List<EvmRequirement> evmRequirements;
	
	@Column(name="submission_path")
	private String submissionPath;
	
	@Column(name="level_name")
	private String levelName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getServer_url() {
		return server_url;
	}

	public void setServer_url(String server_url) {
		this.server_url = server_url;
	}

	public String getForm_id() {
		return form_id;
	}

	public void setForm_id(String form_id) {
		this.form_id = form_id;
	}

	public String getTransform_result_title() {
		return transform_result_title;
	}

	public void setTransform_result_title(String transform_result_title) {
		this.transform_result_title = transform_result_title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLast_updated_by() {
		return last_updated_by;
	}

	public void setLast_updated_by(String last_updated_by) {
		this.last_updated_by = last_updated_by;
	}

	public Timestamp getLast_updated_time() {
		return last_updated_time;
	}

	public void setLast_updated_time(Timestamp last_updated_time) {
		this.last_updated_time = last_updated_time;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public List<EvmRequirement> getEvmRequirements() {
		return evmRequirements;
	}

	public void setEvmRequirements(List<EvmRequirement> evmRequirements) {
		this.evmRequirements = evmRequirements;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getSubmissionPath() {
		return submissionPath;
	}

	public void setSubmissionPath(String submissionPath) {
		this.submissionPath = submissionPath;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
}
