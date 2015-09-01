package org.sdrc.evm.web;

import java.util.List;

import org.sdrc.evm.model.FactsheetsModel;
import org.sdrc.evm.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FactsheetsController {
	
	@Autowired
	private DocumentService documentService;
	
//	@Authorize(feature="Workspace",permission="View")
	@RequestMapping(value = "/factsheets", method = RequestMethod.GET)
    public ModelAndView factsheets(Model model) throws Exception {
        String areaId = (String) model.asMap().get("areaId");
		
		 List<FactsheetsModel> factsheetList = documentService.getFactsheets(areaId);
		 ModelAndView modelAndView = new ModelAndView();
		 modelAndView.addObject("factsheetList",factsheetList);
		 modelAndView.setViewName("factsheet");
		 return modelAndView;
	}
	
}
