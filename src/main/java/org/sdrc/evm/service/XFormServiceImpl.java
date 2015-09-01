package org.sdrc.evm.service;

import java.sql.Timestamp;
import java.util.Date;

import org.sdrc.evm.repository.XFormRepository;
import org.sdrc.odkaggregate.domain.XForm;
import org.sdrc.odkaggregate.gateway.AggregateFormGetway;
import org.sdrc.odkaggregate.model.Form;
import org.sdrc.odkaggregate.model.FormCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XFormServiceImpl implements XFormService{

	@Autowired
	private AggregateFormGetway gateWay;
	
	@Autowired
	private XFormRepository xFormRepository;
	
	@Override
	public String getAllForms() throws Exception{
		return gateWay.getAllForms();
	}

	@Override
	public String insertXformDetail(FormCollection collection) throws Exception {
		String message = "Form ID : ";
		try {
			for(Form form:collection.getForms()){
				XForm obj = new XForm();
				obj.setForm_id(form.getForm_id());
				obj.setServer_url(form.getServer_url());
				obj.setTransform_result_title(form.getTransform_result_title());
				obj.setUrl(form.getUrl());
				obj.setLast_updated_by("system");
				obj.setLast_updated_time(new Timestamp(new Date().getTime()));
				obj.setActive(true);
				try {
					xFormRepository.save(obj);
				} catch (Exception e) {
					e.printStackTrace();
					message+=","+obj.getForm_id();
				}
				
			}
			return message;
		} catch (Exception e) {
			System.out.println("Exceptin in inserting xform data"+e);
			e.printStackTrace();
			return "fail";
		}
	}

}
