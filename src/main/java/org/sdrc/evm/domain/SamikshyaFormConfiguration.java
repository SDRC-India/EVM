package org.sdrc.evm.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="samikshya_form_configuration")
@NamedQuery(name="SamikshyaFormConfiguration.findAll", query="SELECT s FROM SamikshyaFormConfiguration s")
public class SamikshyaFormConfiguration implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int deadline;
	private int frequency;
	private String formType;
	private String editAvailability;
	
	
	
	/**
	 * @return the editAvailability
	 */
	@Column(name="edit_availability")
	public String getEditAvailability() {
		return editAvailability;
	}



	/**
	 * @param editAvailability the editAvailability to set
	 */
	public void setEditAvailability(String editAvailability) {
		this.editAvailability = editAvailability;
	}



	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}



	/**
	 * @return the deadline
	 */
	@Column(name="deadline")
	public int getDeadline() {
		return deadline;
	}



	/**
	 * @param deadline the deadline to set
	 */
	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}



	/**
	 * @return the frequency
	 */
	@Column(name="frequency")
	public int getFrequency() {
		return frequency;
	}


	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}



	/**
	 * @return the formType
	 */
	@Column(name="form_type")
	public String getFormType() {
		return formType;
	}



	/**
	 * @param formType the formType to set
	 */
	public void setFormType(String formType) {
		this.formType = formType;
	}



	public SamikshyaFormConfiguration(){
		
	}

}
