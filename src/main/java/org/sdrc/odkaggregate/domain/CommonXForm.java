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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the common_xform table.
 * 
 */
@Entity
@Table(name="common_xform")
@NamedQuery(name="CommonXForm.findAll", query="SELECT xform FROM CommonXForm xform")
public class CommonXForm {
	
	private int id;
	private String server_url;
	private String form_id;
	private String transform_result_title;
	private String url;
	private String area_code;
	private String status;
	private String version;
	private String last_updated_by;
	private Timestamp last_updated_time;
	private Boolean active;
	private List<CommonXFormMapping> commonXFormMappingList;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="server_url")
	public String getServer_url() {
		return server_url;
	}
	public void setServer_url(String server_url) {
		this.server_url = server_url;
	}
	
	@Column(name="form_id")
	public String getForm_id() {
		return form_id;
	}
	public void setForm_id(String form_id) {
		this.form_id = form_id;
	}
	
	@Column(name="transform_result_title")
	public String getTransform_result_title() {
		return transform_result_title;
	}
	public void setTransform_result_title(String transform_result_title) {
		this.transform_result_title = transform_result_title;
	}
	
	@Column(name="url")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name="area_code")
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	
	@Column(name="status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="version")
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	@Column(name="last_updated_by")
	public String getLast_updated_by() {
		return last_updated_by;
	}
	public void setLast_updated_by(String last_updated_by) {
		this.last_updated_by = last_updated_by;
	}
	
	@Column(name="last_updated_time")
	public Timestamp getLast_updated_time() {
		return last_updated_time;
	}
	public void setLast_updated_time(Timestamp last_updated_time) {
		this.last_updated_time = last_updated_time;
	}
	
	@Column(name="active")
	public Boolean isActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy="commonXform",cascade = CascadeType.ALL)
	public List<CommonXFormMapping> getCommonXFormMappingList() {
		return commonXFormMappingList;
	}
	public void setCommonXFormMappingList(
			List<CommonXFormMapping> commonXFormMappingList) {
		this.commonXFormMappingList = commonXFormMappingList;
	}
	
}
