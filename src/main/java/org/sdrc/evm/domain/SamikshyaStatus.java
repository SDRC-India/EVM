package org.sdrc.evm.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the samikshya_area_mapper database table.
 * 
 */
@Entity
@Table(name="samikshya_status")
@NamedQuery(name="SamikshyaStatus.findAll", query="SELECT s FROM SamikshyaStatus s")
public class SamikshyaStatus implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	private int id;
	private String statusCode;
	private String statusDisplayName;
	
	/**
	 * @return the statusId
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getStatusId() {
		return id;
	}
	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(int id) {
		this.id = id;
	}
	/**
	 * @return the statusCode
	 */
	@Column(name="status_code")
	public String getStatusCode() {
		return statusCode;
	}
	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * @return the statusDisplayName
	 */
	@Column(name="status_display_name")
	public String getStatusDisplayName() {
		return statusDisplayName;
	}
	/**
	 * @param statusDisplayName the statusDisplayName to set
	 */
	public void setStatusDisplayName(String statusDisplayName) {
		this.statusDisplayName = statusDisplayName;
	}
	
	
	

}
