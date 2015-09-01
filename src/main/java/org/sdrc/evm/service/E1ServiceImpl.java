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
public class E1ServiceImpl implements E1Service {
	

	@Override
	public EvmQuestionModel calculateE1_03(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
		
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
		
		String valC=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(2).getXpath()).evaluate(document);
		dataNode = new EvmDataNode();
		dataNode.setValue(valC);
		dataNode.setName(question.getEvmSubQuestions().get(2).getQuestionName());
		dataNodes.add(dataNode);
		
		String valD=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(3).getXpath()).evaluate(document);
		dataNode = new EvmDataNode();
		dataNode.setValue(valD);
		dataNode.setName(question.getEvmSubQuestions().get(3).getQuestionName());
		dataNodes.add(dataNode);
		
		try {
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			Double var = (Double.parseDouble(valB) / Double.parseDouble(valA)) * (Double.parseDouble(valC) / Double.parseDouble(valB))* (1-(Double.parseDouble(valD) / Double.parseDouble(valA)))* question.getWeightage() /4;
			evmQuestionModel.setValue(var);
			return evmQuestionModel;
			
		} catch (Exception e) {
			
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}
		
	}
public EvmQuestionModel calculateE1_05(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
		

		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		List<EvmDataNode> dataNodes = new ArrayList<>();
		EvmDataNode dataNode = null;
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		try {
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			if (xPath
					.compile(
							xPathString + question.getXpath() + "/"
									+ question.getEvmSubQuestions().get(0).getXpath())
					.evaluate(document).equals("")) {
				evmQuestionModel.setValue(0.0);
				return evmQuestionModel;
			}
			Double valA = Double.parseDouble(xPath.compile(xPathString + question.getXpath() + "/"+ question.getEvmSubQuestions().get(0).getXpath()).evaluate(document));
			dataNode = new EvmDataNode();
			dataNode.setValue(valA.toString());
			dataNode.setName(question.getEvmSubQuestions().get(0).getQuestionName());
			dataNodes.add(dataNode);
			
			if(valA == 0){
				evmQuestionModel.setValue(question.getWeightage());
				return evmQuestionModel;
				
			}
				
			
			Double valB = Double.parseDouble(xPath.compile(xPathString + question.getXpath() + "/"+ question.getEvmSubQuestions().get(1).getXpath()).evaluate(document));
			dataNode = new EvmDataNode();
			dataNode.setValue(valB.toString());
			dataNode.setName(question.getEvmSubQuestions().get(1).getQuestionName());
			Double var = valB > valA ? 0.0  : valB/valA* question.getWeightage();
			evmQuestionModel.setValue(var);
			return evmQuestionModel;
			
		} catch (Exception e) {
			
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}
		
	}
	
	@Override
	public EvmQuestionModel calculateE1_06(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
		
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
		
		String valC=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(2).getXpath()).evaluate(document);
		dataNode = new EvmDataNode();
		dataNode.setValue(valC);
		dataNode.setName(question.getEvmSubQuestions().get(2).getQuestionName());
		dataNodes.add(dataNode);
		
		String valD=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(3).getXpath()).evaluate(document);
		dataNode = new EvmDataNode();
		dataNode.setValue(valD);
		dataNode.setName(question.getEvmSubQuestions().get(3).getQuestionName());
		dataNodes.add(dataNode);
		
		try {
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			Double var = (valC.equals("yes") ? (Double.parseDouble(valB) / Double.parseDouble(valA)) * (Double.parseDouble(valD) / Double.parseDouble(valA)) : (Double.parseDouble(valB) / Double.parseDouble(valA)) * 0.5);
			evmQuestionModel.setValue(var);
			return evmQuestionModel;
			
		} catch (Exception e) {
			
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}
		
	}
	
	@Override
	public EvmQuestionModel calculateE1_19(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
		
		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		List<EvmDataNode> dataNodes = new ArrayList<>();
		EvmDataNode dataNode = null;
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		
		try {
			
			Double valA = Double.parseDouble(xPath.compile(xPathString + question.getXpath() + "/"+ question.getEvmSubQuestions().get(0).getXpath()).evaluate(document));
			dataNode = new EvmDataNode();
			dataNode.setValue(valA.toString());
			dataNode.setName(question.getEvmSubQuestions().get(0).getQuestionName());
			dataNodes.add(dataNode);
			
			Double valB = Double.parseDouble(xPath.compile(xPathString + question.getXpath() + "/"+ question.getEvmSubQuestions().get(1).getXpath()).evaluate(document));
			dataNode = new EvmDataNode();
			dataNode.setValue(valB.toString());
			dataNode.setName(question.getEvmSubQuestions().get(1).getQuestionName());
			dataNodes.add(dataNode);
			
			Double valC = Double.parseDouble(xPath.compile(xPathString + question.getXpath() + "/"+ question.getEvmSubQuestions().get(2).getXpath()).evaluate(document));
			dataNode = new EvmDataNode();
			dataNode.setValue(valC.toString());
			dataNode.setName(question.getEvmSubQuestions().get(2).getQuestionName());
			dataNodes.add(dataNode);
			
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			
			if(valB > valA || valC > valB){
				evmQuestionModel.setValue(0.0);
				return evmQuestionModel;
			}
			
			else{
				evmQuestionModel.setValue((valB/valA * valC/valB) * question.getWeightage());
				return evmQuestionModel;
			}
					
		} catch (Exception e) {
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}
		
	}
	
  }
