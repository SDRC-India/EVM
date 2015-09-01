package org.sdrc.evm.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.opendatakit.briefcase.model.XmlDocumentFetchException;
import org.sdrc.evm.domain.AreaMapper;
import org.sdrc.evm.model.User;
import org.sdrc.evm.model.UserRoleSchemeMapping;
import org.sdrc.evm.model.WorkspaceDetailModel;
import org.sdrc.evm.model.WorkspaceModel;
import org.sdrc.evm.repository.AreaMapperRepository;
import org.sdrc.evm.repository.CommonXFormRepository;
import org.sdrc.evm.repository.XFormRepository;
import org.sdrc.evm.util.Constants;
import org.sdrc.evm.util.ODKAggregateFormDetailUtil;
import org.sdrc.evm.util.StateManager;
import org.sdrc.odkaggregate.domain.CommonXForm;
import org.sdrc.odkaggregate.domain.XForm;
import org.sdrc.odkaggregate.gateway.ODKAggregateWay;
import org.sdrc.odkaggregate.gateway.ODKConstants;
import org.sdrc.odkaggregate.gateway.ViewSubmissionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;


@Service
public class WorkspaceServiceImpl implements WorkspaceService {

	@Autowired
	private StateManager stateManager;
	
	@Autowired
	private XFormRepository xFormRepository;
	
	@Autowired
	private AreaMapperRepository areaMapperRepository;
	
	@Autowired
	private ODKAggregateFormDetailUtil odkAggregateFormDetail;
	
	@Autowired
	private ODKAggregateWay odkBriefcaseGetWay;
	
	@Autowired
	private ViewSubmissionList viewSubmissionGetWay;
	
	@Autowired
	private CommonXFormRepository commonXFormRepository;
	
	@Override
	public List<WorkspaceModel> getWorkspaces(String areaCode, String type) throws Exception {

		List<CommonXForm> commonFormList = commonXFormRepository.getListOfXFormDetailByAreaCode(areaCode);
		List<WorkspaceModel> workspaceList = new  ArrayList<WorkspaceModel>();
		
		List<AreaMapper> area = areaMapperRepository.getAreaByAreaCode(areaCode);
		int count = 1;
		for(CommonXForm xform: commonFormList){
			WorkspaceModel model = new WorkspaceModel();
			model.setCount(count++);
			model.setState(area.get(0).getArea_name());
			model.setEnketo_url(xform.getUrl());
			model.setForm_id(xform.getForm_id());
			model.setForm_title(xform.getTransform_result_title());
			model.setServer_url(xform.getServer_url());
			model.setArea_id(areaCode);
			
			workspaceList.add(model);
		}
		
		return workspaceList;
	}

	@Override
	public List<WorkspaceDetailModel> getXFormLog(String formID,String state,String district) throws IllegalArgumentException, IllegalStateException, XmlDocumentFetchException, IOException, SAXException, ParserConfigurationException {
		
		List<WorkspaceDetailModel> logList = null;
		XForm xForm = xFormRepository.getXFormDetailsByFormID(formID) != null ? xFormRepository.getXFormDetailsByFormID(formID).get(0) : null;
		if(district!=null && district.length()>1)
		{
			List<AreaMapper> stateAreaCode = areaMapperRepository.getAreaCodeByName(state);
			List<AreaMapper> districtAreaCode = areaMapperRepository.getAreaCodeByNameAndParentCode(stateAreaCode.get(0).getChildCode(),district);
			
			logList = viewSubmissionGetWay.getSubmissionListLogs(state,district,districtAreaCode.get(0).getChildCode(), xForm,Constants.AREA_DISTRICT_LEVEL);
		}
		else{
			logList = viewSubmissionGetWay.getSubmissionListLogs(state,district,null, xForm,Constants.AREA_STATE_LEVEL);
		}
		return logList;
	}

	@Override
	public Map<String, String> getAreaCodeAndTypeByLoginUser() {
		User user= (User) stateManager.getValue(Constants.USER_PRINCIPAL);
		UserRoleSchemeMapping roleSchemeMapping=user.getUserRoleSchemeMappings().get(0);
		//find area code form User
		String areaCode=roleSchemeMapping.getRoleScheme().getAreaCode();
		System.out.println("Area----->>"+areaCode);
		
		Map<String, String> areacodeAndType = new HashMap<String, String>();
		areacodeAndType.put(areaCode, roleSchemeMapping.getRoleScheme().getSamikshyaRole().getRoleName());
		
		return areacodeAndType;
	}

	@Override
	public String getSingleSurveyViewOrEdit(String formID, String uui_id,int level) throws IllegalArgumentException, IllegalStateException, XmlDocumentFetchException, IOException, SAXException, ParserConfigurationException, TransformerException {
		XForm xForm = xFormRepository.getXFormDetailsByFormID(formID) != null ?xFormRepository.getXFormDetailsByFormID(formID).get(0) :null ;
		String response = viewSubmissionGetWay.getSingleSubmissionListEditURL(xForm, uui_id,level);
		try{
			int code = Integer.parseInt(response);
			switch(code){
				case 400:
	        	{
	        		return ODKConstants.ENKETO_ERROR_400;
	        	}
				case 401:
	        	{
	        		return ODKConstants.ENKETO_ERROR_401;
	        	}
				case 403:
	        	{
	        		return ODKConstants.ENKETO_ERROR_403;
	        	}
				case 404:
	        	{
	        		return ODKConstants.ENKETO_ERROR_404;
	        	}
				case 405:
	        	{
	        		return ODKConstants.ENKETO_ERROR_405;
	        	}
				case 410:
	        	{
	        		return ODKConstants.ENKETO_ERROR_410;
	        	}
	        	default:{
	        		return "ERROR CODE :"+code;
	        	}
			}
		}catch(Exception e){
			return response;
		}
	}

}
