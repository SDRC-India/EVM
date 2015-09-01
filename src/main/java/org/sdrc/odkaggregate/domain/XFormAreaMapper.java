package org.sdrc.odkaggregate.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="xform_area_areacode_mapper")
public class XFormAreaMapper {
	
	@Id
	@Column(name="area_code")
	private String areaCode;
	
	@Column(name="area_name")
	private String areaName;
	
	@Column(name="area_level")
	private String areaLevel;

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaLevel() {
		return areaLevel;
	}

	public void setAreaLevel(String areaLevel) {
		this.areaLevel = areaLevel;
	}

}
