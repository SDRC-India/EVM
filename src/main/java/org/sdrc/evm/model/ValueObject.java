package org.sdrc.evm.model;

public class ValueObject implements Comparable<ValueObject> {
	private String key;
	private Object value;
	private String description;
	private String shortName;
	public ValueObject(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	public ValueObject(int key, String value) {
		this(new Integer(key).toString(),value);
	}

	public ValueObject() {
		// TODO Auto-generated constructor stub
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Override
	public int compareTo(ValueObject ob) {
		// TODO Auto-generated method stub
		return this.getValue().toString().compareTo(ob.getValue().toString());
	}
}
