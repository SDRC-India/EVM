/**
 * 
 */
package org.sdrc.evm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.sdrc.evm.model.EvmDataNode;
import org.sdrc.evm.model.EvmQuestionModel;
import org.sdrc.evm.model.EvmRequirementModel;
import org.sdrc.odkaggregate.domain.EvmQuestion;
import org.sdrc.odkaggregate.domain.EvmRequirement;
import org.sdrc.odkaggregate.domain.EvmSubQuestion;
import org.sdrc.odkaggregate.domain.XForm;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

/**
 * @author kamal
 *
 */
@Component("REG")
public class BiharXFormProcess implements ProcessXForm {
	
	private static final Logger LOGGER = Logger.getLogger(BiharXFormProcess.class);
	
	private String formPath;

	/* (non-Javadoc)
	 * @see org.sdrc.evm.util.ProcessXForm#parseXML(org.w3c.dom.Document, org.sdrc.odkaggregate.domain.XForm)
	 */
	@Override
	public Map<String, Object> parseXML(Document document, XForm xForm) {
		System.out.println("In Bihar xForm process method.");
			
			Map<String, Object> dataValueMap=new HashMap<>();
			
			Map<String, List<Double>> categoryScore = new HashMap<>();
			
			Map<String, List<Double>> categoryWeight = new HashMap<>();
			
			String imgPath="";
			
			formPath=xForm.getSubmissionPath();
//			formPath = "/EVM_DVS_04082014_V1/";
//			formPath = "/EVM_HealthFacility_06082014_V1/";
//			formPath = "/EVM_SVS_04082014_V1/";
			System.out.println("in parse xml method.");
			try{
			
//			File xmlFile = new File("D:/submission.xml");
//			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder builder = builderFactory.newDocumentBuilder();
//			Document document = builder.parse(xmlFile);
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			document.getDocumentElement().normalize();
			
			List<EvmRequirement> evmRequirements=xForm.getEvmRequirements();
			
			String formType=xPath.compile(formPath+"g1/g1_4").evaluate(document);
			
//			String districtName=xPath.compile(formPath+"district").evaluate(document);
			String districtName=xForm.getLevelName().equalsIgnoreCase("HF") ?xPath.compile(formPath+"b/block").evaluate(document) : xPath.compile(formPath+"district").evaluate(document);
//			String districtName=xPath.compile(formPath+"b/block").evaluate(document);
			
			String facilityName=xPath.compile(formPath+"L2/L2_g1/L2_3").evaluate(document);
			
//			String facilityAddress=xPath.compile(formPath+"L2/L2_g1/L2_5").evaluate(document);
			
			String lat_long_info=xPath.compile(formPath+"L2/L4/L4_1").evaluate(document);
			
			if(!lat_long_info.equals(""))
				System.out.println(lat_long_info+"-->"+lat_long_info.split(" ")[0]+"-->"+lat_long_info.split(" ")[1]);
			
			Map<String ,Double> dataValues=new HashMap<>();
			
			LOGGER.info("District Name====================>"+districtName);
			LOGGER.info("=====================================================================================");
			
			Map<String,EvmRequirementModel> evmRequirementModels = new HashMap<>();
			Map<String, List<EvmQuestionModel>> evmCategoryClassificationModel =  new HashMap<>();
			for(EvmRequirement requirement:evmRequirements){//calculate each requirement value
				EvmRequirementModel evmRequirementModel = new EvmRequirementModel();
				Double scoreSum = 0.0;
				Double weightageSum = 0.0;
				Double percentVal = 0.0;
				System.out.println(requirement.getXpath());
				
				List<EvmQuestion> evmQuestions=requirement.getEvmQuestions();
				List<EvmQuestionModel> evmQuestionModels = new ArrayList<>();
				
				
//				System.out.println("evm Question size:->"+evmQuestions.size());
				
				for(EvmQuestion question:evmQuestions){//questions for each requirement
					
//					if(formType.equals("rvs")){
//						System.out.println("rvs"+question.getXpath());
//						if(question.getXpath().equals("E9_9.05") || question.getXpath().equals("E9_9.06") || question.getXpath().equals("E9_9.07")
//								||question.getXpath().equals("E9_9.08")||question.getXpath().equals("E9_9.09")||question.getXpath().equals("E9_9.11")
//								||question.getXpath().equals("E9_9.13")||question.getXpath().equals("E9_9.14")||question.getXpath().equals("E9_9.15")
//								||question.getXpath().equals("E9_9.16")||question.getXpath().equals("E2_2.2.1c")||question.getXpath().equals("E4_4.3.9")){
//							continue;
//						}
//					}
					int ycount = 0;
					int tcount = 0;
//					Double val=0.0;
					Double wt=0.0;
					List<EvmSubQuestion> evmSubQuestions=question.getEvmSubQuestions();
					
					String xPathString="";		
					EvmQuestionModel questionModel = new EvmQuestionModel();
					switch (question.getQuestionType()) {
					
					case "EvenByOdd":
						
						xPathString=formPath+requirement.getXpath()+"/";
						
						questionModel = evenByOdd(document, question, xPathString);
						
					//	LOGGER.info(question.getQuestionName()+"--"+question.getQuestionType()+"--"+val);
						
						break;
					case "E5Exception":
						
						xPathString=formPath+requirement.getXpath()+"/";
						
						questionModel = E5_Exceptional(document, question, xPathString);
						
					//	LOGGER.info(question.getQuestionName()+"--"+question.getQuestionType()+"--"+val);
						
						break;
					case "E4BooleanException":
						
//						EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
						List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
						questionModel.setName(question.getQuestionName());
						
						System.out.println("xpath:->"+formPath+requirement.getXpath()+"/"+question.getXpath());
						EvmDataNode dataNode= new EvmDataNode();
						for(EvmSubQuestion subQuestion:evmSubQuestions){
							if(requirement.getXpath()!=null)
								xPathString=formPath+requirement.getXpath()+"/"+question.getXpath()+"/"+subQuestion.getXpath();
							else
								xPathString=formPath+question.getXpath()+"/"+subQuestion.getXpath();
							
							System.out.println("xpath:->"+xPathString);
							
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
							dataNode= new EvmDataNode();
							dataNode.setName(subQuestion.getXpath());
							dataNode.setValue(newVal);
							dataNodes.add(dataNode);
							
							if(subQuestion.getExceptionrule() != null && subQuestion.getExceptionrule().length() > 0  )
							{
								strVal = subQuestion.getExceptionrule();
							}
							
//							System.out.println("value-->"+val);
							
							if(strVal.equalsIgnoreCase("yes") || strVal.equalsIgnoreCase("1"))
								ycount++;
							
							if(!strVal.equals(""))
								tcount++;
							
							if(tcount!=0 && strVal.equalsIgnoreCase("3"))
								tcount--;
							
						}
						
						if(tcount!=0){
							questionModel.setValue(question.getWeightage() * ycount / tcount);
					//		LOGGER.info(question.getQuestionName()+"--"+question.getQuestionType()+"--"+question.getWeightage() * ycount / tcount);
						}else{
							LOGGER.info(question.getQuestionName()+"-->"+question.getQuestionType()+"-->"+0);
							questionModel.setValue(0.0);
						}
						questionModel.setDataNodes(dataNodes);
						break;
					case "E6BooleanException":
						System.out.println("xpath:->"+formPath+requirement.getXpath()+"/"+question.getXpath());
							
						xPathString=formPath+requirement.getXpath()+"/";
						questionModel = E6_BooleanException(document, question, xPathString);
						
						break;
						
					case "E8_15_BooleanException":
						System.out.println("xpath:->"+formPath+requirement.getXpath()+"/"+question.getXpath());
							
						xPathString=formPath+requirement.getXpath()+"/";
						questionModel = E8_15_BooleanException(document, question, xPathString);
						
						break;
						
					case "E8_16_BooleanException":
						System.out.println("xpath:->"+formPath+requirement.getXpath()+"/"+question.getXpath());
							
						xPathString=formPath+requirement.getXpath()+"/";
						questionModel = E8_16_BooleanException(document, question, xPathString);
						
						break;
						
					case "Boolean":
						
						xPathString=formPath+requirement.getXpath()+"/";
						
						
						switch (question.getQuestionName()) {
						
						case "E3_3.4.1" :
							questionModel=calculateE3_8(document, question, xPathString);
							
							LOGGER.info(question.getQuestionName()+"--"+question.getQuestionType()+"--"+questionModel.getValue());
							break;
							
						case "E3_3.4.2" :
							questionModel=calculateE3_9(document, question, xPathString);
							
							LOGGER.info(question.getQuestionName()+"--"+question.getQuestionType()+"--"+questionModel.getValue());
							
							break;
						
						case "E6_6.16":
							
							questionModel=calculateE6_14(document, question, xPathString);
							
				//			LOGGER.info(question.getQuestionName()+"--"+question.getQuestionType()+"--"+val);
							
							break;
							
						case "E7_7.5":
							
							questionModel=calculateE7_11(document, question, xPathString);
							
			//				LOGGER.info(question.getQuestionName()+"--"+question.getQuestionType()+"--"+val);
							
							break;

						default:
							
							System.out.println("xpath:->"+formPath+requirement.getXpath()+"/"+question.getXpath());
							dataNodes= new ArrayList<EvmDataNode>();
							for(EvmSubQuestion subQuestion:evmSubQuestions){
								xPathString="";
								if(requirement.getXpath()!=null)
									xPathString=xPathString+formPath+requirement.getXpath();
								else
									xPathString=xPathString+formPath;
								
								if(question.getXpath()!=null)
									xPathString=xPathString + "/"+question.getXpath()+"/"+subQuestion.getXpath();
								else
									xPathString=xPathString +"/"+subQuestion.getXpath();
								
								System.out.println("xpath:->"+xPathString);
								dataNode= new EvmDataNode();
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
								dataNode.setName(subQuestion.getXpath());
								dataNode.setValue(newVal);
								dataNodes.add(dataNode);
								
								if(subQuestion.getExceptionrule() != null && subQuestion.getExceptionrule().length() > 0  )
								{
									strVal = subQuestion.getExceptionrule();
								}
								
//								System.out.println("value-->"+val);
								
								if(strVal.equalsIgnoreCase("yes") || strVal.equalsIgnoreCase("1"))
									ycount++;
								
								//if(!val.equals(""))
									tcount++;
								
								if(tcount!=0 && strVal.equalsIgnoreCase("3"))
									tcount--;
								
							}
							
							questionModel.setName(question.getQuestionName());
							questionModel.setValue(question.getWeightage() * ycount / tcount);
							questionModel.setDataNodes(dataNodes);
							
							break;
						}
						
						break;
					case "Numeric" :
						
						xPathString=formPath+requirement.getXpath()+"/";
						
						System.out.println("Xpath-->"+xPathString+"/"+question.getQuestionName());
						
						switch (question.getQuestionName()) {
						
						case "E6_6.16":
							
							questionModel=calculateE6_15(document, question, xPathString);
							break;
							
						case "E6_6.3.1":
							
							questionModel=calculateE6_21(document, question, xPathString);
							break;
							
						case "E6_6_23":
							
							questionModel=calculateE6_22(document, question, xPathString);
							break;
							
							
						case "E6_6.3.2":
							
							questionModel=calculateE6_23(document, question, xPathString);
							break;
							
						case "E7_7.02":
							
							questionModel=calculateE7_2(document, question, xPathString);
							break;
							
						case "E7_7.03":
							
							questionModel=calculateE7_3(document, question, xPathString);
							break;
							
						case "E7_7.2":
							
							questionModel=calculateE7_5(document, question, xPathString);
							break;
							
						case "E7_7.10":
							
							questionModel=calculateE7_9(document, question, xPathString);
							break;
							
						case "E7_7.11":
							
							questionModel=calculateE7_10(document, question, xPathString);
							break;
							
						case "E8_8.04":
							
							questionModel=calculateE8_4(document, question, xPathString);
							break;
							
						case "E9_9.05":
							
							questionModel=calculateE9_1(document, question, xPathString);
							break;
							
						case "E9_9.07":
							
							questionModel=calculateE9_6(document, question, xPathString);
							break;
							
						case "E9_9.08":
							
							questionModel=calculateE9_7(document, question, xPathString);
							break;
							
						case "E9_9.09":
							
							questionModel=calculateE9_8(document, question, xPathString);
							break;
							
						case "E3_3.1.1":
							
//							val=calculateE3_1(document, question, xPathString);
							questionModel=E3_Compare_Number(document, question, xPathString);
							break;
							
						case "E3_3.02":
							
//							val=calculateE3_2(document, question, xPathString);
							questionModel=E3_Compare_Number(document, question, xPathString);
							break;
							
						case "E3_3.1.2":
							
//							val=calculateE3_3(document, question, xPathString);
							questionModel=E3_Compare_Number(document, question, xPathString);
							break;
							
						case "E3_3.05":
							
//							val=calculateE3_4(document, question, xPathString);
							questionModel=E3_Compare_Number(document, question, xPathString);
							break;
							
						case "E3_3.06":
							
//							val=calculateE3_5(document, question, xPathString);
							questionModel=E3_Compare_Number(document, question, xPathString);
							break;
							
						case "E3_3.2.2":
							
//							val=calculateE3_6(document, question, xPathString);
							questionModel=E3_Compare_Number(document, question, xPathString);
							break;
							
						case "E3_3.3.1":
							
//							val=calculateE3_7(document, question, xPathString);
							questionModel=E3_Compare_Number(document, question, xPathString);
							break;
							
						case "E2_2.2.1d":
							
							questionModel=calculateE2_2(document, question, xPathString);
							break;
							
						case "E1_1.3":
							
							questionModel=calculateE1_2(document, question, xPathString);
							break;
							
						case "E1_1.4":
							
							questionModel=calculateE1_3_4(document, question, xPathString);
							break;
							
						case "E1_1.5":
							
							questionModel=calculateE1_3_4(document, question, xPathString);
							break;
							
						case "E1_1.6":
							
							questionModel=calculateE1_5_6_7(document, question, xPathString);
							break;
							
						case "E1_1.7":
							
							questionModel=calculateE1_5_6_7(document, question, xPathString);
							break;
							
						case "E1_1.8":
							
							questionModel=calculateE1_5_6_7(document, question, xPathString);
							break;
							
						case "E.8.12" :
							
							questionModel=calculateE8_12(document, question, xPathString);
							
							break;

						default:
							
//							val=calculateNumericVal(document, question, xPathString);
							break;
						}
						
						break;

					default:
						break;
					}
					
					LOGGER.info(question.getQuestionName()+"--"+question.getQuestionType()+"--"+questionModel.getValue());
					
					/**
					 * calculate score and weight  
					 */
					
					if(!(questionModel.getValue()==null || questionModel.getValue().isNaN())){
						
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
//							val=val.isNaN()?0.0:val;
							categoryScore.get(question.getDevinfoclassificationName()).add(questionModel.getValue()==null || questionModel.getValue().isNaN()?0.0:questionModel.getValue());
							
						}else{
//							val=val.isNaN()?0.0:val;
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
//				percentVal = Double.parseDouble(df.format(scoreSum * 100 / weightageSum));
				percentVal = scoreSum * 100 / weightageSum;
				
				evmRequirementModel.setName(requirement.getIndicatorName().split("_")[2]); // set requirement name, value after percentVal calculation
				evmRequirementModel.setValue(new Double(Math.round(percentVal)));
				evmRequirementModel.setEvmQuestionModels(evmQuestionModels);
				evmRequirementModels.put(evmRequirementModel.getName(),evmRequirementModel);
				
				LOGGER.info("Total Score-->"+scoreSum+"Weightage Sum-->"+weightageSum+"Percent val : "+percentVal);
				
				dataValues.put(requirement.getIndicatorName(), new Double(Math.round(percentVal)));
				
				
			}
			
//			LOGGER.info(districtName+"data values-->"+dataValues);
			
	//		LOGGER.info(districtName+"Category Score-->"+categoryScore);
			
	//		LOGGER.info(districtName+"Category weight-->"+categoryWeight);
			
			for(String category:categoryScore.keySet()){
				
				try {
					Double catScore = sum(categoryScore.get(category));
					Double maxScore = sum(categoryWeight.get(category));
		//			LOGGER.info(districtName+"Category -->"+catScore/maxScore*100);
					Double catVal= catScore/maxScore*100;
//					Double catValue = Double.parseDouble(df.format(catScore/maxScore*100)) ;
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
			dataValueMap.put("categoryDataNode",evmCategoryClassificationModel);
			dataValueMap.put("imgPath",imgPath );
			if(!lat_long_info.equals("")){
				System.out.println(lat_long_info+"-->"+lat_long_info.split(" ")[0]+"-->"+lat_long_info.split(" ")[1]);
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
	private Double getDouble(String str){
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			return 0.0;
		}
	}
	
	public boolean isEven(double num) { 
		
		return ((num % 2) == 0) ;
	}
	
	private EvmQuestionModel evenByOdd(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
        Double odd=0.0;
        Double even=0.0;
        XPath xPath = XPathFactory.newInstance().newXPath();
        
        EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
        List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
        evmQuestionModel.setName(question.getQuestionName());
        /*
         * Set data nodes 
         */
        for(EvmSubQuestion subQuestion:question.getEvmSubQuestions()){
                
                String path = xPathString + question.getXpath() +"/"+subQuestion.getXpath();
                
                EvmDataNode dataNode= new EvmDataNode();
                
                dataNode.setName(subQuestion.getXpath());
                dataNode.setValue(xPath.compile(path).evaluate(document));
                dataNodes.add(dataNode);
                
        }
        evmQuestionModel.setDataNodes(dataNodes);
        
        int num=0;
        for(EvmSubQuestion subQuestion:question.getEvmSubQuestions()){
                num++;
                String path = xPathString + question.getXpath() +"/"+subQuestion.getXpath();
                try {
                        
                        Double val=getDouble(xPath.compile(path).evaluate(document));
                        
                        
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
        Double odd=0.0;
        Double even=0.0;
        XPath xPath = XPathFactory.newInstance().newXPath();
        
        EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
        List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
        evmQuestionModel.setName(question.getQuestionName());
        /*
         * Set data nodes 
         */
        for(EvmSubQuestion subQuestion:question.getEvmSubQuestions()){
                String path = xPathString + question.getXpath() +"/"+subQuestion.getXpath();
                EvmDataNode dataNode= new EvmDataNode();
                dataNode.setName(subQuestion.getXpath());
                dataNode.setValue(xPath.compile(path).evaluate(document));
                dataNodes.add(dataNode);
        }
        evmQuestionModel.setDataNodes(dataNodes);
        
        int num=0;
        for(EvmSubQuestion subQuestion:question.getEvmSubQuestions()){
                num++;
                String path = xPathString + question.getXpath() +"/"+subQuestion.getXpath();
                try {
                        Double val=Double.parseDouble(xPath.compile(path).evaluate(document));
                        
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

//questions fetched manually
	private EvmQuestionModel E6_BooleanException(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
        
        EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
        List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
        evmQuestionModel.setName(question.getQuestionName());
        EvmDataNode dataNode= new EvmDataNode();
        
        XPath xPath = XPathFactory.newInstance().newXPath();
        
        int ncount = 0;
        String newVal = "";
        String valA=xPath.compile(xPathString+question.getXpath()+"/"+"E6_15a_A").evaluate(document);
		if(valA.equalsIgnoreCase("1")){
			newVal="Yes";
		}else if(valA.equalsIgnoreCase("2")){
			newVal = "No";
		}else if(valA.equalsIgnoreCase("3")){
			newVal = "N/A";
		}else{
			newVal = valA;
		}
        dataNode.setName("E6_15a_A");
        dataNode.setValue(newVal);
        dataNodes.add(dataNode);
        
        String valB=xPath.compile(xPathString+question.getXpath()+"/"+"E6_15a_B").evaluate(document);
		if(valB.equalsIgnoreCase("1")){
			newVal="Yes";
		}else if(valB.equalsIgnoreCase("2")){
			newVal = "No";
		}else if(valB.equalsIgnoreCase("3")){
			newVal = "N/A";
		}else{
			newVal = valB;
		}
        dataNode = new EvmDataNode();
        dataNode.setName("E6_15a_B");
        dataNode.setValue(newVal);
        dataNodes.add(dataNode);
        
        String valC=xPath.compile(xPathString+question.getXpath()+"/"+"E6_15a_C").evaluate(document);
		if(valC.equalsIgnoreCase("1")){
			newVal="Yes";
		}else if(valC.equalsIgnoreCase("2")){
			newVal = "No";
		}else if(valC.equalsIgnoreCase("3")){
			newVal = "N/A";
		}else{
			newVal = valC;
		}
        dataNode = new EvmDataNode();
        dataNode.setName("E6_15a_C");
        dataNode.setValue(newVal);
        dataNodes.add(dataNode);
        
        String valD=xPath.compile(xPathString+question.getXpath()+"/"+"E6_15a_D").evaluate(document);
    	if(valD.equalsIgnoreCase("1")){
			newVal="Yes";
		}else if(valD.equalsIgnoreCase("2")){
			newVal = "No";
		}else if(valD.equalsIgnoreCase("3")){
			newVal = "N/A";
		}else{
			newVal = valD;
		}
        dataNode = new EvmDataNode();
        dataNode.setName("E6_15a_D");
        dataNode.setValue(newVal);
        dataNodes.add(dataNode);
        //set data nodes
        evmQuestionModel.setDataNodes(dataNodes);
        if(valA.equals("1") || valA.equals("yes"))
            ncount++;
	    if(valB.equals("1") || valB.equals("yes"))
	            ncount++;
	    if((valA.equals("1")|| valA.equals("yes")) && valB.equals("3"))
	            ncount++;
	    
	    if(valC.equals("1") || valC.equals("yes"))
	            ncount++;
	    if(valD.equals("1") || valD.equals("yes"))
	            ncount++;
	    if((valC.equals("1")|| valC.equals("yes")) && valD.equals("3"))
	            ncount++;
	    
	    evmQuestionModel.setValue(question.getWeightage() * ncount/4);
	    
	    return evmQuestionModel;
}

	private EvmQuestionModel E8_15_BooleanException(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
	       EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	       List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
	       evmQuestionModel.setName(question.getQuestionName());
	       EvmDataNode dataNode = new EvmDataNode();
	       
	       XPath xPath = XPathFactory.newInstance().newXPath();
	       
	       
	       List<EvmSubQuestion> evmSubQuestions = question.getEvmSubQuestions();
	       
	       String valA=xPath.compile(xPathString+question.getXpath()+"/"+evmSubQuestions.get(0).getXpath()).evaluate(document);
	       dataNode.setName(evmSubQuestions.get(0).getXpath());
	       dataNode.setValue(valA);
	       dataNodes.add(dataNode);
	       
	       String valB=xPath.compile(xPathString+question.getXpath()+"/"+evmSubQuestions.get(1).getXpath()).evaluate(document);
	       dataNode = new EvmDataNode();
	       dataNode.setName(evmSubQuestions.get(1).getXpath());
	       dataNode.setValue(valB);
	       dataNodes.add(dataNode);
	       
	       String valC=xPath.compile(xPathString+question.getXpath()+"/"+evmSubQuestions.get(2).getXpath()).evaluate(document);
	       dataNode = new EvmDataNode();
	       dataNode.setName(evmSubQuestions.get(2).getXpath());
	       dataNode.setValue(valC);
	       dataNodes.add(dataNode);
	       
	       String valD=xPath.compile(xPathString+question.getXpath()+"/"+evmSubQuestions.get(3).getXpath()).evaluate(document);
	       dataNode = new EvmDataNode();
	       dataNode.setName(evmSubQuestions.get(3).getXpath());
	       dataNode.setValue(valD);
	       dataNodes.add(dataNode);
	       String valE=xPath.compile(xPathString+question.getXpath()+"/"+evmSubQuestions.get(4).getXpath()).evaluate(document);
	       dataNode = new EvmDataNode();
	       dataNode.setName(evmSubQuestions.get(4).getXpath());
	       dataNode.setValue(valE);
	       dataNodes.add(dataNode);
	       evmQuestionModel.setDataNodes(dataNodes);
	       
	       if(valA.equals("yes") && (valC.equals("yes") || valD.equals("yes") || valE.equals("yes")))
	               evmQuestionModel.setValue(question.getWeightage());
	       else if(valB.equals("yes") && (valD.equals("yes") || valE.equals("yes")))
	               evmQuestionModel.setValue(question.getWeightage());
	       else
	               evmQuestionModel.setValue(0.0);
	       
	       return evmQuestionModel;
	       
	}

	private EvmQuestionModel E8_16_BooleanException(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
	       
	       EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	       List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
	       evmQuestionModel.setName(question.getQuestionName());
	       EvmDataNode dataNode= new EvmDataNode();
	       
	       XPath xPath = XPathFactory.newInstance().newXPath();
	       
	       List<EvmSubQuestion> evmSubQuestions = question.getEvmSubQuestions();
	       
	       String valA=xPath.compile(xPathString+question.getXpath()+"/"+evmSubQuestions.get(0).getXpath()).evaluate(document);
	       dataNode.setName(evmSubQuestions.get(0).getXpath());
	       dataNode.setValue(valA);
	       dataNodes.add(dataNode);
	       
	       String valB=xPath.compile(xPathString+question.getXpath()+"/"+evmSubQuestions.get(1).getXpath()).evaluate(document);
	       dataNode = new EvmDataNode();
	       dataNode.setName(evmSubQuestions.get(1).getXpath());
	       dataNode.setValue(valB);
	       dataNodes.add(dataNode);
	       
	       String valC=xPath.compile(xPathString+question.getXpath()+"/"+evmSubQuestions.get(2).getXpath()).evaluate(document);
	       dataNode = new EvmDataNode();
	       dataNode.setName(evmSubQuestions.get(2).getXpath());
	       dataNode.setValue(valC);
	       dataNodes.add(dataNode);
	       evmQuestionModel.setDataNodes(dataNodes);
	       
	       if(valA.equals("yes") && valB.equals("yes") && valC.equals("yes") )
	               evmQuestionModel.setValue(0.0);
	       else if(valA.equals("yes") && valC.equals("yes"))
	               evmQuestionModel.setValue(0.0);
	       else if(valB.equals("yes") && valC.equals("yes"))
	               evmQuestionModel.setValue(0.0);
	       else if(!valA.equals("yes") && !valB.equals("yes") && !valC.equals("yes") )
	    	   evmQuestionModel.setValue(0.0);
	       else
	               evmQuestionModel.setValue(question.getWeightage());
	       
	       return evmQuestionModel;
	       
	}

	private EvmQuestionModel calculateE3_8(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
	       
	       EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	       List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>(); 
	       evmQuestionModel.setName(question.getQuestionName());
	       EvmDataNode dataNode = new EvmDataNode();
	       
	       XPath xPath = XPathFactory.newInstance().newXPath();
	               
	       String valA=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(0).getXpath()).evaluate(document);
	       dataNode.setName(question.getEvmSubQuestions().get(0).getXpath());
	       dataNode.setValue(valA);
	       dataNodes.add(dataNode);
	       
	       String valB=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(1).getXpath()).evaluate(document);
	       dataNode = new EvmDataNode();
	       dataNode.setName(question.getEvmSubQuestions().get(1).getXpath());
	       dataNode.setValue(valB);
	       dataNodes.add(dataNode);
	       
	       evmQuestionModel.setDataNodes(dataNodes);
	       
	       if(valA.equals("yes")) evmQuestionModel.setValue(question.getWeightage());
	       
	       else if(!valA.equals("yes") && valB.equals("yes")) evmQuestionModel.setValue(question.getWeightage()/2);
	       
	       else  evmQuestionModel.setValue(0.0);
	       
	       return evmQuestionModel;
	}

private EvmQuestionModel calculateE3_9(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
	
	EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
	evmQuestionModel.setName(question.getQuestionName());	
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	
	EvmDataNode dataNode= new EvmDataNode();
	String newVal = "";
	String valA=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(0).getXpath()).evaluate(document);
	if(valA.equalsIgnoreCase("1")){
		newVal="Yes";
	}else if(valA.equalsIgnoreCase("2")){
		newVal = "No";
	}else if(valA.equalsIgnoreCase("3")){
		newVal = "N/A";
	}else{
		newVal = valA;
	}
	dataNode.setName(question.getEvmSubQuestions().get(0).getXpath());
	dataNode.setValue(newVal);
	dataNodes.add(dataNode);
	
	String valB=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(1).getXpath()).evaluate(document);
	if(valB.equalsIgnoreCase("1")){
		newVal="Yes";
	}else if(valB.equalsIgnoreCase("2")){
		newVal = "No";
	}else if(valB.equalsIgnoreCase("3")){
		newVal = "N/A";
	}else{
		newVal = valB;
	}
	dataNode = new EvmDataNode();
	dataNode.setName(question.getEvmSubQuestions().get(1).getXpath());
	dataNode.setValue(newVal);
	dataNodes.add(dataNode);
	evmQuestionModel.setDataNodes(dataNodes);
	
	if(valA.equals("yes") && (valB.equals("yes") || valB.equals("1")) ) {
		evmQuestionModel.setValue(question.getWeightage());
		return evmQuestionModel;
	}
	
	else evmQuestionModel.setValue(0.0);
	return evmQuestionModel;
	
}

//question fetched manually
private EvmQuestionModel calculateE6_14(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
//	Double dataValue=0.0;
	EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
	evmQuestionModel.setName(question.getQuestionName());		
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	EvmDataNode dataNode= new EvmDataNode();
	String newVal = "";
	String valA=xPath.compile(xPathString+question.getXpath()+"/"+"E6_15a_A").evaluate(document);
	if(valA.equalsIgnoreCase("1")){
		newVal="Yes";
	}else if(valA.equalsIgnoreCase("2")){
		newVal = "No";
	}else if(valA.equalsIgnoreCase("3")){
		newVal = "N/A";
	}else{
		newVal = valA;
	}
	dataNode.setName("E6_15a_A");
	dataNode.setValue(newVal);
	dataNodes.add(dataNode);
	
	String valB=xPath.compile(xPathString+question.getXpath()+"/"+"E6_15a_B").evaluate(document);
	if(valB.equalsIgnoreCase("1")){
		newVal="Yes";
	}else if(valB.equalsIgnoreCase("2")){
		newVal = "No";
	}else if(valB.equalsIgnoreCase("3")){
		newVal = "N/A";
	}else{
		newVal = valB;
	}
	dataNode = new EvmDataNode();
	dataNode.setName("E6_15a_B");
	dataNode.setValue(newVal);
	dataNodes.add(dataNode);
	
	String valC=xPath.compile(xPathString+question.getXpath()+"/"+"E6_15a_C").evaluate(document);
	if(valC.equalsIgnoreCase("1")){
		newVal="Yes";
	}else if(valC.equalsIgnoreCase("2")){
		newVal = "No";
	}else if(valC.equalsIgnoreCase("3")){
		newVal = "N/A";
	}else{
		newVal = valC;
	}
	dataNode = new EvmDataNode();
	dataNode.setName("E6_15a_C");
	dataNode.setValue(newVal);
	dataNodes.add(dataNode);
	
	String valD=xPath.compile(xPathString+question.getXpath()+"/"+"E6_15a_D").evaluate(document);
	if(valD.equalsIgnoreCase("1")){
		newVal="Yes";
	}else if(valD.equalsIgnoreCase("2")){
		newVal = "No";
	}else if(valD.equalsIgnoreCase("3")){
		newVal = "N/A";
	}else{
		newVal = valD;
	}
	dataNode = new EvmDataNode();
	dataNode.setName("E6_15a_D");
	dataNode.setValue(newVal);
	dataNodes.add(dataNode);
	evmQuestionModel.setDataNodes(dataNodes);
	
	int numerator=0;
	if(valA.equals("yes")) numerator++;
	
	if(valA.equals("yes") && (valB.equals("yes") || valB.equals("3"))) numerator++;
	
	if(valC.equals("yes")) numerator++;
	
	if(valC.equals("yes") && (valD.equals("yes") || valD.equals("3"))) numerator++;
	
	if(numerator==0){
		evmQuestionModel.setValue(0.0);
		return evmQuestionModel;
	}
	
	else evmQuestionModel.setValue(numerator/4 * question.getWeightage());
	return evmQuestionModel;
	
}

//question fetched manually
private EvmQuestionModel calculateE7_11(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
//	Double dataValue=0.0;
	EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
	evmQuestionModel.setName(question.getQuestionName());
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	EvmDataNode dataNode= new EvmDataNode();
	
	String valA=xPath.compile(xPathString+question.getXpath()+"/"+"E7_12a_A").evaluate(document);
	dataNode.setName("E7_12a_A");
	dataNode.setValue(valA);
	dataNodes.add(dataNode);
	
	String valB=xPath.compile(xPathString+question.getXpath()+"/"+"E7_12a_B").evaluate(document);
	dataNode = new EvmDataNode();
	dataNode.setName("E7_12a_B");
	dataNode.setValue(valB);
	dataNodes.add(dataNode);
	evmQuestionModel.setDataNodes(dataNodes);
		
		if(valA.equals("yes")){
			evmQuestionModel.setValue(question.getWeightage());
			return evmQuestionModel;
		}else if(valA.equals("no") && valB.equals("yes")) {
			evmQuestionModel.setValue(question.getWeightage()/2);
			return evmQuestionModel;
		}else evmQuestionModel.setValue(0.0);
		return evmQuestionModel;
}

//question fetched manually
private EvmQuestionModel calculateE6_15(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
	Double dataValue=0.0;
	EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
	evmQuestionModel.setName(question.getQuestionName());
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	EvmDataNode dataNode= new EvmDataNode();
	
	String val=xPath.compile(xPathString+question.getXpath()+"/"+"E6_16a_A").evaluate(document);
	dataNode.setName("E6_16a_A");
	dataNode.setValue(val);
	dataNodes.add(dataNode);
	
	String valA=xPath.compile(xPathString+question.getXpath()+"/"+"E6_16a_B").evaluate(document);
	dataNode = new EvmDataNode();
	dataNode.setName("E6_16a_B");
	dataNode.setValue(valA);
	dataNodes.add(dataNode);
	
	String valB=xPath.compile(xPathString+question.getXpath()+"/"+"E6_16a_B").evaluate(document);
//	dataNode = new EvmDataNode();
//	dataNode.setName("E6_16a_B");
//	dataNode.setValue(valB);
//	dataNodes.add(dataNode);
	
	String valC=xPath.compile(xPathString+question.getXpath()+"/"+"E6_16a_C").evaluate(document);
	dataNode = new EvmDataNode();
	dataNode.setName("E6_16a_C");
	dataNode.setValue(valC);
	dataNodes.add(dataNode);
	evmQuestionModel.setDataNodes(dataNodes);
	
	try {
		if(val.trim().equals("")){
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
			}else if (valA.trim().equals("")){
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
			}else if (valB.equals("0")) {
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
			}
		dataValue = Double.parseDouble(valC)/Double.parseDouble(valB);
		
		if(dataValue > 0.01 ) {
			evmQuestionModel.setValue(0.0) ;
			return evmQuestionModel;
			}
		else {
			evmQuestionModel.setValue(question.getWeightage());
			return evmQuestionModel;
		}
		
	} catch (Exception e) {
		evmQuestionModel.setValue(0.0);
		return evmQuestionModel;
	}
}

//question fetched manually
private EvmQuestionModel calculateE6_21(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
//	Double dataValue=0.0;
	EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
	evmQuestionModel.setName(question.getQuestionName());
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	EvmDataNode dataNode= new EvmDataNode();
	
	String valA=xPath.compile(xPathString+question.getXpath()+"/"+"E6_22a_A").evaluate(document);
	dataNode.setName("E6_22a_A");
	dataNode.setValue(valA);
	dataNodes.add(dataNode);
	
	String valB=xPath.compile(xPathString+question.getXpath()+"/"+"E6_22a_B").evaluate(document);
	dataNode = new EvmDataNode();
	dataNode.setName("E6_22a_B");
	dataNode.setValue(valB);
	dataNodes.add(dataNode);
	evmQuestionModel.setDataNodes(dataNodes);
	
	try {
		if(Double.parseDouble(valA) < 1 ){
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}else if (Double.parseDouble(valB) >= 12/Double.parseDouble(valA)) {
			evmQuestionModel.setValue(question.getWeightage());
			return evmQuestionModel;
		}
//		else if(Double.parseDouble(valB) >= Double.parseDouble(valA)) return question.getWeightage();
		else{
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}
	} catch (Exception e) {
		evmQuestionModel.setValue(0.0);
		return evmQuestionModel;
	}
}

//question fetched manually
private EvmQuestionModel calculateE6_22(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
//	Double dataValue=0.0;
	EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
	evmQuestionModel.setName(question.getQuestionName());
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	EvmDataNode dataNode= new EvmDataNode();
	
	String valA=xPath.compile(xPathString+question.getXpath()+"/"+"E6_23a_E").evaluate(document);
	dataNode.setName("E6_23a_E");
	dataNode.setValue(valA);
	dataNodes.add(dataNode);
	evmQuestionModel.setDataNodes(dataNodes);
//	String valB=xPath.compile(xPathString+question.getXpath()+"/"+"E6_22a_B").evaluate(document);
	try {
		
		if(Double.parseDouble(valA) < 0 || Double.parseDouble(valA) > 4) {
			evmQuestionModel.setValue(Double.NaN);
			return evmQuestionModel;
		}else {
			evmQuestionModel.setValue(Double.parseDouble(valA) * question.getWeightage() /4);
			return evmQuestionModel;
		}
		
	} catch (Exception e) {
		
		evmQuestionModel.setValue(0.0);
		return evmQuestionModel;
	}
}

//question fetched manually
private EvmQuestionModel calculateE6_23(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
//	Double dataValue=0.0;
	EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
	evmQuestionModel.setName(question.getQuestionName());
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	EvmDataNode dataNode= new EvmDataNode();
	
	String valA=xPath.compile(xPathString+question.getXpath()+"/"+"E6_24a_D").evaluate(document);
	dataNode.setName("E6_24a_D");
	dataNode.setValue(valA);
	dataNodes.add(dataNode);
	evmQuestionModel.setDataNodes(dataNodes);
//	String valB=xPath.compile(xPathString+question.getXpath()+"/"+"E6_22a_B").evaluate(document);
	try {
		if(Double.parseDouble(valA) < 0 || Double.parseDouble(valA) > 4) {
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}else {
			evmQuestionModel.setValue(Double.parseDouble(valA) * question.getWeightage() /4);
			return evmQuestionModel;
		}
	} catch (Exception e) {
		evmQuestionModel.setValue(0.0);
		return evmQuestionModel;
	}
	
}

//question fetched manually
private EvmQuestionModel calculateE7_2(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
//	Double dataValue=0.0;
	EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
	evmQuestionModel.setName(question.getQuestionName());
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	EvmDataNode dataNode= new EvmDataNode();
	String A = xPath.compile(xPathString+question.getXpath()+"/"+"E7_02a_A").evaluate(document);
	dataNode.setName("E7_02a_A");
	dataNode.setValue(A);
	dataNodes.add(dataNode);
	
	String B = xPath.compile(xPathString+question.getXpath()+"/"+"E7_02a_B").evaluate(document);
	dataNode = new EvmDataNode();
	dataNode.setName("E7_02a_B");
	dataNode.setValue(B);
	dataNodes.add(dataNode);
	evmQuestionModel.setDataNodes(dataNodes);
	
	try {
		Double valA=Double.parseDouble(A);
		
		Double valB=Double.parseDouble(B);
		
		if(valB/valA <= 1.2 && valB/valA >= 0.8) {
			evmQuestionModel.setValue(question.getWeightage());
			return evmQuestionModel;
		}else {
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}
	} catch (Exception e) {
		evmQuestionModel.setValue(0.0);
		return evmQuestionModel;
	}
}

//question fetched manually
private EvmQuestionModel calculateE7_3(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
//	Double dataValue=0.0;
	EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
	evmQuestionModel.setName(question.getQuestionName());
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	EvmDataNode dataNode= new EvmDataNode();
	
	String A = xPath.compile(xPathString+question.getXpath()+"/"+"E7_03a_A").evaluate(document);
	dataNode.setName("E7_03a_A");
	dataNode.setValue(A);
	dataNodes.add(dataNode);
	
	String B = xPath.compile(xPathString+question.getXpath()+"/"+"E7_03a_B").evaluate(document);
	dataNode = new EvmDataNode();
	dataNode.setName("E7_03a_B");
	dataNode.setValue(B);
	dataNodes.add(dataNode);
	evmQuestionModel.setDataNodes(dataNodes);
	
	try {
		Double valA=Double.parseDouble(A);
		Double valB=Double.parseDouble(B);
		
		if(valB/valA > 1 ) {
			evmQuestionModel.setValue(valA/valB * question.getWeightage());
			return evmQuestionModel;
		}else {
			evmQuestionModel.setValue(valB/valA * question.getWeightage());
			return evmQuestionModel;
		}
		
	} catch (Exception e) {
		evmQuestionModel.setValue(0.0);
		return evmQuestionModel;
	}
}

//question fetched manually
private EvmQuestionModel calculateE7_5(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
//	Double dataValue=0.0;
	EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
	evmQuestionModel.setName(question.getQuestionName());
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	EvmDataNode dataNode= new EvmDataNode();
	
	String valA=xPath.compile(xPathString+question.getXpath()+"/"+"E7_05a_B").evaluate(document);
	dataNode.setName("E7_05a_B");
	dataNode.setValue(valA);
	dataNodes.add(dataNode);
	evmQuestionModel.setDataNodes(dataNodes);
//	String valB=xPath.compile(xPathString+question.getXpath()+"/"+"E6_22a_B").evaluate(document);
	try {
		
		if(Double.parseDouble(valA) < 0 || Double.parseDouble(valA) > 4) {
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}else {
			evmQuestionModel.setValue(Double.parseDouble(valA) * question.getWeightage() /4);
			return evmQuestionModel;
		}
		
	} catch (Exception e) {
		evmQuestionModel.setValue(0.0);
		return evmQuestionModel;
	}
}	

//question fetched manually
private EvmQuestionModel calculateE7_9(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
//	Double dataValue=0.0;
	EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
	evmQuestionModel.setName(question.getQuestionName());
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	EvmDataNode dataNode= new EvmDataNode();
	
	String valA=xPath.compile(xPathString+question.getXpath()+"/"+"E7_10a").evaluate(document);
	dataNode.setName("E7_10a");
	dataNode.setValue(valA);
	dataNodes.add(dataNode);
	evmQuestionModel.setDataNodes(dataNodes);
//	String valB=xPath.compile(xPathString+question.getXpath()+"/"+"E6_22a_B").evaluate(document);
	
	try {
		
		if(Double.parseDouble(valA) < 0 || Double.parseDouble(valA) > 4) {
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}else {
			evmQuestionModel.setValue(Double.parseDouble(valA) * question.getWeightage() /4);
			return evmQuestionModel;
		}
	} catch (Exception e) {
		evmQuestionModel.setValue(0.0);
		return evmQuestionModel;
	}
}

private EvmQuestionModel calculateE7_10(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
//	Double dataValue=0.0;
	EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
	evmQuestionModel.setName(question.getQuestionName());
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	EvmDataNode dataNode= new EvmDataNode();
	
	List<EvmSubQuestion> subQuestions = question.getEvmSubQuestions();
	
	String check=xPath.compile(xPathString+"E7_7.4/E7_09a").evaluate(document);
	
	String A = xPath.compile(xPathString+question.getXpath()+"/"+subQuestions.get(0).getXpath()).evaluate(document);
	dataNode.setName(subQuestions.get(0).getXpath());
	dataNode.setValue(A);
	dataNodes.add(dataNode);
	
	String C = xPath.compile(xPathString+question.getXpath()+"/"+subQuestions.get(2).getXpath()).evaluate(document);
	dataNode = new EvmDataNode();
	dataNode.setName(subQuestions.get(2).getXpath());
	dataNode.setValue(C);
	dataNodes.add(dataNode);
	
	String D = xPath.compile(xPathString+question.getXpath()+"/"+subQuestions.get(3).getXpath()).evaluate(document);
	dataNode = new EvmDataNode();
	dataNode.setName(subQuestions.get(3).getXpath());
	dataNode.setValue(D);
	dataNodes.add(dataNode);
	
	Double valA=getDouble(A);
//	Double valB=getDouble(xPath.compile(xPathString+question.getXpath()+"/"+subQuestions.get(1).getXpath()).evaluate(document));
	Double valC=getDouble(C);
	Double valD=getDouble(D);
	evmQuestionModel.setDataNodes(dataNodes);
	
	if(valD > valC) {
		evmQuestionModel.setValue(0.0);
		return evmQuestionModel;
	}
	else if(check.equals("no")) {
		evmQuestionModel.setValue(valC/valA*question.getWeightage());
		return evmQuestionModel;
	}
	else if(check.equals("yes")) {
		evmQuestionModel.setValue(valD/valA*question.getWeightage());
		return evmQuestionModel;
	}
	else evmQuestionModel.setValue(0.0);
	return evmQuestionModel;
}

//question fetched manually
private EvmQuestionModel calculateE8_4(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
//	Double dataValue=0.0;
	EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
	evmQuestionModel.setName(question.getQuestionName());
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	EvmDataNode dataNode= new EvmDataNode();
	
	String valA=xPath.compile(xPathString+question.getXpath()+"/"+"E8_12a").evaluate(document);
	dataNode.setName("E8_12a");
	dataNode.setValue(valA.toString());
	dataNodes.add(dataNode);
	evmQuestionModel.setDataNodes(dataNodes);
//	String valB=xPath.compile(xPathString+question.getXpath()+"/"+"E6_22a_B").evaluate(document);
	
	try {
		
		if(Double.parseDouble(valA) < 0 || Double.parseDouble(valA) > 4) {
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}else{
			evmQuestionModel.setValue(Double.parseDouble(valA) * question.getWeightage() /4);
			return evmQuestionModel;
		}
	} catch (Exception e) {
		evmQuestionModel.setValue(0.0);
		return evmQuestionModel;
	}
}

//question fetched manually
private EvmQuestionModel calculateE9_1(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
    EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
    List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
    evmQuestionModel.setName(question.getQuestionName());
    
    XPath xPath = XPathFactory.newInstance().newXPath();
    EvmDataNode dataNode= new EvmDataNode();
    
    String valA=xPath.compile(xPathString+question.getXpath()+"/"+"E9_02a").evaluate(document);
    dataNode.setName("E9_02a");
    dataNode.setValue(valA);
    dataNodes.add(dataNode);
    evmQuestionModel.setDataNodes(dataNodes);
    
    try {
            
            if(Double.parseDouble(valA) < 0 || Double.parseDouble(valA) > 4) evmQuestionModel.setValue(0.0);
            
            else evmQuestionModel.setValue(Double.parseDouble(valA) * question.getWeightage() /4);
            
    } catch (Exception e) {
            
            evmQuestionModel.setValue(0.0);
    }
    
    return evmQuestionModel;
}

//question fetched manually
private EvmQuestionModel calculateE9_6(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
//  Double dataValue=0.0;
 EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
 List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
 evmQuestionModel.setName(question.getQuestionName());
 
 XPath xPath = XPathFactory.newInstance().newXPath();
 EvmDataNode dataNode= new EvmDataNode();
 
 String valA=xPath.compile(xPathString+question.getXpath()+"/"+"E9_09a").evaluate(document);
 dataNode.setName("E9_09a");
 dataNode.setValue(valA);
 dataNodes.add(dataNode);
 evmQuestionModel.setDataNodes(dataNodes);
 try {
         
         if(Double.parseDouble(valA) < 0 || Double.parseDouble(valA) > 4) evmQuestionModel.setValue(0.0);
         
         
         else evmQuestionModel.setValue(Double.parseDouble(valA) * question.getWeightage() /4);
         
 } catch (Exception e) {
         
         evmQuestionModel.setValue(0.0);
 }
 
 return evmQuestionModel;
}

//question fetched manually
private EvmQuestionModel calculateE9_7(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
//  Double dataValue=0.0;
 EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
 List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
 evmQuestionModel.setName(question.getQuestionName());
 
 XPath xPath = XPathFactory.newInstance().newXPath();
 EvmDataNode dataNode= new EvmDataNode();
 
 String valA=xPath.compile(xPathString+question.getXpath()+"/"+"E9_10a_B").evaluate(document);
 dataNode.setName("E9_10a_B");
 dataNode.setValue(valA);
 dataNodes.add(dataNode);
 evmQuestionModel.setDataNodes(dataNodes);
 
 try {
         
         if(Double.parseDouble(valA) < 0 || Double.parseDouble(valA) > 4) evmQuestionModel.setValue(0.0);
         
         
         else evmQuestionModel.setValue(Double.parseDouble(valA) * question.getWeightage() /4);
         
 } catch (Exception e) {
         
         evmQuestionModel.setValue(0.0);
 }
 
 return evmQuestionModel;
}

//question fetched manually
private EvmQuestionModel calculateE9_8(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
//  Double dataValue=0.0;
 EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
 List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
 evmQuestionModel.setName(question.getQuestionName());
 
 XPath xPath = XPathFactory.newInstance().newXPath();
 EvmDataNode dataNode= new EvmDataNode();
 
 String valA=xPath.compile(xPathString+question.getXpath()+"/"+"E9_11a_A").evaluate(document);
 dataNode.setName("E9_11a_A");
 dataNode.setValue(valA);
 dataNodes.add(dataNode);
 
 String valB=xPath.compile(xPathString+question.getXpath()+"/"+"E9_11a_B").evaluate(document);
 dataNode = new EvmDataNode();
 dataNode.setName("E9_11a_B");
 dataNode.setValue(valB);
 dataNodes.add(dataNode);
 evmQuestionModel.setDataNodes(dataNodes);
 try {
         
         if(valA.equals("3")) evmQuestionModel.setValue(0.0);
         
         else if(Double.parseDouble(valB) < 0 || Double.parseDouble(valB) > 4) evmQuestionModel.setValue(0.0);
         
         
         else evmQuestionModel.setValue(Double.parseDouble(valB) * question.getWeightage() /4);
         
 } catch (Exception e) {
         
         evmQuestionModel.setValue(0.0);
 }
 
 return evmQuestionModel;
}

private EvmQuestionModel E3_Compare_Number(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
	EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
	evmQuestionModel.setName(question.getQuestionName());
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	EvmDataNode dataNode= new EvmDataNode();
	
	List<EvmSubQuestion> evmSubQuestions = question.getEvmSubQuestions();
	boolean check=true;
	int cnt=0;
	Double denominator=0.0;
	for(EvmSubQuestion subQuestion:evmSubQuestions){
		dataNode = new EvmDataNode();
		if(cnt<1){
			denominator = getDouble(xPath.compile(xPathString+question.getXpath()+"/"+subQuestion.getXpath()).evaluate(document));
			dataNode.setName(subQuestion.getXpath());
			dataNode.setValue(denominator.toString());
			dataNodes.add(dataNode);
		} else{
			Double numerator = getDouble(xPath.compile(xPathString+question.getXpath()+"/"+subQuestion.getXpath()).evaluate(document));
			dataNode.setName(subQuestion.getXpath());
			dataNode.setValue(numerator.toString());
			dataNodes.add(dataNode);
			
			Double val= numerator/denominator;
			if(val.isNaN()) check=false;//evmQuestionModel.setValue(0.0);
			else if(val.isInfinite()) check=false;//evmQuestionModel.setValue(0.0);
			else{
				if(val>1){
					check=false;
					break;
				}
			}
		}
		cnt++;
	}
	evmQuestionModel.setDataNodes(dataNodes);
	evmQuestionModel.setValue(check ?	question.getWeightage() : 0.0);	
	return evmQuestionModel;
}

//question fetched manually
private EvmQuestionModel calculateE2_2(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
//	Double dataValue=0.0;
	EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
	evmQuestionModel.setName(question.getQuestionName());
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	EvmDataNode dataNode= new EvmDataNode();
	
	String valA=xPath.compile(xPathString+question.getXpath()+"/"+"E2_02a_A").evaluate(document);
	dataNode.setName("E2_02a_A");
	dataNode.setValue(valA);
	dataNodes.add(dataNode);
	
	String valB=xPath.compile(xPathString+question.getXpath()+"/"+"E2_02a_B").evaluate(document);
	dataNode = new EvmDataNode();
	dataNode.setName("E2_02a_B");
	dataNode.setValue(valB);
	dataNodes.add(dataNode);
	evmQuestionModel.setDataNodes(dataNodes);
	
	try {
		evmQuestionModel.setValue(Double.parseDouble(valB)/Double.parseDouble(valA)*question.getWeightage());
		return evmQuestionModel;
	} catch (Exception e) {
		evmQuestionModel.setValue(0.0);
		return evmQuestionModel;
	}
}

//question fetched manually
private EvmQuestionModel calculateE1_2(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
//	Double dataValue=0.0;
	EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
	evmQuestionModel.setName(question.getQuestionName());
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	EvmDataNode dataNode= new EvmDataNode();	
	
	String A = xPath.compile(xPathString+question.getXpath()+"/"+"E1_03a_A").evaluate(document);
	dataNode.setName("E1_03a_A");
	dataNode.setValue(A);
	dataNodes.add(dataNode);
	
	String B = xPath.compile(xPathString+question.getXpath()+"/"+"E1_03a_B").evaluate(document);
	dataNode = new EvmDataNode();
	dataNode.setName("E1_03a_B");
	dataNode.setValue(B);
	dataNodes.add(dataNode);

	String C= xPath.compile(xPathString+question.getXpath()+"/"+"E1_03a_C").evaluate(document);
	dataNode = new EvmDataNode();
	dataNode.setName("E1_03a_C");
	dataNode.setValue(C);
	dataNodes.add(dataNode);
	evmQuestionModel.setDataNodes(dataNodes);
	
	try {
		
		Double valA=Double.parseDouble(A);
		Double valB=Double.parseDouble(B);
		Double valC=Double.parseDouble(C);
		
		if(valB == 0.0) {
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}
		
		else {
			evmQuestionModel.setValue(valB/valA * valC/valB * question.getWeightage());
			return evmQuestionModel;
		}
		
	} catch (Exception e) {
		evmQuestionModel.setValue(0.0);
		return evmQuestionModel;
	}
}

private EvmQuestionModel calculateE1_3_4(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
//	Double dataValue=0.0;
	EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
	evmQuestionModel.setName(question.getQuestionName());
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	EvmDataNode dataNode= new EvmDataNode();	
	String A = xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(0).getXpath()).evaluate(document);
	dataNode.setName(question.getEvmSubQuestions().get(0).getXpath());
	dataNode.setValue(A);
	dataNodes.add(dataNode);
	
	String B = xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(1).getXpath()).evaluate(document);
	dataNode= new EvmDataNode();
	dataNode.setName(question.getEvmSubQuestions().get(1).getXpath());
	dataNode.setValue(B);
	dataNodes.add(dataNode);
	evmQuestionModel.setDataNodes(dataNodes);
	
	try {
		
		Double valA=Double.parseDouble(A);
		if(valA == 0.0) {
			evmQuestionModel.setValue(question.getWeightage());
			return evmQuestionModel;
		}
		Double valB=Double.parseDouble(B);
		evmQuestionModel.setValue(valB/valA * question.getWeightage());
		return evmQuestionModel;
		
	} catch (Exception e) {		
		evmQuestionModel.setValue(0.0);
		return evmQuestionModel;
	}
	
}

private EvmQuestionModel calculateE1_5_6_7(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
//	Double dataValue=0.0;
	EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
	evmQuestionModel.setName(question.getQuestionName());
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	EvmDataNode dataNode= new EvmDataNode();
	
	String A = xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(0).getXpath()).evaluate(document);
	dataNode.setName(question.getEvmSubQuestions().get(0).getXpath());
	dataNode.setValue(A);
	dataNodes.add(dataNode);
	
	String B = xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(1).getXpath()).evaluate(document);
	dataNode = new EvmDataNode();
	dataNode.setName(question.getEvmSubQuestions().get(1).getXpath());
	dataNode.setValue(B);
	dataNodes.add(dataNode);
	evmQuestionModel.setDataNodes(dataNodes);
	try {
		
		Double valA=Double.parseDouble(A);
		Double valB=Double.parseDouble(B);
		
		if(valB > valA) {
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}else {
			evmQuestionModel.setValue(valB/valA * question.getWeightage());
			return evmQuestionModel;
		}
		
	} catch (Exception e) {
		evmQuestionModel.setValue(0.0);
		return evmQuestionModel;
	}
}

//question fetched manually
private EvmQuestionModel calculateE8_12(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
//	Double dataValue=0.0;
	EvmQuestionModel evmQuestionModel= new EvmQuestionModel();
	List<EvmDataNode> dataNodes= new ArrayList<EvmDataNode>();
	evmQuestionModel.setName(question.getQuestionName());
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	EvmDataNode dataNode= new EvmDataNode();
	
	String valA=xPath.compile(xPathString+question.getXpath()+"/"+"E8_12a").evaluate(document);
	dataNode.setName("E8_12a");
	dataNode.setValue(valA);
	dataNodes.add(dataNode);
	evmQuestionModel.setDataNodes(dataNodes);
	
//	String valB=xPath.compile(xPathString+question.getXpath()+"/"+"E6_22a_B").evaluate(document);
	
	try {
		
		if(Double.parseDouble(valA) < 0 || Double.parseDouble(valA) > 4) {
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}
		else {
			evmQuestionModel.setValue(Double.parseDouble(valA) * question.getWeightage() /4);
			return evmQuestionModel;
		}
		
	} catch (Exception e) {
		evmQuestionModel.setValue(0.0);
		return evmQuestionModel;
	}
}

private Double calculateNumericVal(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	
	ResourceBundle bundle = ResourceBundle.getBundle("messages/indicatorinfo");
	
	String questionInfoString = bundle.getString(question.getQuestionName());
	
	boolean isException=false;
	
	int tcount = 0;
	
	List<Double>  vals=new ArrayList<>();
	
	for(String info:questionInfoString.split("#")){
		
		Double value=0.0;
		
		for(String xPathName:info.split("@")){
			
			System.out.println("xpath-->"+xPathString+question.getXpath()+"/"+xPathName);
			
			String val=xPath.compile(xPathString+question.getXpath()+"/"+xPathName).evaluate(document);
			
			try {
				
				value=value+Double.parseDouble(val);
				
			} catch (Exception e) {
				
				isException=true;
			}
			
			if(!val.equals("")){
				
				tcount++;
				
			}
		}
			
		vals.add(value);
		
	}
	if(question.getQuestionName().equalsIgnoreCase("E5_5.05")){
		String weightA=xPath.compile(formPath+"E0_01/group_q1/E0_01a_B").evaluate(document);
		if(!weightA.equals("yes")) return Double.NaN;
	}
	else if(question.getQuestionName().equalsIgnoreCase("E5_5.2.1")){
		
		String weightA=xPath.compile(formPath+"E0_01/group_q1/E0_01a_C").evaluate(document);
		String weightB=xPath.compile(formPath+"E5_5.1/E5_5.2.1/E5_06a_A").evaluate(document);
		
		if(!weightA.equals("yes") || weightB.equals("0")) return Double.NaN;
	}
	
	if(tcount==0) return 0.0;
	
	if(isException) return 0.0;
	
	else return vals.get(0)/vals.get(1)*question.getWeightage();
	
}

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

private double calculateWeightNotY(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException{
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	
	String relevantQuestion=question.getWeightageRelevant();
	
	boolean check=true;
	
	for(String path : relevantQuestion.split(",")){
		
		String val=xPath.compile(formPath+path).evaluate(document);
		
		if(val.equals("yes")){
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
		
		if(!val.equals("yes")) {
			check=false;
			break;
		}
		
		
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
		
		if(val.equals("yes")) check=true;
		
		
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
		
		if(val.equalsIgnoreCase("no")) check=false;
		
		
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
		
		if(!val.equals("yes")) {
			check=false;
			break;
		}
		
		
	}
	
	if(check) return 0.0;
	
	else return question.getWeightage();
}

private double calculateMultiWeighte3(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException{
	
	XPath xPath = XPathFactory.newInstance().newXPath();
	
	String valA=xPath.compile(formPath+"E0_01/group_q3/E0_03a_A").evaluate(document);
//		System.out.println(formPath+question.getXpath()+"/E3_3.4.2/E3_10a_A");
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

}
