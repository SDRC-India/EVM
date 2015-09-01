package org.sdrc.evm.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the samikshya_monitoring_form_tran database table.
 * 
 */
@Entity
@Table(name="samikshya_monitoring_form_tran")
@NamedQuery(name="SamikshyaMonitoringFormTran.findAll", query="SELECT s FROM SamikshyaMonitoringFormTran s")
public class SamikshyaMonitoringFormTran implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String formPath;
	private String lastUpdatedBy;
	private String timePeriod;
	private Timestamp lastUpdatedDate;
	private SamikshyaMonitoringForm samikshyaMonitoringForm;
	private String status;
	private String remarks;
	private boolean active;
	
	
	
//	/**
//	 * @return the isActive
//	 */
//	@Column(name="is_active")
//	public boolean isActive() {
//		return isActive;
//	}
//
//
//	/**
//	 * @param isActive the isActive to set
//	 */
//	public void setActive(boolean isActive) {
//		this.isActive = isActive;
//	}

	@Column(name="is_active")
	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	/**
	 * @return the status
	 */
	@Column(name="status")
	public String getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the remarks
	 */
	@Column(name="remarks")
	public String getRemarks() {
		return remarks;
	}


	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	/**
	 * @return the timePeriod
	 */
	@Column(name="time_period")
	public String getTimePeriod() {
		return timePeriod;
	}


	/**
	 * @param timePeriod the timePeriod to set
	 */
	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}

	

	public SamikshyaMonitoringFormTran() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Column(name="form_path")
	public String getFormPath() {
		return this.formPath;
	}

	public void setFormPath(String formPath) {
		this.formPath = formPath;
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
	
	


	//bi-directional many-to-one association to SamikshyaMonitoringForm
	@ManyToOne
	@JoinColumn(name="form_id")
	public SamikshyaMonitoringForm getSamikshyaMonitoringForm() {
		return this.samikshyaMonitoringForm;
	}

	public void setSamikshyaMonitoringForm(SamikshyaMonitoringForm samikshyaMonitoringForm) {
		this.samikshyaMonitoringForm = samikshyaMonitoringForm;
	}

}