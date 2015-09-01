package org.sdrc.evm.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.sdrc.evm.model.EvmDataNode;
import org.sdrc.evm.model.EvmQuestionModel;
import org.sdrc.odkaggregate.domain.EvmQuestion;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
@Service
public class E3ServiceImpl implements E3Service {
	
	@Override
	public EvmQuestionModel calculateNetStorage(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
		
		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		List<EvmDataNode> dataNodes = new ArrayList<>();
		EvmDataNode dataNode = null;
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		String valA=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(0).getXpath()).evaluate(document);
		dataNode = new EvmDataNode();
		dataNode.setValue(valA);
		dataNode.setName(question.getEvmSubQuestions().get(0).getQuestionName());
		dataNodes.add(dataNode);
		
		String valB=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(1).getXpath()).evaluate(document);
		dataNode = new EvmDataNode();
		dataNode.setValue(valB);
		dataNode.setName(question.getEvmSubQuestions().get(1).getQuestionName());
		dataNodes.add(dataNode);
		
		try {
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			
			if(Double.parseDouble(valB) == 0 || Double.parseDouble(valA) == 0){
				evmQuestionModel.setValue(0.0);
				return evmQuestionModel;
			}
			else{
				Double var = (Double.parseDouble(valA) > Double.parseDouble(valB) ? 1 : Double.parseDouble(valA)/Double.parseDouble(valB)) * question.getWeightage();
				evmQuestionModel.setValue(var);
				return evmQuestionModel;
			}
					
		} catch (Exception e) {
			
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}
		
	}
	
	@Override
	public EvmQuestionModel calculateE3_09(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
		
		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		List<EvmDataNode> dataNodes = new ArrayList<>();
		EvmDataNode dataNode = null;
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		 String valA=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(0).getXpath()).evaluate(document);
		 dataNode = new EvmDataNode();
		 dataNode.setValue(valA);
		 dataNode.setName(question.getEvmSubQuestions().get(0).getQuestionName());
		 dataNodes.add(dataNode);
		 
		 Double valB=getDouble(xPath.compile(xPathString + question.getXpath() + "/"+ question.getEvmSubQuestions().get(1).getXpath()).evaluate(document));
		 dataNode = new EvmDataNode();
		 dataNode.setValue(valB.toString());
		 dataNode.setName(question.getEvmSubQuestions().get(1).getQuestionName());
		 dataNodes.add(dataNode);
		 
		 Double valC=getDouble(xPath.compile(xPathString + question.getXpath() + "/"+ question.getEvmSubQuestions().get(2).getXpath()).evaluate(document));
		 dataNode = new EvmDataNode();
		 dataNode.setValue(valC.toString());
		 dataNode.setName(question.getEvmSubQuestions().get(2).getQuestionName());
		 dataNodes.add(dataNode);
		 
		 String valD=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(3).getXpath()).evaluate(document);
		 dataNode = new EvmDataNode();
		 dataNode.setValue(valD);
		 dataNode.setName(question.getEvmSubQuestions().get(3).getQuestionName());
		 dataNodes.add(dataNode);
		 
		 Double valE=getDouble(xPath.compile(xPathString + question.getXpath() + "/"+ question.getEvmSubQuestions().get(4).getXpath()).evaluate(document));
		 dataNode = new EvmDataNode();
		 dataNode.setValue(valE.toString());
		 dataNode.setName(question.getEvmSubQuestions().get(4).getQuestionName());
		 dataNodes.add(dataNode);
		 
		 Double valF=getDouble(xPath.compile(xPathString + question.getXpath() + "/"+ question.getEvmSubQuestions().get(5).getXpath()).evaluate(document));
		 dataNode = new EvmDataNode();
		 dataNode.setValue(valF.toString());
		 dataNode.setName(question.getEvmSubQuestions().get(5).getQuestionName());
		 dataNodes.add(dataNode);
		 
         try {
 			evmQuestionModel.setName(question.getQuestionName());
 			evmQuestionModel.setDataNodes(dataNodes);
             
             Double valPart1 = valA.equals("yes") ? (valB) == 0 || (valC) == 0 ? 0 : (valB) > (valC) ? 1 : (valB)/(valC) : 0;
             Double valPart2 = valD.equals("yes") ? (valE) == 0 || (valF) == 0 ? 0 : (valE) > (valF) ? 1 : (valE)/(valF) : 0;
             Double valNoCase1 = (valA.equals("no") ? 1.0 : 0);
             Double valNocase2 = (valD.equals("no") ? 1.0 :0);
             Double var = (((valPart1+valPart2)/(2-valNoCase1-valNocase2))*question.getWeightage());
             evmQuestionModel.setValue(var);
 			return evmQuestionModel;
 			
     } catch (Exception e) {
             
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
     }
}
     
     private Double getDouble(String str){
             try {
                     return Double.parseDouble(str);
             } catch (Exception e) {
                     return 0.0;
                     
             }
     
     }
}