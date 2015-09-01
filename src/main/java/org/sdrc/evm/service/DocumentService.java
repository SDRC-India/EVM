package org.sdrc.evm.service;

import java.util.List;

import org.sdrc.evm.domain.Factsheets;
import org.sdrc.evm.model.FactsheetsModel;

public interface DocumentService {
	List<String> searchAllResources(int level);
	List<String> searchAllSop(int level);
	List<String> searchAllAssessmentTools(int level);
	List<String> searchAllUserGuides(int level);
	List<FactsheetsModel> getFactsheets(String areaId) throws Exception;
}
