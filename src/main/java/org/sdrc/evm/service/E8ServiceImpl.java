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
public class E8ServiceImpl implements E8Service {

	@Override
	public EvmQuestionModel e8_13_BooleanException(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException 
	{
		
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
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(3).getQuestionName());
		dataNode.setValue(valD.toString());
		dataNodes.add(dataNode);
		
		Double ycount = 0.0;
		Double tcount = 4.0;
		Double nAcount = 0.0;
		
		ycount+= valA.equals("yes") || valA.equals("1") ? 1 : 0;
		ycount+= valB.equals("yes") || valB.equals("1") ? 1 : 0;
		ycount+= valC.equals("yes") || valC.equals("1") ? 1 : 0;
		ycount+= valD.equals("yes") || valD.equals("1") ? 1 : 0;
		
		if(!valC.equals("yes") || !valC.equals("1")){
			tcount = 3.0;
		}else{
			nAcount+=valD.equals("3")? 1 : 0;
		}
		evmQuestionModel.setName(question.getQuestionName());
		evmQuestionModel.setDataNodes(dataNodes);
		evmQuestionModel.setValue(question.getWeightage() * ycount / (tcount-nAcount));
		return evmQuestionModel;
		
	}
	
	@Override
	public EvmQuestionModel e8_11(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException 
	{
		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		XPath xPath = XPathFactory.newInstance().newXPath();
		EvmDataNode dataNode = null;
		List<EvmDataNode> dataNodes = new ArrayList<>();
		
		String valB=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(0).getXpath()).evaluate(document);
		String valC=xPath.compile(xPathString+question.getXpath()+"/"+question.getEvmSubQuestions().get(1).getXpath()).evaluate(document);
		
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(0).getQuestionName());
		dataNode.setValue(valB);
		dataNodes.add(dataNode);
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(1).getQuestionName());
		dataNode.setValue(valC);
		dataNodes.add(dataNode);
		evmQuestionModel.setName(question.getQuestionName());
		evmQuestionModel.setDataNodes(dataNodes);
		
		Double ycount = 0.0;
		ycount+= valB.equals("yes") || valB.equals("1") ? 1 : 0;
		ycount+= (valC.equals("yes") || valC.equals("1")) && (valB.equals("yes") || valB.equals("1")) ? 1 : 0;
		
		evmQuestionModel.setName(question.getQuestionName());
		evmQuestionModel.setDataNodes(dataNodes);
		evmQuestionModel.setValue(ycount * (question.getWeightage()/2));
		return evmQuestionModel;
	}
	
	
}
