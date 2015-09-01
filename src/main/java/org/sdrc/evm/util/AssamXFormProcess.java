package org.sdrc.evm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.sdrc.evm.model.EvmDataNode;
import org.sdrc.evm.model.EvmQuestionModel;
import org.sdrc.evm.model.EvmRequirementModel;
import org.sdrc.evm.service.AggregationServiceUtilityImpl;
import org.sdrc.evm.service.E1Service;
import org.sdrc.evm.service.E3Service;
import org.sdrc.evm.service.E4Service;
import org.sdrc.evm.service.E5Service;
import org.sdrc.evm.service.E6Service;
import org.sdrc.evm.service.E7Service;
import org.sdrc.evm.service.E8Service;
import org.sdrc.evm.service.E9Service;
import org.sdrc.odkaggregate.domain.EvmQuestion;
import org.sdrc.odkaggregate.domain.EvmRequirement;
import org.sdrc.odkaggregate.domain.EvmSubQuestion;
import org.sdrc.odkaggregate.domain.XForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

/**
 * @author kamal
 *
 */
@Component("REG1")
public class AssamXFormProcess implements ProcessXForm {
	
	private static final Logger LOGGER = Logger
			.getLogger(AggregationServiceUtilityImpl.class);

	private String formPath;

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

	@Override
	public Map<String, Object> parseXML(Document document, XForm xForm) {
		System.out.println("In Assam xForm process method.");

		
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
		Map<String, List<EvmQuestionModel>> evmCategoryClassificationModel =  new HashMap<>();
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
				if(question.getDevinfoclassificationName()!=null){
					
					if(categoryScore.containsKey(question.getDevinfoclassificationName())){
//						val=val.isNaN()?0.0:val;
						categoryScore.get(question.getDevinfoclassificationName()).add(questionModel.getValue().isNaN()?0.0:questionModel.getValue());
						
					}else{
//						val=val.isNaN()?0.0:val;
						List<Double> catScoreList= new ArrayList<>();
						catScoreList.add(questionModel.getValue().isNaN()?0.0:questionModel.getValue());
						categoryScore.put(question.getDevinfoclassificationName(), catScoreList);
					}
					
					if(categoryWeight.containsKey(question.getDevinfoclassificationName())){
						categoryWeight.get(question.getDevinfoclassificationName()).add(wt);
						
					}else{
						List<Double> catWtList= new ArrayList<>();
						catWtList.add(wt);
						categoryWeight.put(question.getDevinfoclassificationName(), catWtList);
					}
					
					//FOR CATEGORY STUFF====//
					if(evmCategoryClassificationModel.containsKey(question.getDevinfoclassificationName())){
						evmCategoryClassificationModel.get(question.getDevinfoclassificationName()).add(questionModel);
						
					}else{
						List<EvmQuestionModel> catQuestionList= new ArrayList<>();
						catQuestionList.add(questionModel);
						evmCategoryClassificationModel.put(question.getDevinfoclassificationName(), catQuestionList);
					}
				}
				
			}
			/**
			 * Find data vale for indicator
			 */
//			percentVal = Double.parseDouble(df.format(scoreSum * 100 / weightageSum));
			
			percentVal = scoreSum * 100 / weightageSum;
			evmRequirementModel.setName(requirement.getIndicatorName().split("_")[1]); // set requirement name, value after percentVal calculation
			evmRequirementModel.setValue(new Double(Math.round(percentVal)));
			evmRequirementModel.setEvmQuestionModels(evmQuestionModels);
			evmRequirementModels.put(evmRequirementModel.getName(),evmRequirementModel);
			
			LOGGER.info("Total Score-->"+scoreSum+"Weightage Sum-->"+weightageSum+"Percent val : "+percentVal);
			
			dataValues.put(requirement.getIndicatorName(), new Double(Math.round(percentVal)));
			
		}
		
//		LOGGER.info(districtName+"data values-->"+dataValues);
//		System.out.println("EVM REQUIREMENT LIST:"+evmRequirementModels.size());
//		System.out.println("EVM REQUIREMENT LIST:"+evmRequirementModels);
//		LOGGER.info(districtName+"Category Score-->"+categoryScore.get("M").size());
		
//		LOGGER.info(districtName+"Category weight-->"+categoryWeight.get("M").size());
		
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
		dataValueMap.put("categoryDataNode",evmCategoryClassificationModel);
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
	
	
	
	private EvmQuestionModel calculateE4_27(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
		
		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		XPath xPath = XPathFactory.newInstance().newXPath();
		EvmDataNode dataNode = new EvmDataNode();
		List<EvmDataNode> dataNodes = new ArrayList<>();
		evmQuestionModel.setName(question.getQuestionName());
		
		String relvance1 = xPath.compile(formPath+"E0_01/group_q3/E0_03a_A").evaluate(document);
		String relvance2 = xPath.compile(formPath+"E0_01/group_q3/E0_03a_B").evaluate(document);
		String valA=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(0).getXpath()).evaluate(document);
		String valB=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(1).getXpath()).evaluate(document);
		
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(0).getQuestionName());
		dataNode.setValue(valA);
		dataNodes.add(dataNode);
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(1).getQuestionName());
		dataNode.setValue(valB);
		dataNodes.add(dataNode);
		evmQuestionModel.setDataNodes(dataNodes);
		
		try{
		Double result_relvance = (relvance1.equals("yes") && relvance2.equals("yes") ? 0.5 : 1);
		
		Double val1 = (relvance1.equals("yes") && valA.equals("yes") ? 1.0 : 0.0);
		
		Double val2 = (relvance2.equals("yes") && valB.equals("yes") ? 1.0 : 0.0);
		
		evmQuestionModel.setValue(((val1 + val2) * result_relvance) * question.getWeightage());
		
		return evmQuestionModel;
		
		}
			catch (Exception e) {
				evmQuestionModel.setValue(0.0);
				return evmQuestionModel;
		}
		
		
	}
	
	private EvmQuestionModel evenByOdd(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		List<EvmDataNode> dataNodes = new ArrayList<>();
		Double odd=0.0;
		Double even=0.0;
		int num=0;
		XPath xPath = XPathFactory.newInstance().newXPath();
		evmQuestionModel.setName(question.getQuestionName());
		
		for(EvmSubQuestion subQuestion:question.getEvmSubQuestions()){
			String path = xPathString + question.getXpath() +"/"+subQuestion.getXpath();
			String value = (xPath.compile(path).evaluate(document));
			EvmDataNode dataNode = new EvmDataNode();
			dataNode.setName(subQuestion.getQuestionName());
			dataNode.setValue(value);
			dataNodes.add(dataNode);
		}
		evmQuestionModel.setDataNodes(dataNodes);
		
		for(EvmSubQuestion subQuestion:question.getEvmSubQuestions()){
			num++;
			
			String path = xPathString + question.getXpath() +"/"+subQuestion.getXpath();
			LOGGER.info("xpath:->"+xPathString);
			try {
				
				Double val=getDouble(xPath.compile(path).evaluate(document));
				
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
				evmQuestionModel.setValue(0.0);
				return evmQuestionModel;
			}
		}
		evmQuestionModel.setValue(even/odd*question.getWeightage());
		return evmQuestionModel;
	}
	
	private EvmQuestionModel E5_Exceptional(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		List<EvmDataNode> dataNodes = new ArrayList<>();
		Double odd=0.0;
		Double even=0.0;
		int num=0;
		XPath xPath = XPathFactory.newInstance().newXPath();
		evmQuestionModel.setName(question.getQuestionName());
		
		for(EvmSubQuestion subQuestion:question.getEvmSubQuestions()){
			String path = xPathString + question.getXpath() +"/"+subQuestion.getXpath();
			String value = xPath.compile(path).evaluate(document);
			EvmDataNode dataNode = new EvmDataNode();
			dataNode.setName(subQuestion.getQuestionName());
			dataNode.setValue(value);
			dataNodes.add(dataNode);
		}
		evmQuestionModel.setDataNodes(dataNodes);
		
		for(EvmSubQuestion subQuestion:question.getEvmSubQuestions()){
			num++;
			String path = xPathString + question.getXpath() +"/"+subQuestion.getXpath();
			LOGGER.info("xpath:->"+xPathString);
			try {
				Double val=Double.parseDouble(xPath.compile(path).evaluate(document));
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
				evmQuestionModel.setValue(0.0);
				return evmQuestionModel;
			}
		}
		evmQuestionModel.setValue((1-even/odd)*question.getWeightage());
		return evmQuestionModel;
	}
	
	private EvmQuestionModel calculateE7_11(Document document, EvmQuestion question,String xPathString) throws XPathExpressionException {

		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		XPath xPath = XPathFactory.newInstance().newXPath();
		EvmDataNode dataNode = null;
		List<EvmDataNode> dataNodes = new ArrayList<>();
		evmQuestionModel.setName(question.getQuestionName());
		String relvance="";
		
		if(formPath.contains("DVS") || formPath.contains("HF")){
			
		relvance = xPath.compile(formPath+"E7/E7_7.4.1/E7_09a").evaluate(document);
		
		}else{
			
			relvance = xPath.compile(formPath+"E7/E7_7.4/E7_09a").evaluate(document);
		}
		
		String A =xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(0).getXpath()).evaluate(document);
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(0).getQuestionName());
		dataNode.setValue(A);
		dataNodes.add(dataNode);
		
		String B = xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(1).getXpath()).evaluate(document);
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(1).getQuestionName());
		dataNode.setValue(B);
		dataNodes.add(dataNode);
		
		String valC=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(2).getXpath()).evaluate(document);
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(2).getQuestionName());
		dataNode.setValue(valC);
		dataNodes.add(dataNode);
		
		String var1 = xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(3).getXpath()).evaluate(document);
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(3).getQuestionName());
		dataNode.setValue(var1);
		dataNodes.add(dataNode);
		
		String var2 = xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(4).getXpath()).evaluate(document);
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(4).getQuestionName());
		dataNode.setValue(var2);
		dataNodes.add(dataNode);
		evmQuestionModel.setDataNodes(dataNodes);
		
		Double valA=getDouble(A);
		Double valB=getDouble(B);
		Double valD=getDouble(xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(3).getXpath()).evaluate(document));
		Double valE=getDouble(xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(4).getXpath()).evaluate(document));
		try {
						
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
			
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}

	}
		
	private EvmQuestionModel E4_02Boolean_Exception(Document document,EvmRequirement requirement, EvmQuestion question,String xPathString) throws XPathExpressionException {
			
			EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
			List<EvmDataNode> dataNodes = new ArrayList<>();
			EvmDataNode dataNode = new EvmDataNode();
			XPath xPath = XPathFactory.newInstance().newXPath();
			evmQuestionModel.setName(question.getQuestionName());
			
			String relvance = xPath.compile(formPath+"E0_01/group_q2/E0_02a_D").evaluate(document);
			
			if(!relvance.equals("yes")) {
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
			
	private EvmQuestionModel booleanMethod(Document document,EvmRequirement requirement, EvmQuestion question,String xPathString) throws XPathExpressionException {
			int ycount = 0;
			int tcount = 0;
			EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
			List<EvmDataNode> dataNodes = new ArrayList<>();
			evmQuestionModel.setName(question.getQuestionName());
				
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
					String newVal = "";
					if(strVal.equalsIgnoreCase("1")){
						newVal="Yes";
					}else if(strVal.equalsIgnoreCase("2")){
						newVal = "No";
					}else if(strVal.equalsIgnoreCase("3")){
						newVal = "N/A";
					}else{
						newVal = strVal;
					}
					dataNode.setValue(newVal);
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
