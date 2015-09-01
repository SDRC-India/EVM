package org.sdrc.odkaggregate.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="common_xform_mapping")
@NamedQuery(name="CommonXFormMapping.findAll", query="SELECT xform FROM CommonXFormMapping xform")
public class CommonXFormMapping {

	private int id;
	private String form_name;
	private String area_level;
	private CommonXForm commonXform;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Column(name="form_name")
	public String getForm_name() {
		return form_name;
	}
	public void setForm_name(String form_name) {
		this.form_name = form_name;
	}

	@Column(name="area_level")
	public String getArea_level() {
		return area_level;
	}
	public void setArea_level(String area_level) {
		this.area_level = area_level;
	}

	//bi-directional many-to-one association to CommonXForm table
	@ManyToOne
	@JoinColumn(name="common_form_id")
	public CommonXForm getCommonXform() {
		return commonXform;
	}
	public void setCommonXform(CommonXForm commonXform) {
		this.commonXform = commonXform;
	}
	
}
