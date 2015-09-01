package org.sdrc.odkaggregate.gateway;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.kxml2.io.KXmlSerializer;
import org.opendatakit.briefcase.model.DocumentDescription;
import org.opendatakit.briefcase.model.ServerConnectionInfo;
import org.opendatakit.briefcase.model.TerminationFuture;
import org.opendatakit.briefcase.model.XmlDocumentFetchException;
import org.opendatakit.briefcase.util.AggregateUtils;
import org.opendatakit.briefcase.util.WebUtils;
import org.sdrc.evm.model.WorkspaceDetailModel;
import org.sdrc.evm.model.XFormCollection;
import org.sdrc.evm.util.StateManager;
import org.sdrc.odkaggregate.domain.XForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

@Component
public class ViewSubmissionListImpl implements ViewSubmissionList {

	@Autowired
	private ResourceBundleMessageSource applicationMessageSource;

	@Autowired
	private ODKAggregateWay aggregateGetWay;

	@Autowired
	private StateManager sateManager;

	@Autowired
	private FormXML formXML;

	@Override
	public List<XFormCollection> getAllSubmissionList(XForm xForm, int level)
			throws XmlDocumentFetchException, IllegalArgumentException,
			IllegalStateException, IOException, SAXException,
			ParserConfigurationException {
		AggregateUtils.DocumentFetchResult result = null;
		XmlSerializer serializer = new KXmlSerializer();
		XPath xPath = XPathFactory.newInstance().newXPath();
		String formRooTitle = "";
		List<XFormCollection> collectionList = new ArrayList<XFormCollection>();

		StringWriter id_list = new StringWriter();
		StringWriter base_xlsForm = formXML.getXML(xForm);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document core_xml_doc = dBuilder.parse(new InputSource(
				new ByteArrayInputStream(base_xlsForm.toString().getBytes(
						"utf-8"))));
		if (core_xml_doc != null) {
			core_xml_doc.getDocumentElement().normalize();
			Element eElement = (Element) core_xml_doc.getElementsByTagName(
					"group").item(0);
			formRooTitle = eElement.getAttribute("ref").split("/")[1];
		}
		if (sateManager.getValue(xForm.getForm_id()) == null)
			sateManager.setValue(xForm.getForm_id(), formRooTitle);

		String baseUrl = xForm.getServer_url()
				+ ODKConstants.VIEW_SUBMISSION_LIST_PATH;
		Map<String, String> params = new HashMap<String, String>();
		params.put("formId", xForm.getForm_id());
		params.put("cursor", "");// websafeCursorString
		params.put("numEntries", "");
		String fullUrl = WebUtils.createLinkWithProperties(baseUrl, params);

		ServerConnectionInfo serverInfo = new ServerConnectionInfo(
				xForm.getServer_url(), xForm.getUsername(),
				xForm.getPassword().toCharArray());
		DocumentDescription submissionDescription = new DocumentDescription(
				"Fetch of manifest failed. Detailed reason: ",
				"Fetch of manifest failed ", "form manifest",
				new TerminationFuture());
		result = AggregateUtils.getXmlDocument(fullUrl, serverInfo, false,
				submissionDescription, null);
		serializer.setOutput(id_list);
		result.doc.write(serializer);

		Document doc_id_list = dBuilder
				.parse(new InputSource(new ByteArrayInputStream(id_list
						.toString().getBytes("utf-8"))));

		if (doc_id_list != null) {
			doc_id_list.getDocumentElement().normalize();

			NodeList nodeIdList = doc_id_list.getElementsByTagName("id");
			for (int node_no = 0; node_no < nodeIdList.getLength(); node_no++) {
				String instance_id = nodeIdList.item(node_no).getFirstChild()
						.getNodeValue();
				XFormCollection collection = new XFormCollection();
				collection.setForm_id(xForm.getForm_id());
				collection.setInstanceID(instance_id);

				try {
					String submission_xml_url = xForm.getServer_url()
							+ ODKConstants.DOWNLOAD_FORM_URL;
					String link_formID = generateFormID(xForm.getForm_id(), formRooTitle,
							instance_id);
					Map<String, String> submiteParams = new HashMap<String, String>();
					submiteParams.put("formId", link_formID);
					String full_url = WebUtils.createLinkWithProperties(
							submission_xml_url, submiteParams);

					serializer = new KXmlSerializer();
					StringWriter data_writer = new StringWriter();
					result = AggregateUtils.getXmlDocument(full_url,
							serverInfo, false, submissionDescription, null);
					serializer.setOutput(data_writer);
					result.doc.write(serializer);

					Document submission_doc = dBuilder.parse(new InputSource(
							new ByteArrayInputStream(data_writer.toString()
									.getBytes("utf-8"))));

					if (submission_doc != null) {
						submission_doc.getDocumentElement().normalize();
						Element rootElement = (Element) submission_doc
								.getElementsByTagName(formRooTitle).item(0);
						if (level == 2) {
							NodeList nodeList = (NodeList) xPath.compile(
									ODKConstants.VIEW_PREFIX
											+ applicationMessageSource
													.getMessage("area.xpath."
															+ xForm.getForm_id(), null,
															null)).evaluate(
									submission_doc, XPathConstants.NODESET);
							collection.setAreaCode(nodeList.item(0)
									.getFirstChild().getNodeValue());
						}
						NodeList district_nodeList = (NodeList) xPath.compile(
								ODKConstants.VIEW_PREFIX
										+ applicationMessageSource.getMessage(
												"xpath.area." + xForm.getForm_id(), null,
												null)).evaluate(submission_doc,
								XPathConstants.NODESET);
						collection.setDistrict(district_nodeList.item(0)
								.getFirstChild().getNodeValue());

						NodeList facility_nodeList = (NodeList) xPath.compile(
								ODKConstants.VIEW_PREFIX
										+ applicationMessageSource.getMessage(
												"xpath.facility." + xForm.getForm_id(),
												null, null)).evaluate(
								submission_doc, XPathConstants.NODESET);
						collection.setFacility(facility_nodeList.item(0)
								.getFirstChild().getNodeValue());

						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						collection.setSubmission_date(format.format(WebUtils
								.parseDate(rootElement
										.getAttribute("submissionDate"))));
						collectionList.add(collection);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return collectionList;
	}

	@Override
	public String getSingleSubmissionListEditURL(XForm xForm,
			String instance_id, int level) throws ProtocolException,
			MalformedURLException, IOException, XmlDocumentFetchException,
			SAXException, ParserConfigurationException, TransformerException {
		String xml_form_title = "";
		
		if (sateManager.getValue(xForm.getForm_id()) != null) {
			xml_form_title = (String) sateManager.getValue(xForm.getForm_id());
		} else {
			
			getAllSubmissionList(xForm, level);
		}
		String instance_xml = getSubmisionXML(xForm, xml_form_title,
				instance_id);
//		new FileOutputStream(new File("D:/xform.xml")).write(instance_xml.getBytes());
		return aggregateGetWay.enketoAPIPostAnInstanceForEditing(xForm,
				xml_form_title, instance_id, instance_xml);
	}

	@Override
	public List<WorkspaceDetailModel> getSubmissionListLogs(String state,
			String district, String areaCode, XForm xForm, int level)
			throws XmlDocumentFetchException, IllegalArgumentException,
			IllegalStateException, IOException, SAXException,
			ParserConfigurationException {

		List<WorkspaceDetailModel> logModelList = null;
		List<XFormCollection> collectionList = null;
		String areaName = (district != null && !district.equals("")) ? district
				: state;

		collectionList = getAllSubmissionList(xForm, level);
		logModelList = generateLogbyArea(collectionList,xForm.getForm_id(), areaName,
				level);
		return logModelList;
	}

	public List<WorkspaceDetailModel> generateLogbyArea(
			List<XFormCollection> collectionList, String formID,
			String areaName, int level) {

		List<WorkspaceDetailModel> logModelList = new ArrayList<WorkspaceDetailModel>();
		int count = 1;
		for (XFormCollection collection : collectionList) {
			WorkspaceDetailModel logModel = new WorkspaceDetailModel();
			if (level == 2) {
				logModel.setFormID(formID);
				logModel.setAreaName(areaName);
				logModel.setSubmission_date(collection.getSubmission_date());
				logModel.setId(collection.getInstanceID());
				logModel.setLevel(level);
				logModel.setDistrict(collection.getDistrict());
				logModel.setFacility(collection.getFacility());
				logModel.setCount(count++);
				logModelList.add(logModel);
			} else if (level == 1) {
				logModel.setFormID(formID);
				logModel.setAreaName(areaName);
				logModel.setSubmission_date(collection.getSubmission_date());
				logModel.setId(collection.getInstanceID());
				logModel.setDistrict(collection.getDistrict());
				logModel.setFacility(collection.getFacility());
				logModel.setCount(count++);
				logModelList.add(logModel);
				logModel.setLevel(level);
			}// else end
		}
		return logModelList;
	}

	private String generateFormID(String formID, String form_titile, String key) {
		return formID + "[@version=null and @uiVersion=null]/" + form_titile
				+ "" + "[@key=" + key + "]";
	}

	public String getSubmisionXML(XForm xForm, String xml_form_title,
			String instance_id) throws XmlDocumentFetchException, IOException,
			SAXException, ParserConfigurationException, TransformerException {

		String baseURL = xForm.getServer_url()
				+ ODKConstants.DOWNLOAD_FORM_URL;
		String unique_formID = generateFormID(xForm.getForm_id(), xml_form_title,
				instance_id);

		ServerConnectionInfo serverInfo = new ServerConnectionInfo(
				xForm.getServer_url(), xForm.getUsername(),
				xForm.getPassword().toCharArray());
		Map<String, String> params = new HashMap<String, String>();
		params.put("formId", unique_formID);
		String fullUrl = WebUtils.createLinkWithProperties(baseURL, params);

		DocumentDescription submissionDescription = new DocumentDescription(
				"Fetch of a submission failed.  Detailed error: ",
				"Fetch of a submission failed.", "submission",
				new TerminationFuture());
		AggregateUtils.DocumentFetchResult result = AggregateUtils
				.getXmlDocument(fullUrl, serverInfo, false,
						submissionDescription, null);

		XmlSerializer serializer = new KXmlSerializer();
		StringWriter writer = new StringWriter();
		serializer.setOutput(writer);
		result.doc.write(serializer);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(
				writer.toString().getBytes("utf-8"))));
		String submissionXML = "";
		if (doc != null) {
			doc.getDocumentElement().normalize();
			// System.out.println("Root element :" +
			// doc.getDocumentElement().getNodeName());

			NodeList rootNodeList = doc.getElementsByTagName(xml_form_title);
			StringWriter sw = new StringWriter();
			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			transformer.transform(new DOMSource(rootNodeList.item(0)),
					new StreamResult(sw));
			submissionXML = sw.toString();
		}
		submissionXML = submissionXML
				.substring("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
						.length());
		submissionXML = submissionXML.replaceAll("&amp;", "and");
		submissionXML = submissionXML.replaceAll("andamp;;", "and");
		submissionXML = submissionXML.replaceAll("orx:", "n0:");
		int ind = submissionXML.lastIndexOf("<n0:meta");
		submissionXML = submissionXML.substring(0, ind + 8)
				+ "  xmlns:n0=\"http://openrosa.org/xforms\"> "
				+ submissionXML.substring(ind + 9);

		// System.out.println(submissionXML);
		return submissionXML;
	}

}
