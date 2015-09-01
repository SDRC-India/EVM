package org.sdrc.evm.model;

import java.util.List;

public class EvmQuestionModel {

	private String name;
	private Double value;
	private Double weight;
	private List<EvmDataNode> dataNodes;

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

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public List<EvmDataNode> getDataNodes() {
		return dataNodes;
	}

	public void setDataNodes(List<EvmDataNode> dataNodes) {
		this.dataNodes = dataNodes;
	}

	@Override
	public String toString() {
		return "EvmQuestionModel [name=" + name + ", value=" + value
				+ ", weight=" + weight + ", dataNodes=" + dataNodes + "]";
	}

}
