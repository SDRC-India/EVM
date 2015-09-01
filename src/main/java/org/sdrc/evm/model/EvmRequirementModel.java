package org.sdrc.evm.model;

import java.util.List;

public class EvmRequirementModel {

	private String name;
	private Double value;
	private List<EvmQuestionModel> evmQuestionModels;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public List<EvmQuestionModel> getEvmQuestionModels() {
		return evmQuestionModels;
	}

	public void setEvmQuestionModels(List<EvmQuestionModel> evmQuestionModels) {
		this.evmQuestionModels = evmQuestionModels;
	}

}
