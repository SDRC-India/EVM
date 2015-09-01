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
public class E5ServiceImpl implements E5Service {
	
	@Override
	public EvmQuestionModel multiplyWeight(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException 
	{
		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		XPath xPath = XPathFactory.newInstance().newXPath();
		EvmDataNode dataNode = null;
		List<EvmDataNode> dataNodes = new ArrayList<>();
		try {
			
//			if (xPath
//					.compile(
//							xPathString + question.getXpath() + "/"
//									+ question.getEvmSubQuestions().get(2).getXpath())
//					.evaluate(document).equals("")) {
//				return 0.0;
//			}
			Double valA = getDouble(xPath.compile(xPathString + question.getXpath() + "/"+ question.getEvmSubQuestions().get(0).getXpath()).evaluate(document));
			Double valB = getDouble(xPath.compile(xPathString + question.getXpath() + "/"+ question.getEvmSubQuestions().get(1).getXpath()).evaluate(document));
			Double valC = getDouble(xPath.compile(xPathString + question.getXpath() + "/"+ question.getEvmSubQuestions().get(2).getXpath()).evaluate(document));
			
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
			
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			if((valB+ valC) > valA){
				evmQuestionModel.setValue(0.0);
				return evmQuestionModel;
			}
			else {
				evmQuestionModel.setValue((valB - valC) / valA < 0.0 ? 0.0 : (valB - valC) / valA * question.getWeightage());
				return evmQuestionModel;
			}
			
		}
			
		catch (Exception e) 
		{
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
	
	@Override
	public EvmQuestionModel e5_2_BooleanException(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException 
	{
		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		XPath xPath = XPathFactory.newInstance().newXPath();
		EvmDataNode dataNode = null;
		List<EvmDataNode> dataNodes = new ArrayList<>();
		
		String valA=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(0).getXpath()).evaluate(document);
		String valB=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(1).getXpath()).evaluate(document);
		String valC=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(2).getXpath()).evaluate(document);
		String valD=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(3).getXpath()).evaluate(document);
		String valE=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(4).getXpath()).evaluate(document);
		String valF=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(5).getXpath()).evaluate(document);
		String valG=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(6).getXpath()).evaluate(document);
		
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
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(4).getQuestionName());
		dataNode.setValue(valE);
		dataNodes.add(dataNode);
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(5).getQuestionName());
		dataNode.setValue(valF);
		dataNodes.add(dataNode);
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(6).getQuestionName());
		dataNode.setValue(valG);
		dataNodes.add(dataNode);

		Double ycount = 0.0;
		Double tcount = 7.0;
		Double nAcount = 0.0;
		
		ycount+= valA.equals("yes") || valA.equals("1") ? 1 : 0;
		ycount+= valB.equals("yes") || valA.equals("1") ? 1 : 0;
		ycount+= valC.equals("yes") || valA.equals("1") ? 1 : 0;
		ycount+= valD.equals("yes") || valA.equals("1") ? 1 : 0;
		ycount+= valE.equals("yes") || valA.equals("1") ? 1 : 0;
		ycount+= valF.equals("yes") || valA.equals("1") ? 1 : 0;
		ycount+= valG.equals("yes") || valA.equals("1") ? 1 : 0;
		
		nAcount+=valE.equals("3")? 1 : 0;
		nAcount+=valF.equals("3")? 2 : valG.equals("3") ? 1 : 0;
		
		evmQuestionModel.setName(question.getQuestionName());
		evmQuestionModel.setDataNodes(dataNodes);
		evmQuestionModel.setValue(question.getWeightage() * ycount / (tcount-nAcount));
		return evmQuestionModel;
		
	}
	
}
