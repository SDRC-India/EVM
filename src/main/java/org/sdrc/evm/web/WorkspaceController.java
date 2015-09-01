package org.sdrc.evm.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.sdrc.core.Authorize;
import org.sdrc.evm.model.WorkspaceDetailModel;
import org.sdrc.evm.service.WorkspaceService;
import org.sdrc.evm.service.XFormService;
import org.sdrc.evm.util.StateManager;
import org.sdrc.odkaggregate.gateway.ViewSubmissionList;
import org.sdrc.odkaggregate.model.FormCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class WorkspaceController {

	@Autowired
	private XFormService xservice;

	@Autowired
	private WorkspaceService wservice;
	
	@Autowired
	private ViewSubmissionList viewService;
	
	@Autowired
	private  StateManager stateManager;

	@RequestMapping(value = "/updateXFormDetail", method = RequestMethod.GET)
	public String getAllXlSFormFromByEnketoAPI(HttpServletRequest req)
			throws Exception {
		String forms = xservice.getAllForms();

		ObjectMapper mapper = new ObjectMapper();
		FormCollection collection = mapper.readValue(forms,
				FormCollection.class);

		String isError = xservice.insertXformDetail(collection);
		System.out.println(isError);

		return "success";
	}

	@Authorize(feature="Workspace",permission="View")
	@RequestMapping(value = "/workspace", method = RequestMethod.GET)
	public ModelAndView showUploadForm(HttpServletRequest req) throws Exception {
		
		ModelAndView modelAndView=new ModelAndView();
		Map<String, String> areaCodeAndType = wservice.getAreaCodeAndTypeByLoginUser();
		String areacode = areaCodeAndType.keySet().iterator().next();
		String type = areaCodeAndType.get(areacode);
		
		System.out.println(areacode+"::::::"+type);
		

		modelAndView.addObject("xformList", wservice.getWorkspaces(areacode,type));
		modelAndView.setViewName("workspace");
		return modelAndView;
	}

	/**
	 * Called by LOG button click
	 * @param formID
	 * @param state
	 * @param district
	 * @return
	 */
	@RequestMapping(value = "/getAllXFormHistoryRecord", method = RequestMethod.GET)
	public ModelAndView getAllXFormHistoryPerArea(
			@RequestParam("formID") String formID,
			@RequestParam("state") String state,
			@RequestParam("district") String district) {
		try{
			
		ModelAndView modelAndView = new ModelAndView();
		List<WorkspaceDetailModel> details = wservice.getXFormLog(formID, state, district);
		modelAndView.addObject("formDetails",details);
		modelAndView.addObject("formID",formID);
		
		if(details.size()>0){
			modelAndView.setViewName("xformLogs");
			return modelAndView;
		}
		else
			return null;
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Exception caught : "+e);
			return null;
		}
	}
	
	@RequestMapping(value = "/read_ASingleXformInstance", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView read_a_single_XformInstance(@RequestParam("formId") String formID,@RequestParam("id") String uui_id,@RequestParam("level") int level){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("xformDetails");
		try {
			String url = wservice.getSingleSurveyViewOrEdit(formID,uui_id,level);
			
			if(url.contains("http"))
				modelAndView.addObject("editURL", url);
			else
				modelAndView.addObject("error", url);
			return modelAndView;
		} catch (Exception e) {
			modelAndView.addObject("collection",null);
			return modelAndView;
		}
		
	}
}
