package org.sdrc.evm.web;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sdrc.core.Authorize;
import org.sdrc.evm.model.AdminLog;
import org.sdrc.evm.model.MonitoringFormTran;
import org.sdrc.evm.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminFormController {

	List<MonitoringFormTran> auditList = null;

//	@ModelAttribute("auditList")
//	public List<MonitoringFormTran> auditList() {
//		if (auditList != null)
//			System.out.println("auditList current size : " + auditList.size());
//		return auditList;
//	}

	private AdminService adminService;

	@Autowired
	public AdminFormController(AdminService adminService) {
		this.adminService = adminService;
	}

	@Authorize(feature="Logs_Generation",permission="View")
	@RequestMapping(value = "/logs")
	public ModelAndView findalldata(Model model) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Date> dateMap = (Map<String, Date>) model.asMap().get("date");
		System.out.println(dateMap);
		ModelAndView modelAndView = new ModelAndView();
		if (dateMap != null) {
			auditList = adminService.findRecordByDate(new Timestamp(dateMap
					.get("startdate").getTime()),
					new Timestamp(dateMap.get("enddate").getTime()));
			modelAndView.addObject("auditList",auditList);
		} 
		else{
			
			modelAndView.addObject("auditList", adminService.findAll());
		}
		modelAndView.setViewName("adminauditform");
		return modelAndView;
	}

	@RequestMapping(value = "/adminauditform", method = RequestMethod.POST)
	public String finddata(@ModelAttribute("adminLog") AdminLog adminLog,
			BindingResult result, RedirectAttributes redirectAttributes)
			throws Exception {
		// System.out.println("Admin form controller is called");
		// System.out.println(adminLog.getStart());
		// System.out.println(adminLog.getEnd());
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		Date startdate = formatter.parse(adminLog.getStart());
		Date enddate = formatter.parse(adminLog.getEnd());
		// List<Date> dates=new ArrayList<Date>();
		// dates.add(startdate);
		Map<String, Date> dateMap = new HashMap<String, Date>();
		dateMap.put("startdate", startdate);
		dateMap.put("enddate", enddate);
		redirectAttributes.addFlashAttribute("date", dateMap);
		// auditList = adminService.findRecordByDate(
		// new Timestamp(startdate.getTime()),
		// new Timestamp(enddate.getTime()));
		return "redirect:/logs";
	}

}
