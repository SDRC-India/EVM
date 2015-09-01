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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "evm_question")
public class EvmQuestion {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="xform_question_id")
	private int id;
	
	@Column(name="question_name")
	private String questionName;
	
	@Column(name="xpath")
	private String xpath;
	
	@ManyToOne
	@JoinColumn(name="evm_requirement_id")
	private EvmRequirement evmRequirement;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="ev_question_id")
	private List<EvmSubQuestion> evmSubQuestions;
	
	@Column(name="weightage")
	private Double weightage;
	
	@Column(name="question_type")
	private String questionType;
	
	@Column(name="weightage_relevant")
	private String weightageRelevant;
	
	@Column(name="exceptionrule")
	private String exceptionrule;
	
	@Column(name="weightage_type")
	private String weightagetype;
	
	@Column(name="clssification")
	private String clssification;
	
	@Column(name="devinfo_classification_name")
	private String devinfoclassificationName;
	
//	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinTable(name = "question_classification_mapping", joinColumns = { 
//			@JoinColumn(name = "xform_question_id", nullable = false, updatable = false) }, 
//			inverseJoinColumns = { @JoinColumn(name = "classification_id", 
//					nullable = false, updatable = false) })
//	private List<Classification> classifications;

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public EvmRequirement getEvmRequirement() {
		return evmRequirement;
	}

	public void setEvmRequirement(EvmRequirement evmRequirement) {
		this.evmRequirement = evmRequirement;
	}

	public List<EvmSubQuestion> getEvmSubQuestions() {
		return evmSubQuestions;
	}

	public void setEvmSubQuestions(List<EvmSubQuestion> evmSubQuestions) {
		this.evmSubQuestions = evmSubQuestions;
	}

	public Double getWeightage() {
		return weightage;
	}

	public void setWeightage(Double weightage) {
		this.weightage = weightage;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getWeightageRelevant() {
		return weightageRelevant;
	}

	public void setWeightageRelevant(String weightageRelevant) {
		this.weightageRelevant = weightageRelevant;
	}

	public String getWeightagetype() {
		return weightagetype;
	}

	public void setWeightagetype(String weightagetype) {
		this.weightagetype = weightagetype;
	}
	
	public String getExceptionrule() {
		return exceptionrule;
	}

	public void setExceptionrule(String exceptionrule) {
		this.exceptionrule = exceptionrule;
	}

	public String getClssification() {
		return clssification;
	}

	public void setClssification(String clssification) {
		this.clssification = clssification;
	}

	public String getDevinfoclassificationName() {
		return devinfoclassificationName;
	}

	public void setDevinfoclassificationName(String devinfoclassificationName) {
		this.devinfoclassificationName = devinfoclassificationName;
	}

	

//	public List<Classification> getClassifications() {
//		return classifications;
//	}
//
//	public void setClassifications(List<Classification> classifications) {
//		this.classifications = classifications;
//	}

}
