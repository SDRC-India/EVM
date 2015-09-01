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
public class E7ServiceImpl implements E7Service {

	@Override
	public EvmQuestionModel calculateE7_2(Document document, EvmQuestion question,
			String xPathString) throws XPathExpressionException {

		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		XPath xPath = XPathFactory.newInstance().newXPath();
		EvmDataNode dataNode = null;
		List<EvmDataNode> dataNodes = new ArrayList<>();

		try {
			Double valA = Double.parseDouble(xPath.compile(
					xPathString + question.getXpath() + "/"
							+ question.getEvmSubQuestions().get(0).getXpath()).evaluate(
					document));
			Double valB = Double.parseDouble(xPath.compile(
					xPathString + question.getXpath() + "/"
							+ question.getEvmSubQuestions().get(1).getXpath()).evaluate(
					document));
			Double valC = Double.parseDouble(xPath.compile(
					xPathString + question.getXpath() + "/"
							+ question.getEvmSubQuestions().get(2).getXpath()).evaluate(
					document));
			
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
			dataNode.setValue(valC.toString());
			dataNodes.add(dataNode);
			Double result = valA == 0 || valB == 0 ? 0 : valC > valB ? 0
					: valB / valA <= 1.2 && valB / valA >= .8 ? valC / valB
							* question.getWeightage() : 0;
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(result);
			return evmQuestionModel;

		} catch (Exception e) {
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;

		}

	}

	@Override
	public EvmQuestionModel calculateE7_5(Document document, EvmQuestion question,
			String xPathString) throws XPathExpressionException {

		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		XPath xPath = XPathFactory.newInstance().newXPath();
		EvmDataNode dataNode = null;
		List<EvmDataNode> dataNodes = new ArrayList<>();

		try {
			
			Double valA = Double.parseDouble(xPath.compile(
					xPathString + question.getXpath() + "/"
							+ question.getEvmSubQuestions().get(0).getXpath()).evaluate(
					document));
			Double valB = Double.parseDouble(xPath.compile(
					xPathString + question.getXpath() + "/"
							+ question.getEvmSubQuestions().get(1).getXpath()).evaluate(
					document));
			dataNode = new EvmDataNode();
			dataNode.setName(question.getEvmSubQuestions().get(0).getQuestionName());
			dataNode.setValue(valA.toString());
			dataNodes.add(dataNode);
			dataNode = new EvmDataNode();
			dataNode.setName(question.getEvmSubQuestions().get(1).getQuestionName());
			dataNode.setValue(valB.toString());
			dataNodes.add(dataNode);
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
			
			evmQuestionModel.setValue(valB > valA ? 0.0 : valA == 0.0 ? question.getWeightage()
					: valB / valA * question.getWeightage());
			return evmQuestionModel;

		} catch (Exception e) {
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;		
			}

	}
	
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
	
//	public Double calculateE7_11(Document document, EvmQuestion question,String xPathString) throws XPathExpressionException {
//
//		XPath xPath = XPathFactory.newInstance().newXPath();
//		
//		Double valA=getDouble(xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(0).getXpath()).evaluate(document));
//		Double valB=getDouble(xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(1).getXpath()).evaluate(document));
//		String valC=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(2).getXpath()).evaluate(document);
//		Double valD=getDouble(xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(3).getXpath()).evaluate(document));
//		Double valE=getDouble(xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(4).getXpath()).evaluate(document));
//		
//		try {
//			
//			if(valA == 0) return 0.0;
//			
//			else if (valB > valA || valD > valB || valE > valB) return 0.0; 
//			
//			else if(valE == null) return question.getWeightage()*Double.parseDouble(valC)/ Double.parseDouble(valA); 
//			
//			else return question.getWeightage()* Math.min(Double.parseDouble(valC), valE)/ Double.parseDouble(valA); 
//			
//
//		} catch (Exception e) {
//
//			return 0.0;
//		}
//
//	}
	
	
	@Override
	public EvmQuestionModel calculateE7_14(Document document, EvmQuestion question,
			String xPathString) throws XPathExpressionException {

		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		XPath xPath = XPathFactory.newInstance().newXPath();
		EvmDataNode dataNode = null;
		List<EvmDataNode> dataNodes = new ArrayList<>();

		try {
			Double valA = Double.parseDouble(xPath.compile(
					xPathString + question.getXpath() + "/"
							+ question.getEvmSubQuestions().get(0).getXpath()).evaluate(
					document));
			Double valB = Double.parseDouble(xPath.compile(
					xPathString + question.getXpath() + "/"
							+ question.getEvmSubQuestions().get(1).getXpath()).evaluate(
					document));
			dataNode = new EvmDataNode();
			dataNode.setName(question.getEvmSubQuestions().get(0).getQuestionName());
			dataNode.setValue(valA.toString());
			dataNodes.add(dataNode);
			dataNode = new EvmDataNode();
			dataNode.setName(question.getEvmSubQuestions().get(1).getQuestionName());
			dataNode.setValue(valB.toString());
			dataNodes.add(dataNode);
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(valB > valA ? 0.0 : valB/valA * question.getWeightage());
			return evmQuestionModel;
			
		} catch (Exception e) {
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}

	}
	
}
