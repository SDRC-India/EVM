package org.sdrc.evm.model;

import java.util.Map;

/**
 * @author kamal
 * 
 */
public class AggregationDataModel {

	private String formType;
	private String districtName;
	private String facilityName;
	private Map<String, Double> dataValuesMap;
	private String imagePath;
	private String latitude;
	private String longitude;

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public Map<String, Double> getDataValuesMap() {
		return dataValuesMap;
	}

	public void setDataValuesMap(Map<String, Double> dataValuesMap) {
		this.dataValuesMap = dataValuesMap;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}
