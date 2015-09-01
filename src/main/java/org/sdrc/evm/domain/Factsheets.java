package org.sdrc.evm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="factsheets")
public class Factsheets {

	@Id
	@Column(name="area_id")
	private String area_id;
	
	@Column(name="area_name")
	private String name;
	
	@Column(name="area_parentId")
	private String area_parentId;
	
	@Column(name="factsheet")
	private String factsheet;
	
	@Column(name="strength")
	private String strength;
	
	@Column(name="improvement_plan")
	private String improvementPlan;
	
	public String getArea_parentId() {
		return area_parentId;
	}
	public void setArea_parentId(String area_parentId) {
		this.area_parentId = area_parentId;
	}
	public String getArea_id() {
		return area_id;
	}
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFactsheet() {
		return factsheet;
	}
	public void setFactsheet(String factsheet) {
		this.factsheet = factsheet;
	}
	public String getStrength() {
		return strength;
	}
	public void setStrength(String strength) {
		this.strength = strength;
	}
	public String getImprovementPlan() {
		return improvementPlan;
	}
	public void setImprovementPlan(String improvementPlan) {
		this.improvementPlan = improvementPlan;
	}
	
}
