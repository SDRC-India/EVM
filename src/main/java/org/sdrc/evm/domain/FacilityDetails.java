package org.sdrc.evm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.*;

@Entity
@Table(name="facility_details")
public class FacilityDetails {
	
	@Id
	@Column(name="area_id")
	private String areaId;
	@Column(name="area_name")
	private String areaName;
	@Column(name="longitude")
	private Double longitude;
	@Column(name="latitude")
	private Double latitude;
	@Column(name="altitude")
	private Double altitude;
	@Column(name="images")
	private String images;
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getAltitude() {
		return altitude;
	}
	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}

	
}
