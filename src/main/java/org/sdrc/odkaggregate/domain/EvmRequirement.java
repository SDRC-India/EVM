package org.sdrc.odkaggregate.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="evm_requirement")
public class EvmRequirement {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="evm_requirement_id")
	private int id;
	
	@Column(name="indicator_name")
	private String indicatorName;
	
	@Column(name="xpath")
	private String xpath;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="evm_requirement_id")
	private List<EvmQuestion> evmQuestions;
	
	@ManyToMany(fetch = FetchType.LAZY,mappedBy="evmRequirements")
	private List<XForm> xForms;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "requirement_indicator_info_mapping", joinColumns = { 
			@JoinColumn(name = "evm_requirement_id", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "indicator_info_id", 
					nullable = false, updatable = false) })
	private List<IndicatorInfo> indicatorInfos;

	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public List<EvmQuestion> getEvmQuestions() {
		return evmQuestions;
	}

	public void setEvmQuestions(List<EvmQuestion> evmQuestions) {
		this.evmQuestions = evmQuestions;
	}

	public List<XForm> getxForms() {
		return xForms;
	}

	public void setxForms(List<XForm> xForms) {
		this.xForms = xForms;
	}

}
