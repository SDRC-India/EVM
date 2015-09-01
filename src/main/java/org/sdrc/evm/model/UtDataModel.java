package org.sdrc.evm.model;

import java.util.List;

public class UtDataModel {
 private String id;
 private String areaCode;
 private String areaName;
 private String value;
 private String unit;
 private String rank;
 private String cssClass;
 private String percentageChange;
 private Boolean isPositiveTrend;
 private List<ValueObject> dataSeries;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
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
public String getValue() {
	return value;
}
public void setValue(String value) {
	this.value = value;
}
public String getUnit() {
	return unit;
}
public void setUnit(String unit) {
	this.unit = unit;
}
public String getRank() {
	return rank;
}
public void setRank(String rank) {
	this.rank = rank;
}
public String getCssClass() {
	return cssClass;
}
public void setCssClass(String cssClass) {
	this.cssClass = cssClass;
}
public String getPercentageChange() {
	return percentageChange;
}
public void setPercentageChange(String percentageChange) {
	this.percentageChange = percentageChange;
}
public Boolean getIsPositiveTrend() {
	return isPositiveTrend;
}
public void setIsPositiveTrend(Boolean isPositiveTrend) {
	this.isPositiveTrend = isPositiveTrend;
}
public List<ValueObject> getDataSeries() {
	return dataSeries;
}
public void setDataSeries(List<ValueObject> dataSeries) {
	this.dataSeries = dataSeries;
}
}

