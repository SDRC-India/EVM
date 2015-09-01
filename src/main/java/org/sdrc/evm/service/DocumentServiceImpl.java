package org.sdrc.evm.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.sdrc.evm.domain.Factsheets;
import org.sdrc.evm.model.FactsheetsModel;
import org.sdrc.evm.repository.FactsheetsRepository;
import org.sdrc.evm.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService{
	
	@Autowired
	private ServletContext context;
	
	@Autowired
	private FactsheetsRepository factsheetsRepository;
	
	@Autowired
	private ResourceBundleMessageSource workspaceMessageSource;
	
	@Override
	public List<String> searchAllResources(int level) {
		List<String> resourcesList = new ArrayList<String>();
		String full_path = context.getRealPath("");
		File dir = new File(full_path + "\\resources\\RESOURCES\\");
		if (dir.isDirectory() == false) {
			System.out.println("Directory does not exists : " + full_path);
		}
		String[] list = dir.list();

		for (String file : list) {
			if (level != 0) {
				if (resourcesList.size() == level)
					break;
			}
			resourcesList.add(file.toString());
		}
		return resourcesList;
	}     
	
	
//	------------------------------------------------------
	
	@Override
	public List<String> searchAllSop(int level) {
		List<String> resourcesList = new ArrayList<String>();
		String full_path = context.getRealPath("");
		File dir = new File(full_path + "\\resources\\SOP\\");
		if (dir.isDirectory() == false) {
			System.out.println("Directory does not exists : " + full_path);
		}
		String[] list = dir.list();

		for (String file : list) {
			if (level != 0) {
				if (resourcesList.size() == level)
					break;
			}
			resourcesList.add(file.toString());
		}
		return resourcesList;
	}


	@Override
	public List<String> searchAllAssessmentTools(int level) {
		List<String> assessmentToolsList = new ArrayList<String>();
		String full_path = context.getRealPath("");
		File dir = new File(full_path + "\\resources\\TOOLS\\ASSESSMENT TOOLS");
		if (dir.isDirectory() == false) {
			System.out.println("Directory does not exists : " + full_path);
		}
		String[] list = dir.list();

		for (String file : list) {
			if (level != 0) {
				if (assessmentToolsList.size() == level)
					break;
			}
			assessmentToolsList.add(file.toString());
		}
		return assessmentToolsList;
	}

	@Override
	public List<String> searchAllUserGuides(int level) {
		List<String> userGuidesList = new ArrayList<String>();
		String full_path = context.getRealPath("");
		File dir = new File(full_path + "\\resources\\TOOLS\\USER GUIDES");
		if (dir.isDirectory() == false) {
			System.out.println("Directory does not exists : " + full_path);
		}
		String[] list = dir.list();

		for (String file : list) {
			if (level != 0) {
				if (userGuidesList.size() == level)
					break;
			}
			userGuidesList.add(file.toString());
		}
		return userGuidesList;
	}
//	-----------------------------------
	@Override
	public List<FactsheetsModel> getFactsheets(String areaId)throws Exception {
		List<FactsheetsModel> factSheetList= new ArrayList<FactsheetsModel>();
		List<Factsheets> factsheetList = factsheetsRepository.getFactsheets(areaId);
		for(Factsheets factsheet : factsheetList){
			
			FactsheetsModel factSheetModel = new FactsheetsModel();
			
			factSheetModel.setArea_id(factsheet.getArea_id());
			factSheetModel.setName(factsheet.getName());
			factSheetModel.setFactsheet(factsheet.getFactsheet() != null && !factsheet.getFactsheet().trim().isEmpty() ? workspaceMessageSource.getMessage(Constants.FACTSHEETS_PATH, null, null).concat(factsheet.getFactsheet()) : "");
			factSheetModel.setStrength(factsheet.getStrength() != null && !factsheet.getStrength().trim().isEmpty() ? workspaceMessageSource.getMessage(Constants.FACTSHEETS_PATH, null, null).concat(factsheet.getStrength()) : "");
			factSheetModel.setImprovementPlan(factsheet.getImprovementPlan() != null && !factsheet.getImprovementPlan().trim().isEmpty() ? workspaceMessageSource.getMessage(Constants.FACTSHEETS_PATH, null, null).concat(factsheet.getImprovementPlan()) : "");
			
			factSheetList.add(factSheetModel);
		}
		return factSheetList;
	}

}
