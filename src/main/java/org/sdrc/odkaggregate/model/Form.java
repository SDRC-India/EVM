package org.sdrc.odkaggregate.model;

public class Form {

	private int id;
	
	private String server_url;
	
	private String form_id;
	
	private String transform_result_title;
	
	private String url;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getServer_url() {
		return server_url;
	}
	public void setServer_url(String server_url) {
		this.server_url = server_url;
	}
	public String getForm_id() {
		return form_id;
	}
	public void setForm_id(String form_id) {
		this.form_id = form_id;
	}
	public String getTransform_result_title() {
		return transform_result_title;
	}
	public void setTransform_result_title(String transform_result_title) {
		this.transform_result_title = transform_result_title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
