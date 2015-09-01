package org.sdrc.evm.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.opendatakit.briefcase.model.XmlDocumentFetchException;
import org.sdrc.evm.model.WorkspaceDetailModel;
import org.sdrc.evm.model.WorkspaceModel;
import org.xml.sax.SAXException;

public interface WorkspaceService {

	List<WorkspaceModel> getWorkspaces(String areacode, String type) throws Exception;

	List<WorkspaceDetailModel> getXFormLog(String formID,String state,String district) throws IllegalArgumentException, IllegalStateException, XmlDocumentFetchException, IOException, SAXException, ParserConfigurationException;

	Map<String, String> getAreaCodeAndTypeByLoginUser();

	String getSingleSurveyViewOrEdit(String formID, String uui_id,int level)throws IllegalArgumentException, IllegalStateException, XmlDocumentFetchException, IOException, SAXException, ParserConfigurationException, TransformerException ;
}
