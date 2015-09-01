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
public class E9ServiceImpl implements E9Service {

	@Override
	public EvmQuestionModel calculateRange0To4(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
		
		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		XPath xPath = XPathFactory.newInstance().newXPath();
		EvmDataNode dataNode = null;
		List<EvmDataNode> dataNodes = new ArrayList<>();
		
		String valA=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(0).getXpath()).evaluate(document);
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(0).getQuestionName());
		dataNode.setValue(valA.toString());
		dataNodes.add(dataNode);
		try {
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			
			if(Double.parseDouble(valA) < 0 || Double.parseDouble(valA) > 4){
				evmQuestionModel.setValue(0.0);
				return evmQuestionModel;
			}
			
			
			else{
				evmQuestionModel.setValue(Double.parseDouble(valA) * question.getWeightage() /4);
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
	public EvmQuestionModel calculateValDivideBy4(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
		
		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		XPath xPath = XPathFactory.newInstance().newXPath();
		EvmDataNode dataNode = null;
		List<EvmDataNode> dataNodes = new ArrayList<>();
		
		String valA=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(0).getXpath()).evaluate(document);
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(0).getQuestionName());
		dataNode.setValue(valA.toString());
		dataNodes.add(dataNode);
		
		try {
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(Double.parseDouble(valA) * question.getWeightage() /4);
			return evmQuestionModel;
			
		} catch (Exception e) {
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}
		
	}
	
	@Override
	public EvmQuestionModel calculateE9_24(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
		
		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		XPath xPath = XPathFactory.newInstance().newXPath();
		EvmDataNode dataNode = null;
		List<EvmDataNode> dataNodes = new ArrayList<>();
		
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
		
		try {
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue((valB.equals("yes") ? 1 : .5 )* (Double.parseDouble(valA)>4 ? 1 : Double.parseDouble(valA)/4) * question.getWeightage());
			return evmQuestionModel;
			
		} catch (Exception e) {
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}
		
	}
	
	@Override
	public EvmQuestionModel calculateE9_20(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
		
		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		XPath xPath = XPathFactory.newInstance().newXPath();
		EvmDataNode dataNode = null;
		List<EvmDataNode> dataNodes = new ArrayList<>();
		
		String valA=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(0).getXpath()).evaluate(document);
		String valB=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(1).getXpath()).evaluate(document);
		String valC=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(2).getXpath()).evaluate(document);
		String valD=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(3).getXpath()).evaluate(document);
		
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(0).getQuestionName());
		dataNode.setValue(valA);
		dataNodes.add(dataNode);
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(1).getQuestionName());
		dataNode.setValue(valB);
		dataNodes.add(dataNode);
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(2).getQuestionName());
		dataNode.setValue(valC);
		dataNodes.add(dataNode);
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(3).getQuestionName());
		dataNode.setValue(valD);
		dataNodes.add(dataNode);
		
		try {
			Double val = (valA.equals("yes") ? .4 : 0) + (valB.equals("yes") ? .4 : 0) + (valC.equals("yes") ? .1 : 0) + (valD.equals("yes") ? .1 : 0);
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(val * question.getWeightage());
			return evmQuestionModel;
			
		} catch (Exception e) {
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}
		
	}
}
