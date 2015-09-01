package org.sdrc.evm.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.kxml2.io.KXmlSerializer;
import org.opendatakit.briefcase.model.DocumentDescription;
import org.opendatakit.briefcase.model.ServerConnectionInfo;
import org.opendatakit.briefcase.model.TerminationFuture;
import org.opendatakit.briefcase.util.AggregateUtils;
import org.opendatakit.briefcase.util.WebUtils;
import org.sdrc.devinfo.domain.UtAreaEn;
import org.sdrc.devinfo.domain.UtData;
import org.sdrc.devinfo.domain.UtIndicatorUnitSubgroup;
import org.sdrc.devinfo.repository.UtAreaEnRepository;
import org.sdrc.devinfo.repository.UtDataRepository;
import org.sdrc.devinfo.repository.UtIndicatorUnitSubgroupRepository;
import org.sdrc.evm.domain.FacilityDetails;
import org.sdrc.evm.model.AggregationDataModel;
import org.sdrc.evm.model.EvmDataNode;
import org.sdrc.evm.model.EvmQuestionModel;
import org.sdrc.evm.model.EvmRequirementModel;
import org.sdrc.evm.model.XFormCollection;
import org.sdrc.evm.repository.FacilityDetailsRepository;
import org.sdrc.evm.repository.XFormAreaMapperRepository;
import org.sdrc.evm.util.AssamXFormProcess;
import org.sdrc.evm.util.BiharXFormProcess;
import org.sdrc.evm.util.XMLReader;
import org.sdrc.odkaggregate.domain.EvmQuestion;
import org.sdrc.odkaggregate.domain.EvmRequirement;
import org.sdrc.odkaggregate.domain.EvmSubQuestion;
import org.sdrc.odkaggregate.domain.XForm;
import org.sdrc.odkaggregate.domain.XFormAreaMapper;
import org.sdrc.odkaggregate.gateway.FormXML;
import org.sdrc.odkaggregate.gateway.ODKAggregateWay;
import org.sdrc.odkaggregate.gateway.ODKConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.tags.form.FormTag;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlSerializer;

import com.sun.org.apache.bcel.internal.generic.ARETURN;

@Service
public class AggregationServiceUtilityImpl implements AggregationServiceUtility {

	private static final Logger LOGGER = Logger.getLogger(AggregationServiceUtilityImpl.class);
	
	private static final DecimalFormat df = new DecimalFormat("#");
	
	private String formPath;
	// /submission/data/EVM_DVS_04082014_V1/,/submission/data/EVM_HealthFacility_04082014_V1/,/submission/data/EVM_HealthFacility_06082014_V1/,/submission/data/EVM_SVS_04082014_V1/
	
	@Autowired
	private ResourceBundleMessageSource applicationMessageSource;

	@Autowired
	private ODKAggregateWay aggregateGetWay;

	@Autowired
	private FormXML formXML;
	
	@Autowired
	private UtAreaEnRepository areaEnRepository;
	
	@Autowired
	private UtIndicatorUnitSubgroupRepository indicatorUnitSubgroupRepository;
	
	@Autowired
	private UtDataRepository utDataRepository;
	
	@Autowired
	private FacilityDetailsRepository facilityDetailsRepository;
	
	@Autowired
	private XFormAreaMapperRepository xFormAreaMapperRepository;
	
	@Autowired 
	private E1Service e1Service;
	
	@Autowired 
	private E3Service e3Service;
	
	 @Autowired 
	 private E4Service e4Service;
	 
	 @Autowired
	 private E5Service e5Service;
	 
	 @Autowired
	 private E6Service e6Service;
	 
	 @Autowired
	 private E7Service e7Service;
	 
	 @Autowired
	 private E8Service e8Service;
	 
	 @Autowired
	 private E9Service e9Service;
	 
	 @Autowired
	 private BiharXFormProcess biharXFormProcess;
	 
	 @Autowired
	 private AssamXFormProcess assamXFormProcess;
	 
	 
	 
	private String formRootTitle;
	
	private static final HashMap<String, Integer> indicatorInfo = new HashMap<String,Integer>() {
        {
            put("E1", 1);put("E2", 2);put("E3", 3);put("E4", 4);put("E5", 5);put("E6", 6);put("E7", 7);put("E8", 8);put("E9", 9);
            put("B", 10);put("C", 11);put("E", 12);put("M", 13);put("R", 14);put("T", 15);put("V", 16);
        }
    };		
	
	@Override
	public void ProcessXform(XForm form) {
		try {
//			parseXml(null,form);
//			getAllSubmissionList(form);
			List<Map<String, Object>> collections = getAllSubmissionList(form);
			XMLReader.writeDataToExcel(form.getForm_id(),collections);
			
			Map<String, Double> averageValue = new HashMap<>();
			
			Map<String, Double> svsAverageValue = new HashMap<>();
			
			int areaNid=0;
			
			/**
			 * Persist into DevInfo
			 */
//			for(Map<String, Object> map:collections){
//				/**
//				 * Find area details by Area Id
//				 */
//				String formType = (String)map.get("formtype");
//				System.out.println(formType);
//				
//				UtAreaEn areaEn = new UtAreaEn();
//							
//				if(form.getLevelName().equals("SVSRVS")){
////					if(formType.equals("DVS")) continue;
//					System.out.println((String)map.get("district")+"<>"+form.getLevelName());
////						String areaName = areaEnRepository.findByAreaID((String)map.get("district")).getArea_Name();
//					XFormAreaMapper areaMapper = xFormAreaMapperRepository.findByAreaNameAndAreaLevel((String)map.get("district"),form.getLevelName());
//					areaEn=areaEnRepository.findByAreaID(areaMapper.getAreaCode());
//				}else{
//					
//					
////					XFormAreaMapper areaMapper = xFormAreaMapperRepository.findByAreaNameAndAreaLevel((String)map.get("district"),form.getLevelName());
//					System.out.println((String)map.get("district"));
//					areaEn = areaEnRepository.findByAreaID((String)map.get("district"));
//				}		
//				
////				Map<String ,Double> dataValues = (Map<String, Double>) map.get("data");
////				for(String key:dataValues.keySet()){
////					
////					Integer iusNid= key.contains("_")?indicatorInfo.get(key.split("_")[1]):indicatorInfo.get(key);
////					
////					UtIndicatorUnitSubgroup indicatorUnitSubgroup =indicatorUnitSubgroupRepository.findByIUSNId(iusNid);
//////					LOGGER.info(indicatorUnitSubgroup.getIndicator_NId()+"--"+indicatorUnitSubgroup.getUnit_NId()+"--"+indicatorUnitSubgroup.getUnit_NId());
////					if(areaEn != null){
////						UtData utData = setUtData(indicatorUnitSubgroup, areaEn.getArea_NId(), 2, 16, dataValues.get(key));
////						utDataRepository.save(utData);
////						
////						
////						
////						areaNid = areaEn.getArea_Parent_NId();
////						
////					}
////					/**
////					 * store value for average
////					 */
////					if(formType.equalsIgnoreCase("svs")){
////						if(svsAverageValue.containsKey(key)){
////							svsAverageValue.put(key, svsAverageValue.get(key)+dataValues.get(key));
////						}else{
////							svsAverageValue.put(key, dataValues.get(key));
////						}
////					}else if(formType.equalsIgnoreCase("rvs")){
////						if(averageValue.containsKey(key)){
////							averageValue.put(key, averageValue.get(key)+dataValues.get(key));
////						}else{
////							averageValue.put(key, dataValues.get(key));
////						}
////					}else{
////						
////						if(averageValue.containsKey(key)){
////							averageValue.put(key, averageValue.get(key)+dataValues.get(key));
////						}else{
////							averageValue.put(key, dataValues.get(key));
////						}
////					}
////					
////				}
//				FacilityDetails  facilityDetails = new  FacilityDetails();
//				facilityDetails.setAreaId(areaEn.getArea_ID());
//				facilityDetails.setAreaName(areaEn.getArea_Name());
//				int cnt=0;
//				String imagePath="";
//				for(String path:map.get("imgPath").toString().split(",")){
//					
////					String img = cnt>0 ? ","+areaEn.getArea_Name().replaceAll(" ","_")+"_"+path : areaEn.getArea_Name().replaceAll(" ","_")+"_"+path;
////					if(cnt>0)
//					imagePath += cnt>0 ? ","+areaEn.getArea_Name().replaceAll(" ","_")+"_"+path : areaEn.getArea_Name().replaceAll(" ","_")+"_"+path;
//					cnt++;
//				}
//				facilityDetails.setImages(!map.get("imgPath").toString().equals("") ? imagePath : null );
//				facilityDetails.setLatitude(map.get("latitude")!=null?(Double)map.get("latitude"):null);
//				facilityDetails.setLongitude(map.get("longitude")!=null?(Double)map.get("longitude"):null);
//				facilityDetailsRepository.save(facilityDetails);
//			}
			/**
			 * Find area info for aggregate level
			 */
//			UtAreaEn areaEn = areaEnRepository.findbyAreaNId(areaNid);
//			if(svsAverageValue!=null){
//				for(String key:svsAverageValue.keySet()){
//					Integer iusNid= key.contains("_")?indicatorInfo.get(key.split("_")[1]):indicatorInfo.get(key);
//					
//					UtIndicatorUnitSubgroup indicatorUnitSubgroup =indicatorUnitSubgroupRepository.findByIUSNId(iusNid);
//					
////					LOGGER.info(key+"-->"+averageValue.get(key)+"-->"+(averageValue.get(key)/collections.size()));
//					
//					Double averageVal= svsAverageValue.get(key);
//					
////					LOGGER.info(areaEn.getArea_Name()+"-->"+areaEn.getArea_NId());
//					UtData utData = setUtData(indicatorUnitSubgroup, areaEn.getArea_NId(), 2, 16, new Double(Math.round(averageVal)));
//					utDataRepository.save(utData);
//					
//				}
//			}
//			for(String key:averageValue.keySet()){
//				Integer iusNid= key.contains("_")?indicatorInfo.get(key.split("_")[1]):indicatorInfo.get(key);
//				
//				UtIndicatorUnitSubgroup indicatorUnitSubgroup =indicatorUnitSubgroupRepository.findByIUSNId(iusNid);
//				
//				LOGGER.info(key+"-->"+averageValue.get(key)+"-->"+(averageValue.get(key)/collections.size()));
//				
//				Double averageVal= form.getLevelName().equals("SVSRVS")?averageValue.get(key)/(collections.size()-1) : averageValue.get(key)/collections.size();
//				
//				LOGGER.info(areaEn.getArea_Name()+"-->"+areaEn.getArea_NId());
//				UtData utData = setUtData(indicatorUnitSubgroup, areaEn.getArea_NId(), 2, 16, new Double(Math.round(averageVal)));
//				utDataRepository.save(utData);
//				
//			}
//			LOGGER.info(collections.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private UtData setUtData(UtIndicatorUnitSubgroup ius, int areaNid,
			int timePeriodNid, int sourceNid, Double dataValue) {
		UtData data = new UtData();
		if (ius != null) {
			data.setIUSNId(ius.getIUSNId());
			data.setIndicator_NId(ius.getIndicator_NId());
			data.setUnit_NId(ius.getUnit_NId());
			data.setSubgroup_Val_NId(ius.getSubgroup_Val_NId());
			data.setArea_NId(areaNid);
			data.setTimePeriod_NId(timePeriodNid);
			data.setSource_NId(sourceNid);
			data.setData_Value(dataValue);
		}
		return data;
	}
	
	private List<Map<String, Object>> getAllSubmissionList(XForm xForm) throws Exception {
		
		String formID=xForm.getForm_id();
		AggregateUtils.DocumentFetchResult result = null;
		XmlSerializer serializer = new KXmlSerializer();
//		XPath xPath = XPathFactory.newInstance().newXPath();
		String formRooTitle = "";
//		List<XFormCollection> collectionList = new ArrayList<XFormCollection>();

		StringWriter id_list = new StringWriter();
		StringWriter base_xlsForm = formXML.getXML(xForm);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document core_xml_doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(base_xlsForm.toString().getBytes("utf-8"))));
		if (core_xml_doc != null) {
			core_xml_doc.getDocumentElement().normalize();
			Element eElement = (Element) core_xml_doc.getElementsByTagName("group").item(0);
			formRooTitle = eElement.getAttribute("ref").split("/")[1];
		}
		this.formRootTitle = formRooTitle;

		String baseUrl = xForm.getServer_url()+ ODKConstants.VIEW_SUBMISSION_LIST_PATH;
		Map<String, String> params = new HashMap<String, String>();
		params.put("formId", formID);
		params.put("cursor", "");// websafeCursorString
		params.put("numEntries", "");
		String fullUrl = WebUtils.createLinkWithProperties(baseUrl, params);

		ServerConnectionInfo serverInfo = new ServerConnectionInfo(
				xForm.getServer_url(), xForm.getUsername(),
				xForm.getPassword().toCharArray());
		DocumentDescription submissionDescription = new DocumentDescription("Fetch of manifest failed. Detailed reason: ","Fetch of manifest failed ", "form manifest",new TerminationFuture());
		result = AggregateUtils.getXmlDocument(fullUrl, serverInfo, false,submissionDescription, null);
		serializer.setOutput(id_list);
		result.doc.write(serializer);

		Document doc_id_list = dBuilder.parse(new InputSource(new ByteArrayInputStream(id_list.toString().getBytes("utf-8"))));
		
		List<Map<String, Object>> valueList=new ArrayList<>();
		
		if (doc_id_list != null) {
			doc_id_list.getDocumentElement().normalize();

			NodeList nodeIdList = doc_id_list.getElementsByTagName("id");
			for (int node_no = 0; node_no < nodeIdList.getLength(); node_no++) {
				
				String instance_id = nodeIdList.item(node_no).getFirstChild().getNodeValue();

				try {
					String submission_xml_url = xForm.getServer_url() + ODKConstants.DOWNLOAD_FORM_URL;
					String link_formID = generateFormID(formID, formRooTitle,instance_id);
					Map<String, String> submiteParams = new HashMap<String, String>();
					submiteParams.put("formId", link_formID);
					String full_url = WebUtils.createLinkWithProperties(submission_xml_url, submiteParams);

					serializer = new KXmlSerializer();
					StringWriter data_writer = new StringWriter();
					result = AggregateUtils.getXmlDocument(full_url,serverInfo, false, submissionDescription, null);
					serializer.setOutput(data_writer);
					result.doc.write(serializer);
					
//					new FileOutputStream(new File("D:/"+form.getLevelName()+"_"+node_no+".xml")).write(data_writer.toString().getBytes()); 
					Document submission_doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(data_writer.toString().getBytes("utf-8"))));
					
					/**
					 * Getting indicator value for one instance
					 */
					Map<String, Object> dataValueMap= assamXFormProcess.parseXML(submission_doc, xForm);
//					Map<String, Object> dataValueMap= biharXFormProcess.parseXML(submission_doc, xForm);
					valueList.add(dataValueMap);
					
//					new FileOutputStream(new File("D:/_data/_evm/HF/hf_form"+node_no+".xml")).write(data_writer.toString().getBytes());

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
//			System.out.println(valueList.size());
//			System.out.println(valueList);
//			XMLReader.writeDataToExcel(valueList);
			
		}
		
		return valueList;
	}

	@Override
	public Map<String, Object> parseXml(Document document,XForm xForm) {
		
		Map<String, Object> dataValueMap=new HashMap<>();
		
		Map<String, List<Double>> categoryScore = new HashMap<>();
		
		Map<String, List<Double>> categoryWeight = new HashMap<>();
		
		String imgPath="";
		
		formPath=xForm.getSubmissionPath();
//		formPath="EVM_SVS_RVS_09092014_V4/";
//		formPath="EVM_DVS_09092014_V4/";
//		formPath="EVM_HealthFacility_09092014_V4/";
		System.out.println("in parse xml method.");
		try{
		
//		File xmlFile = new File("D:/submission.xml");
//		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder builder = builderFactory.newDocumentBuilder();
//		Document document = builder.parse(xmlFile);
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		document.getDocumentElement().normalize();
		
		List<EvmRequirement> evmRequirements=xForm.getEvmRequirements();
		
		String formType=xForm.getForm_id().equalsIgnoreCase("EVM_HealthFacility_09092014_V4") ? "HF" : xForm.getForm_id().equalsIgnoreCase("EVM_DVS_09092014_V4") ? "DVS" : xPath.compile(formPath+"g1/g1_4").evaluate(document);
		String districtName=xForm.getLevelName().equalsIgnoreCase("HF") ?xPath.compile(formPath+"b/block").evaluate(document) : xPath.compile(formPath+"district").evaluate(document);
		
		String facilityName=xPath.compile(formPath+"L2/L2_g1/L2_3").evaluate(document);
		
		String lat_long_info=xPath.compile(formPath+"L2/L4/L4_1").evaluate(document);
		
//		if(!lat_long_info.equals(""))
//			System.out.println(lat_long_info+"-->"+lat_long_info.split(" ")[0]+"-->"+lat_long_info.split(" ")[1]);
		
		Map<String ,Double> dataValues=new HashMap<>();
		
		LOGGER.info("District Name====================>"+districtName);
		LOGGER.info("=====================================================================================");
		
		Map<String,EvmRequirementModel> evmRequirementModels = new HashMap<>();
		
		for(EvmRequirement requirement:evmRequirements){//calculate each requirement value
			EvmRequirementModel evmRequirementModel = new EvmRequirementModel();
			if(formType.equals("rvs") && requirement.getIndicatorName().equals("SVS_E1") ){
							continue;
			}
			
			Double scoreSum = 0.0;
			Double weightageSum = 0.0;
			Double percentVal = 0.0;
			System.out.println(requirement.getXpath());
			
			List<EvmQuestion> evmQuestions=requirement.getEvmQuestions();
//			System.out.println("evm Question size:->"+evmQuestions.size());
			
			List<EvmQuestionModel> evmQuestionModels = new ArrayList<>();
			
			for(EvmQuestion question:evmQuestions){//questions for each requirement
				
				if(formType.equals("rvs") &&  (question.getQuestionName().equals("E4_4.23") ||
				  question.getQuestionName().equals("E8_8.07") || question.getQuestionName().equals("E2_2.2.1c") ||
					question.getQuestionName().equals("E9_9.05") || question.getQuestionName().equals("E9_9.06") || 
						question.getQuestionName().equals("E9_9.09") || question.getQuestionName().equals("E9_9.12") ||
							question.getQuestionName().equals("E9_9.13") || question.getQuestionName().equals("E9_9.14") ||
								question.getQuestionName().equals("E9_9.15") || question.getQuestionName().equals("E9_9.16") ||
									question.getQuestionName().equals("E9_9.17") || question.getQuestionName().equals("E9_9.18") ||
										question.getQuestionName().equals("E9_9.19")))
							
					{
						continue;
					}
					
//				System.out.println(question.getQuestionName());
//				Double val=0.0;
//				List<EvmDataNode> dataNode = null;
				EvmQuestionModel questionModel = new EvmQuestionModel();
				Double wt=0.0;
				
				String xPathString="";		
				xPathString=formPath+requirement.getXpath()+"/";
				switch (question.getQuestionType()) {
				
					case "Boolean":
						questionModel = booleanMethod(document, requirement,
								question, xPathString);
						break;
					case "EvenByOdd":
						questionModel = evenByOdd(document, question,
								xPathString);
						break;
					case "calE1_03":
						questionModel = e1Service.calculateE1_03(document,
								question, xPathString);
						break;
					case "calE1_05":
						questionModel = e1Service.calculateE1_05(document,
								question, xPathString);
						break;
					case "calE1_06":
						questionModel = e1Service.calculateE1_06(document,
								question, xPathString);
						break;
					case "calE1_19":
						questionModel = e1Service.calculateE1_19(document,
								question, xPathString);
						break;
					case "netStorage":
						questionModel = e3Service.calculateNetStorage(document,
								question, xPathString);
						break;
					case "calE3_09":
						questionModel = e3Service.calculateE3_09(document,
								question, xPathString);
						break;
					case "mulWeightDivide2":
						questionModel = e4Service.mulWeightDivide2(document,
								question, xPathString);
						break;
					case "mulWeightDivide":
						questionModel = e4Service.mulWeightDivide(document,
								question, xPathString);
						break;
					case "calculateE4_27":
						questionModel = calculateE4_27(document, question,
								xPathString);
						break;
					case "e4_02_Exception":
						questionModel = E4_02Boolean_Exception(document,
								requirement, question, xPathString);
						break;
					case "multiplyWeight":
						questionModel = e5Service.multiplyWeight(document,
								question, xPathString);
						break;
					case "e5_2_exception":
						questionModel = e5Service.e5_2_BooleanException(
								document, question, xPathString);
						break;
					case "calculateE6_16":
						questionModel = e6Service.calculateE6_16(document,
								question, xPathString);
						break;
					case "calculateE6_21":
						questionModel = e6Service.calculateE6_21(document,
								question, xPathString);
						break;
					case "calculateE6_22":
						questionModel = e6Service.calculateE6_22(document,
								question, xPathString);
						break;
					case "calculateE6_23":
						questionModel = e6Service.calculateE6_23(document,
								question, xPathString);
						break;
					case "calculateE7_2":
						questionModel = e7Service.calculateE7_2(document,
								question, xPathString);
						break;
					case "calculateE7_5":
						questionModel = e7Service.calculateE7_5(document,
								question, xPathString);
						break;
					case "Range_0_4":
						questionModel = e7Service.calculateRange0To4(document,
								question, xPathString);
						break;
					case "E7_11":
						questionModel = calculateE7_11(document, question,
								xPathString);
						break;
					case "calculateE7_14":
						questionModel = e7Service.calculateE7_14(document,
								question, xPathString);
						break;
					case "e8_13_Exception":
						questionModel = e8Service.e8_13_BooleanException(
								document, question, xPathString);
						break;
					case "e8_11":
						questionModel = e8Service.e8_11(document, question,
								xPathString);
						break;
					case "divideBy4":
						questionModel = e9Service.calculateValDivideBy4(
								document, question, xPathString);
						break;
					case "calculateE9_24":
						questionModel = e9Service.calculateE9_24(document,
								question, xPathString);
						break;
					case "calculateE9_20":
						questionModel = e9Service.calculateE9_20(document,
								question, xPathString);
						break;
					case "E5Exception":
						questionModel = E5_Exceptional(document, question,
								xPathString);
						break;
					}
				
				LOGGER.info(question.getQuestionName()+"--"+question.getQuestionType()+"--"+questionModel.getValue());
				
				/**
				 * calculate score and weight  
				 */
				
				if(!questionModel.getValue().isNaN()){
					
					scoreSum = scoreSum + questionModel.getValue();
					
					/**
					 * calculate category
					 */
				}
				//
				if(question.getWeightagetype()==null){
					wt= question.getWeightage();
				}
				else if(question.getWeightagetype().equalsIgnoreCase("eqna")){//used
					
					wt=calculateWeightEqNA(document, question, xPathString);
					
				}
				else if(question.getWeightagetype().equalsIgnoreCase("noty")){//used
					
					wt=calculateWeightNotY(document, question, xPathString);
					
				}else if(question.getWeightagetype().equalsIgnoreCase("eqy")){//used
					
					wt=calculateWeightEqY(document, question, xPathString);
				}else if(question.getWeightagetype().equalsIgnoreCase("eqory")){//unused
					
					wt=calculateWeightEqORY(document, question, xPathString);
				}else if(question.getWeightagetype().equalsIgnoreCase("eqn")){//used
					
					wt=calculateWeightEqN(document, question, xPathString);
					
				}else if(question.getWeightagetype().equalsIgnoreCase("eqymin")){//used
					
					wt=calculateWeightEqYForMin(document, question, xPathString);
					
				}else if(question.getWeightagetype().equalsIgnoreCase("multi")){//used
					
					switch (question.getQuestionName()) {
					case "E3_3.4.2":
						xPathString=formPath+requirement.getXpath()+"/";
						wt=calculateMultiWeighte3(document, question, xPathString);
						break;
					case "E5_5.2.1":
						wt=calculateMultiWeighte5(document, question, xPathString);
						break;
					case "E4_4.4.5":
						wt=calMultiWeighteE4_27(document, question, xPathString);
						break;
					case "E3_3.4.1":
						wt=calMultiWeighteE3_09(document, question, xPathString);
						break;	
					case "E3_3.02":
						wt=calMultiWeighteE3_02(document, question, xPathString);
						break;
					case "E5_5.2.2":
						wt=calMultiWeighteE5_07(document, question, xPathString);
						break;
					case "E7_7.08":
						wt=calMultiWeighteE7_08(document, question, xPathString);
						break;
					case "E7_7.5.2":
						wt=calMultiWeighteE7_14(document, question, xPathString);
						break;
					case "E4_4.4.2":
						wt=calMultiWeighteE4_27(document, question, xPathString);
						break;
					case "E6_6.16":
						wt=calMultiWeighteE6_16(document, question, xPathString);
						break;
					default:
						break;
					}
					
				}else{
					
				}
				
				LOGGER.info(question.getQuestionName()+"--wt-->"+wt);
				questionModel.setWeight(wt);
				evmQuestionModels.add(questionModel);
				weightageSum = weightageSum + wt;
				if(question.getClssification()!=null){
					
					if(categoryScore.containsKey(question.getClssification())){
//						val=val.isNaN()?0.0:val;
							
						categoryScore.get(question.getClssification()).add(questionModel.getValue().isNaN()?0.0:questionModel.getValue());
						
					}else{
//						val=val.isNaN()?0.0:val;
						List<Double> catScoreList= new ArrayList<>();
						catScoreList.add(questionModel.getValue().isNaN()?0.0:questionModel.getValue());
						categoryScore.put(question.getClssification(), catScoreList);
					}
					
					if(categoryWeight.containsKey(question.getClssification())){
						categoryWeight.get(question.getClssification()).add(wt);
						
					}else{
						List<Double> catWtList= new ArrayList<>();
						catWtList.add(wt);
						categoryWeight.put(question.getClssification(), catWtList);
					}
					
				}
				
			}
			/**
			 * Find data vale for indicator
			 */
//			percentVal = Double.parseDouble(df.format(scoreSum * 100 / weightageSum));
			
			percentVal = scoreSum * 100 / weightageSum;
			evmRequirementModel.setName(requirement.getIndicatorName()); // set requirement name, value after percentVal calculation
			evmRequirementModel.setValue(new Double(Math.round(percentVal)));
			evmRequirementModel.setEvmQuestionModels(evmQuestionModels);
			evmRequirementModels.put(evmRequirementModel.getName(),evmRequirementModel);
			
			LOGGER.info("Total Score-->"+scoreSum+"Weightage Sum-->"+weightageSum+"Percent val : "+percentVal);
			
			dataValues.put(requirement.getIndicatorName(), new Double(Math.round(percentVal)));
			
			
		}
		
//		LOGGER.info(districtName+"data values-->"+dataValues);
//		System.out.println("EVM REQUIREMENT LIST:"+evmRequirementModels.size());
//		System.out.println("EVM REQUIREMENT LIST:"+evmRequirementModels);
		LOGGER.info(districtName+"Category Score-->"+categoryScore.get("M").size());
		
		LOGGER.info(districtName+"Category weight-->"+categoryWeight.get("M").size());
		
		for(String category:categoryScore.keySet()){
			
			try {
				Double catScore = sum(categoryScore.get(category));
				Double maxScore = sum(categoryWeight.get(category));
				LOGGER.info(districtName+"Category -->"+catScore/maxScore*100);
				Double catVal= catScore/maxScore*100;
//				Double catValue = Double.parseDouble(df.format(catScore/maxScore*100)) ;
				Double catValue =(double) Math.round(catVal);
				dataValues.put(category, catValue);
			} catch (Exception e) {
				dataValues.put(category, 0.0);
			}
		}
		
		String imgPath1=xPath.compile(formPath+"L2/L4/L4_2").evaluate(document);
		String imgPath2=xPath.compile(formPath+"L2/L4/L4_3").evaluate(document);
		
		if(!imgPath1.equals(""))
			imgPath = imgPath1;
		if(!imgPath2.equals(""))
			imgPath += ","+imgPath2;
		
		dataValueMap.put("formtype",formType );
		dataValueMap.put("district",districtName );
		dataValueMap.put("facility",facilityName );
		dataValueMap.put("data",dataValues );
		dataValueMap.put("dataNode",evmRequirementModels );
		dataValueMap.put("imgPath",imgPath );
		if(!lat_long_info.equals("")){
//			System.out.println(lat_long_info+"-->"+lat_long_info.split(" ")[0]+"-->"+lat_long_info.split(" ")[1]);
			dataValueMap.put("latitude",Double.parseDouble(lat_long_info.split(" ")[0]) );
			dataValueMap.put("longitude",Double.parseDouble(lat_long_info.split(" ")[1]) );
		}else{
			dataValueMap.put("latitude",null);
			dataValueMap.put("longitude",null);
		}
		LOGGER.info(districtName+"data values-->"+dataValues);
	}catch(Exception e){
		e.printStackTrace();
	}
	return dataValueMap;
}

	public Double sum(List<Double> list) {
		Double sum= 0.0; 
	     for (Double i:list)
	         sum = sum + i;
	     return sum;
	}
	
	public boolean isEven(double num) { 
		
		return ((num % 2) == 0) ;
	}

	private String generateFormID(String formID, String form_titile, String key) {
		return formID + "[@version=null and @uiVersion=null]/" + form_titile
				+ "" + "[@key=" + key + "]";
	}
	
	
	
	
	private Double getDouble(String str){
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			return 0.0;
		}
	}
	
	
	/**
	 * 
	 * @param document
	 * @param question
	 * @param xPathString
	 * @return
	 * @throws XPathExpressionException
	 * 
	 * 
	 * if only one of the value is yes then returns max (yes with or condition)
	 */
	
	private double calculateWeightNotY(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException{
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		String relevantQuestion=question.getWeightageRelevant();
		
		boolean check=true;
		
		for(String path : relevantQuestion.split(",")){
			System.out.println(formPath+path);
			String val=xPath.compile(formPath+path).evaluate(document);
			
			if(val.equals("yes") || val.equals("1")){
				check=false;
				break;
			} 
			
		}
		
		if(check) return 0.0;
		
		else return question.getWeightage();
	}
	/*
	 * if all vals are equal to y then return max 
	 */
	private double calculateWeightEqY(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException{
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		String relevantQuestion=question.getWeightageRelevant();
		
		boolean check=true;
		
		for(String path : relevantQuestion.split(",")){
			
			String val=xPath.compile(formPath+path).evaluate(document);
			
			if(val.equals("yes") || val.equals("1")) {
				
				continue ;
				
			}else {
				
				check=false;
				break;
			}
			
		}
		
		if(check) return question.getWeightage();
		
		else return 0.0;
	}
	/*
	 * if all vals are equal to y then return 0.0 
	 */
	private double calculateWeightEqYForMin(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException{
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		String relevantQuestion=question.getWeightageRelevant();
		
		boolean check=true;
		
		for(String path : relevantQuestion.split(",")){
			
			String val=xPath.compile(formPath+path).evaluate(document);
			
			if(val.equals("yes") || val.equals("1")) {
				
				continue ;
				
			}else {
				
				check=false;
				break;
			}
			
			
		}
		
		if(check) return 0.0;
		
		else return question.getWeightage();
	}
	/**
	 * 
	 * @param document
	 * @param question
	 * @param xPathString
	 * @return
	 * @throws XPathExpressionException
	 * 
	 * calculate weight for = NA
	 */
	private double calculateWeightEqNA(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException{
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		String relevantQuestion=question.getWeightageRelevant();
		
		boolean check=true;
		
		for(String path : relevantQuestion.split(",")){
			
			String val=xPath.compile(formPath+path).evaluate(document);
			
			if(val.equals("3")) check=false;
			
			
		}
		
		if(check) return question.getWeightage();
		
		else return 0.0;
	}
	
	private double calculateWeightEqORY(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException{
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		String relevantQuestion = question.getWeightageRelevant();
		
		boolean check=false;
		
		for(String path : relevantQuestion.split(",")){
			
			String val=xPath.compile(formPath+path).evaluate(document);
			
			if(val.equals("yes") || val.equals("1")) {
				check=true;
				break;
			}
			
			
		}
		
		if(check) return question.getWeightage();
		
		else return 0.0;
	}
	/*
	 * if equals to no then 0 else max
	 */
	private double calculateWeightEqN(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException{
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		String relevantQuestion=question.getWeightageRelevant();
		
		boolean check=true;
		
		for(String path : relevantQuestion.split(",")){
			
			String val=xPath.compile(formPath+path).evaluate(document);
			
			if(val.equalsIgnoreCase("no") || val.equals("2")) check=false;
			
			
		}
		
		if(check) return question.getWeightage();
		
		else return 0.0;
	}
	
	private double calculateMultiWeighte3(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException{
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		String valA=xPath.compile(formPath+"E0_01/group_q3/E0_03a_A").evaluate(document);
//			System.out.println(formPath+question.getXpath()+"/E3_3.4.2/E3_10a_A");
		String valB=xPath.compile(xPathString+"E3_3.4.2/E3_10a_A").evaluate(document);
			
		if(!valA.equals("yes") || valB.equals("no")) return 0.0;
			
		else return question.getWeightage();
	}
	
	private double calculateMultiWeighte5(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException{
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		String valA=xPath.compile(formPath+"E0_01/group_q1/E0_01a_C").evaluate(document);
			
		String valB=xPath.compile(formPath+"E5_5.1/E5_5.2.1/E5_06a_A").evaluate(document);
		
		if(valA.equals("yes")){
			
			return valB.equals("0") || valB.trim().equals("") ? 0.0 : question.getWeightage();
		}else return 0.0;
			
	}
	
	private double calMultiWeighteE4_27(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException{
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		String valA=xPath.compile(formPath+"E0_01/group_q3/E0_03a_A").evaluate(document);
			
		String valB=xPath.compile(formPath+"E0_01/group_q3/E0_03a_B").evaluate(document);
		
		String valC=xPath.compile(formPath+"E0_01/group_q4/E0_04a_A").evaluate(document);
		
		return !valA.equals("yes") && (!valB.equals("yes") || !valC.equals("yes")) ? 0 : question.getWeightage();
		
	}
	
	private double calMultiWeighteE3_09(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException{
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		String[] relevantQuestion=question.getWeightageRelevant().split(",");
		
		String valA=xPath.compile(formPath+relevantQuestion[0]).evaluate(document);
			
		String valB=xPath.compile(formPath+relevantQuestion[1]).evaluate(document);
		
		String valC=xPath.compile(formPath+relevantQuestion[2]).evaluate(document);
		
		return (!valA.equals("yes") || (!valB.equals("yes") && !valC.equals("yes")) ? 0 : question.getWeightage());
		
		
		
	}
	
	private double calMultiWeighteE3_02(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException{
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		String[] relevantQuestion=question.getWeightageRelevant().split(",");
		
		String valA=xPath.compile(formPath+relevantQuestion[0]).evaluate(document);
			
		String valB=xPath.compile(formPath+relevantQuestion[1]).evaluate(document);
		
		String valC=xPath.compile(formPath+relevantQuestion[2]).evaluate(document);
		
		return ((!valA.equals("yes") && !valB.equals("yes")) || !valC.equals("yes") ? 0 : question.getWeightage());
		
	}
	
	private double calMultiWeighteE5_07(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException{
		
	try{
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		String valA=xPath.compile(formPath+"E5_5.1/E5_5.2.2/E5_07a_A").evaluate(document);
			
		String valB=xPath.compile(formPath+"E0_01/group_q4/E0_04a_A").evaluate(document);
		
		String valC=xPath.compile(formPath+"E0_01/group_q4/E0_04a_B").evaluate(document);
		
		if(valA.equals("")){
			return !valB.equals("yes") && !valC.equals("yes") ? 0 : question.getWeightage();
		}
		else if(Double.parseDouble(valA)==0) return 0;
		
		else return !valB.equals("yes") && !valC.equals("yes") ? 0 : question.getWeightage();
		}
		catch (Exception e){
			return 0.0;
	}
		
	}
	
private double calMultiWeighteE7_08(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException{
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		String relevantQuestion=question.getWeightageRelevant();
		
		boolean check=false;
		
		for(String path : relevantQuestion.split(",")){
			
			String val=xPath.compile(formPath+path).evaluate(document);
			
			if(val.equalsIgnoreCase("2") || val.equalsIgnoreCase("1")) check=true;
			
			
		}
		
		if(check) return question.getWeightage();
		
		else return 0.0;
			
	}

private double calMultiWeighteE7_14(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException{
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	
	String[] relevantQuestion=question.getWeightageRelevant().split(",");
	
	boolean check=true;
	
	String relvance1 = xPath.compile(formPath+relevantQuestion[0]).evaluate(document);
	String relvance2 = xPath.compile(formPath+relevantQuestion[1]).evaluate(document);
		
	if(relvance1.equalsIgnoreCase("3") || relvance2.equalsIgnoreCase("0") || relvance2.equalsIgnoreCase("")) check=false;  
	
	if(check) return question.getWeightage();
	
	else return 0.0;
		
}
	
	
	
	public EvmQuestionModel calculateE4_27(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
		
		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		XPath xPath = XPathFactory.newInstance().newXPath();
		EvmDataNode dataNode = new EvmDataNode();
		List<EvmDataNode> dataNodes = new ArrayList<>();
		
		String relvance1 = xPath.compile(formPath+"E0_01/group_q3/E0_03a_A").evaluate(document);
		String relvance2 = xPath.compile(formPath+"E0_01/group_q3/E0_03a_B").evaluate(document);
		String valA=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(0).getXpath()).evaluate(document);
		String valB=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(1).getXpath()).evaluate(document);
		
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(0).getQuestionName());
		dataNode.setValue(valA.toString());
		dataNodes.add(dataNode);
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(1).getQuestionName());
		dataNode.setValue(valB.toString());
		dataNodes.add(dataNode);
		
		try{
		Double result_relvance = (relvance1.equals("yes") && relvance2.equals("yes") ? 0.5 : 1);
		
		Double val1 = (relvance1.equals("yes") && valA.equals("yes") ? 1.0 : 0.0);
		
		Double val2 = (relvance2.equals("yes") && valB.equals("yes") ? 1.0 : 0.0);
		
		evmQuestionModel.setName(question.getQuestionName());
		evmQuestionModel.setDataNodes(dataNodes);
		evmQuestionModel.setValue(((val1 + val2) * result_relvance) * question.getWeightage());
		
		return evmQuestionModel;
		
		}
			catch (Exception e) {
				evmQuestionModel.setName(question.getQuestionName());
				evmQuestionModel.setDataNodes(dataNodes);
				evmQuestionModel.setValue(0.0);
				return evmQuestionModel;
		}
		
		
	}
	
	
	
	private EvmQuestionModel evenByOdd(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		List<EvmDataNode> dataNodes = new ArrayList<>();
		EvmDataNode dataNode = null;
		Double odd=0.0;
		Double even=0.0;
		int num=0;
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		for(EvmSubQuestion subQuestion:question.getEvmSubQuestions()){
			dataNode = new EvmDataNode();
			dataNode.setName(subQuestion.getQuestionName());
			num++;
			
			String path = xPathString + question.getXpath() +"/"+subQuestion.getXpath();
			LOGGER.info("xpath:->"+xPathString);
			try {
				
				Double val=getDouble(xPath.compile(path).evaluate(document));
				dataNode.setValue(val.toString());
				dataNodes.add(dataNode);
				
				if(subQuestion.getExceptionrule() != null && subQuestion.getExceptionrule().length() > 0 )
				{
					val = Double.parseDouble(subQuestion.getExceptionrule());
				}
				
				if(isEven(num)){
					even = even + val;
				}else{
					odd = odd + val;
				}
				
			} catch (Exception e) {
				evmQuestionModel = new EvmQuestionModel();
				evmQuestionModel.setName(question.getQuestionName());
				evmQuestionModel.setDataNodes(dataNodes);
				evmQuestionModel.setValue(0.0);
				return evmQuestionModel;
			}
		}
		
		evmQuestionModel.setName(question.getQuestionName());
		evmQuestionModel.setDataNodes(dataNodes);
		evmQuestionModel.setValue(even/odd*question.getWeightage());
		return evmQuestionModel;
	}
	
	private EvmQuestionModel E5_Exceptional(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		List<EvmDataNode> dataNodes = new ArrayList<>();
		EvmDataNode dataNode = null;
		Double odd=0.0;
		Double even=0.0;
		int num=0;
		XPath xPath = XPathFactory.newInstance().newXPath();
		for(EvmSubQuestion subQuestion:question.getEvmSubQuestions()){
			dataNode = new EvmDataNode();
			dataNode.setName(subQuestion.getQuestionName());
			num++;
			
			String path = xPathString + question.getXpath() +"/"+subQuestion.getXpath();
			LOGGER.info("xpath:->"+xPathString);
			try {
				
				Double val=Double.parseDouble(xPath.compile(path).evaluate(document));
				
				if(subQuestion.getExceptionrule() != null && subQuestion.getExceptionrule().length() > 0 )
				{
					val = Double.parseDouble(subQuestion.getExceptionrule());
				}
				dataNode.setValue(val.toString());
				dataNodes.add(dataNode);
				
				if(isEven(num)){
					even = even + val;
				}else{
					odd = odd + val;
				}
				
			} catch (Exception e) {
				evmQuestionModel.setName(question.getQuestionName());
				evmQuestionModel.setDataNodes(dataNodes);
				evmQuestionModel.setValue(0.0);
				return evmQuestionModel;
			}
		}
		evmQuestionModel.setName(question.getQuestionName());
		evmQuestionModel.setDataNodes(dataNodes);
		evmQuestionModel.setValue((1-even/odd)*question.getWeightage());
		return evmQuestionModel;
	}
	
		public EvmQuestionModel calculateE7_11(Document document, EvmQuestion question,String xPathString) throws XPathExpressionException {

		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		XPath xPath = XPathFactory.newInstance().newXPath();
		EvmDataNode dataNode = null;
		List<EvmDataNode> dataNodes = new ArrayList<>();
		String relvance="";
		
		if(formPath.contains("DVS") || formPath.contains("HF")){
			
		relvance = xPath.compile(formPath+"E7/E7_7.4.1/E7_09a").evaluate(document);
		
		}else{
			
			relvance = xPath.compile(formPath+"E7/E7_7.4/E7_09a").evaluate(document);
		}
		
		Double valA=getDouble(xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(0).getXpath()).evaluate(document));
		Double valB=getDouble(xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(1).getXpath()).evaluate(document));
		String valC=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(2).getXpath()).evaluate(document);
		Double valD=getDouble(xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(3).getXpath()).evaluate(document));
		Double valE=getDouble(xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(4).getXpath()).evaluate(document));
		String var1 = xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(3).getXpath()).evaluate(document);
		String var2 = xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(4).getXpath()).evaluate(document);
		
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(0).getQuestionName());
		dataNode.setValue(valA.toString());
		dataNodes.add(dataNode);
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(1).getQuestionName());
		dataNode.setValue(valB.toString());
		dataNodes.add(dataNode);
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(2).getQuestionName());
		dataNode.setValue(valC);
		dataNodes.add(dataNode);
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(3).getQuestionName());
		dataNode.setValue(valD.toString());
		dataNodes.add(dataNode);
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(4).getQuestionName());
		dataNode.setValue(valE.toString());
		dataNodes.add(dataNode);
		try {
						
			
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			if(valA == 0) {
				evmQuestionModel.setValue(0.0);
				return evmQuestionModel;
			}
			
			else if (valB > valA || valD > valB || valE > valB){
				evmQuestionModel.setValue(0.0);
				return evmQuestionModel; 
			}
			else if (!relvance.equals("yes") && (valC.equals("no") || valC.equals(""))){
				evmQuestionModel.setValue(question.getWeightage()* valB / valA);
				return evmQuestionModel; 
			}
			
			else if (!relvance.equals("yes") && valC.equals("yes")){
				evmQuestionModel.setValue(question.getWeightage()* valD / valA);
				return evmQuestionModel; 
			}
			
			else if (relvance.equals("yes") && (valC.equals("no") || valC.equals(""))){
				evmQuestionModel.setValue(question.getWeightage()* valE / valA);
				return evmQuestionModel;
			}
			
			else if (relvance.equals("yes") && (valC.equals("no") ||  valC.equals(""))){
				evmQuestionModel.setValue(question.getWeightage()* valE / valA);
				return evmQuestionModel;
			}
			
			else {
				evmQuestionModel.setValue(question.getWeightage()* findMin(var1, var2)/ valA);
				return evmQuestionModel; 
			}
			

		} catch (Exception e) {
			
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}

	}
		
			public EvmQuestionModel E4_02Boolean_Exception(Document document,EvmRequirement requirement, EvmQuestion question,String xPathString) throws XPathExpressionException {
			
			EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
			List<EvmDataNode> dataNodes = new ArrayList<>();
			EvmDataNode dataNode =null;
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			String relvance = xPath.compile(formPath+"E0_01/group_q2/E0_02a_D").evaluate(document);
			
			if(!relvance.equals("yes")) {
				evmQuestionModel.setName(question.getQuestionName());
				evmQuestionModel.setValue(0.0);
				List<EvmSubQuestion> evmSubQuestions = question.getEvmSubQuestions();
				for(EvmSubQuestion evmSubQuestion: evmSubQuestions){
					dataNode = new EvmDataNode();
					dataNode.setName(evmSubQuestion.getQuestionName());
					dataNode.setValue(xPath.compile(xPathString+question.getXpath()+"/"+evmSubQuestion.getXpath()).evaluate(document));
					dataNodes.add(dataNode);
				}
				evmQuestionModel.setDataNodes(dataNodes);
				return evmQuestionModel;
			}
			else{
				return booleanMethod(document, requirement, question, xPathString);
			}

		}
			
			public EvmQuestionModel booleanMethod(Document document,EvmRequirement requirement, EvmQuestion question,String xPathString) throws XPathExpressionException {
				int ycount = 0;
				int tcount = 0;
				EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
				List<EvmDataNode> dataNodes = new ArrayList<>();
				
				List<EvmSubQuestion> evmSubQuestions=question.getEvmSubQuestions();
				XPath xPath = XPathFactory.newInstance().newXPath();
				
				for(EvmSubQuestion subQuestion:evmSubQuestions){
					EvmDataNode dataNode = new EvmDataNode();
					dataNode.setName(subQuestion.getXpath());
					xPathString="";
					if(requirement.getXpath()!=null)
						xPathString=xPathString+formPath+requirement.getXpath();
					else
						xPathString=xPathString+formPath;
					
					if(question.getXpath()!=null)
						xPathString=xPathString + "/"+question.getXpath()+"/"+subQuestion.getXpath();
					else
						xPathString=xPathString +"/"+subQuestion.getXpath();
					
					String strVal=xPath.compile(xPathString).evaluate(document);
					dataNode.setValue(strVal);
					dataNodes.add(dataNode);
					
					System.out.println("xpath:->"+xPathString+"-->"+strVal);
					
//					if(subQuestion.getExceptionrule() != null && subQuestion.getExceptionrule().length() > 0  )
//					{
//						strVal = subQuestion.getExceptionrule();
//					}
					
					if(strVal.equalsIgnoreCase("yes") || strVal.equalsIgnoreCase("1"))
						ycount++;
					
					//if(!val.equals(""))
						tcount++;
					
					if(tcount!=0 && strVal.equalsIgnoreCase("3"))
						tcount--;
					
				}
					evmQuestionModel.setName(question.getQuestionName());
					evmQuestionModel.setDataNodes(dataNodes);
					evmQuestionModel.setValue(question.getWeightage() * ycount / tcount);
					return evmQuestionModel;
			}	
			
			private Double findMin(String var1, String var2){
				if(var1.equals("") && var2.equals("")) return 0.0;
				else if(!var1.equals("") && var2.equals("")) return getDouble(var1);
				else if(var1.equals("") && !var2.equals("")) return getDouble(var2);
				else return Math.min(getDouble(var1), getDouble(var2));
			}
			
			private double calMultiWeighteE6_16(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException{
				
				
					XPath xPath = XPathFactory.newInstance().newXPath();
					
					String relvance1=xPath.compile(formPath+question.getWeightageRelevant().split(",")[0]).evaluate(document);
						
					String relvance2=xPath.compile(formPath+question.getWeightageRelevant().split(",")[1]).evaluate(document);
					return relvance1.equals("yes") || !relvance2.equals("yes") ? 0 : question.getWeightage();
					
				}
					
	
		
}
