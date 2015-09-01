package org.sdrc.odkaggregate.model;

import java.util.List;

public class FormCollection {

	private List<Form> forms;
	
	private int code;

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public List<Form> getForms() {
		return forms;
	}
	public void setForms(List<Form> forms) {
		this.forms = forms;
	}
}
