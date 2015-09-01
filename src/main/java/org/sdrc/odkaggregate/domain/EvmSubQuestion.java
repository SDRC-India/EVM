package org.sdrc.odkaggregate.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="evm_sub_question")
public class EvmSubQuestion {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="question_name")
	private String questionName;
	
	@Column(name="xpath")
	private String xpath;
	
	@Column(name="exceptionrule")
	private String exceptionrule;
	

	@ManyToOne
	@JoinColumn(name="ev_question_id")
	private EvmQuestion evmQuestion;

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

	public EvmQuestion getEvmQuestion() {
		return evmQuestion;
	}

	public void setEvmQuestion(EvmQuestion evmQuestion) {
		this.evmQuestion = evmQuestion;
	}


	public String getExceptionrule() {
		return exceptionrule;
	}

	public void setExceptionrule(String exceptionrule) {
		this.exceptionrule = exceptionrule;
	}
}
