package org.sdrc.evm.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the samikshya_area_mapper database table.
 * 
 */
@Entity
@Table(name="samikshya_area_mapper")
@NamedQuery(name="SamikshyaAreaMapper.findAll", query="SELECT s FROM SamikshyaAreaMapper s")
public class SamikshyaAreaMapper implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String childCode;
	private String parentCode;

	public SamikshyaAreaMapper() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Column(name="child_code")
	public String getChildCode() {
		return this.childCode;
	}

	public void setChildCode(String childCode) {
		this.childCode = childCode;
	}


	@Column(name="parent_code")
	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

}