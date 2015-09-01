package org.sdrc.evm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="samikshya_datatable")
public class SamikshyaDataTable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pk_datatable")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "fk_dataset")
	private SamikshyaDataSet dataset;
	
	@Column(name="obs_value")
	private double obs_value;
	
	@Column(name="obs_status")
	private String obs_status;
	
	@Column(name="obs_unit")
	private String obs_unit;
	
	@Column(name="indicator_code")
	private String indicator_code;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getObs_value() {
		return obs_value;
	}

	public void setObs_value(double obs_value) {
		this.obs_value = obs_value;
	}

	public String getObs_status() {
		return obs_status;
	}

	public void setObs_status(String obs_status) {
		this.obs_status = obs_status;
	}

	public String getObs_unit() {
		return obs_unit;
	}

	public void setObs_unit(String obs_unit) {
		this.obs_unit = obs_unit;
	}

	public String getIndicator_code() {
		return indicator_code;
	}

	public void setIndicator_code(String indicator_code) {
		this.indicator_code = indicator_code;
	}

	public SamikshyaDataSet getDataset() {
		return dataset;
	}

	public void setDataset(SamikshyaDataSet dataset) {
		this.dataset = dataset;
	}

}
