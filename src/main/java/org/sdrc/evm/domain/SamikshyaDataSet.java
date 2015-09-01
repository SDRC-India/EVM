package org.sdrc.evm.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="samikshya_dataset")
public class SamikshyaDataSet{

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pk_dataset")
	private int id;
	
	@OneToMany(mappedBy = "dataset",fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	private List<SamikshyaDataTable> listofdata;
	
	@Column(name="parent_area_code")
	private String parent_area_code;
	
	@Column(name="area_code")
	private String area_code;
	
	@Column(name="form_id")
	private String form_id;
	
	@Column(name="form_name")
	private String form_name;
	
	@Column(name="form_desc")
	private String form_desc;
	
	@Column(name="gpi")
	private String gpi;
	
	@Column(name="iria")
	private String iria;
	
	@Column(name="dps_sign")
	private String dps_sign;
	
	@Column(name="deo_sign")
	private String deo_sign;
	
	@Column(name="year")
	private String year;
	
	@Column(name="month")
	private String month;
	
	public SamikshyaDataSet(SamikshyaDataSet copyConst){
		this.id = copyConst.id;
		this.parent_area_code = copyConst.parent_area_code;
		this.area_code = copyConst.area_code;
		this.form_id = copyConst.form_id;
		this.form_name = copyConst.form_name;
		this.form_desc = copyConst.form_desc;
		this.gpi = copyConst.gpi;
		this.iria = copyConst.iria;
		this.dps_sign = copyConst.dps_sign;
		this.deo_sign = copyConst.deo_sign;
		this.year = copyConst.year;
		this.month = copyConst.month;
	}
	public SamikshyaDataSet(){}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getParent_area_code() {
		return parent_area_code;
	}
	public void setParent_area_code(String parent_area_code) {
		this.parent_area_code = parent_area_code;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getForm_id() {
		return form_id;
	}
	public void setForm_id(String form_id) {
		this.form_id = form_id;
	}
	public String getForm_name() {
		return form_name;
	}
	public void setForm_name(String form_name) {
		this.form_name = form_name;
	}
	public String getForm_desc() {
		return form_desc;
	}
	public void setForm_desc(String form_desc) {
		this.form_desc = form_desc;
	}
	public String getGpi() {
		return gpi;
	}
	public void setGpi(String gpi) {
		this.gpi = gpi;
	}
	public String getIria() {
		return iria;
	}
	public void setIria(String iria) {
		this.iria = iria;
	}
	public String getDps_sign() {
		return dps_sign;
	}
	public void setDps_sign(String dps_sign) {
		this.dps_sign = dps_sign;
	}
	public String getDeo_sign() {
		return deo_sign;
	}
	public void setDeo_sign(String deo_sign) {
		this.deo_sign = deo_sign;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public List<SamikshyaDataTable> getListofdata() {
		return listofdata;
	}
	public void setListofdata(List<SamikshyaDataTable> listofdata) {
		this.listofdata = listofdata;
	}

	
}
