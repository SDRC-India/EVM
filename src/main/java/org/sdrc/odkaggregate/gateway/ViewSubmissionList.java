package org.sdrc.odkaggregate.gateway;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.opendatakit.briefcase.model.XmlDocumentFetchException;
import org.sdrc.evm.model.WorkspaceDetailModel;
import org.sdrc.evm.model.XFormCollection;
import org.sdrc.odkaggregate.domain.XForm;
import org.xml.sax.SAXException;

public interface ViewSubmissionList {

	public List<XFormCollection> getAllSubmissionList(XForm xForm,int level) throws XmlDocumentFetchException, IllegalArgumentException, IllegalStateException, IOException , SAXException , ParserConfigurationException ;
	public List<WorkspaceDetailModel> getSubmissionListLogs(String state,String district,String areaCode,XForm xForm,int level) throws XmlDocumentFetchException, IllegalArgumentException, IllegalStateException, IOException , SAXException , ParserConfigurationException ;
	public String getSingleSubmissionListEditURL(XForm xForm,String instance_id,int level)  throws ProtocolException, MalformedURLException ,IOException, XmlDocumentFetchException, SAXException, ParserConfigurationException, TransformerException;
}
