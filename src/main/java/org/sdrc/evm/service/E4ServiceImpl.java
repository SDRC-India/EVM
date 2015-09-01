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
public class E4ServiceImpl implements E4Service {

	@Override
	public EvmQuestionModel mulWeightDivide2(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException {
		
		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		List<EvmDataNode> dataNodes = new ArrayList<>();
		EvmDataNode dataNode = null;
		XPath xPath = XPathFactory.newInstance().newXPath();
		
try {
		    
			Double valA = getDouble(xPath.compile(
					xPathString + question.getXpath() + "/"
							+ question.getEvmSubQuestions().get(0).getXpath()).evaluate(
					document));
			dataNode = new EvmDataNode();
			dataNode.setValue(valA.toString());
			dataNode.setName(question.getEvmSubQuestions().get(0).getQuestionName());
			dataNodes.add(dataNode);
			
			Double valB = getDouble(xPath.compile(
					xPathString + question.getXpath() + "/"
							+ question.getEvmSubQuestions().get(1).getXpath()).evaluate(
					document));
			dataNode = new EvmDataNode();
			dataNode.setValue(valB.toString());
			dataNode.setName(question.getEvmSubQuestions().get(1).getQuestionName());
			dataNodes.add(dataNode);
			
			Double valC = getDouble(xPath.compile(
					xPathString + question.getXpath() + "/"
							+ question.getEvmSubQuestions().get(2).getXpath()).evaluate(
					document));
			dataNode = new EvmDataNode();
			dataNode.setValue(valC.toString());
			dataNode.setName(question.getEvmSubQuestions().get(2).getQuestionName());
			dataNodes.add(dataNode);
			
			Double valD = getDouble(xPath.compile(
					xPathString + question.getXpath() + "/"
							+ question.getEvmSubQuestions().get(3).getXpath()).evaluate(
					document));
			dataNode = new EvmDataNode();
			dataNode.setValue(valD.toString());
			dataNode.setName(question.getEvmSubQuestions().get(3).getQuestionName());
			dataNodes.add(dataNode);
			
			Double valE = getDouble(xPath.compile(
					xPathString + question.getXpath() + "/"
							+ question.getEvmSubQuestions().get(4).getXpath()).evaluate(
					document));
			dataNode = new EvmDataNode();
			dataNode.setValue(valE.toString());
			dataNode.setName(question.getEvmSubQuestions().get(4).getQuestionName());
			dataNodes.add(dataNode);
			
			Double valF = getDouble(xPath.compile(
					xPathString + question.getXpath() + "/"
							+ question.getEvmSubQuestions().get(5).getXpath()).evaluate(
					document));
			dataNode = new EvmDataNode();
			dataNode.setValue(valF.toString());
			dataNode.setName(question.getEvmSubQuestions().get(5).getQuestionName());
			dataNodes.add(dataNode);
			
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			
			Double res1 = (valB/valA)*(question.getWeightage() * (valA/(valA+valD))/2);
			Double res2 = (valC/valA)*(question.getWeightage() * (valA/(valA+valD))/2);
			Double res3 = (valE/valD)*(question.getWeightage() * (valD/(valA+valD))/2);
			Double res4 = (valF/valD)*(question.getWeightage() * (valD/(valA+valD))/2);
			
			Double val1 = res1.isInfinite() || res1.isNaN() ? 0.0 : res1;
			Double val2 = res2.isInfinite() || res2.isNaN() ? 0.0 : res2;
			Double val3 = res3.isInfinite() || res3.isNaN() ? 0.0 : res3;
			Double val4 = res4.isInfinite() || res4.isNaN() ? 0.0 : res4;
			
			Double val = val1 + val2 + val3 + val4;
			evmQuestionModel.setValue(val);
			return evmQuestionModel;
}
		catch (Exception e) 
		{
			
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}
				
	}

@Override
public EvmQuestionModel mulWeightDivide(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException 
{
	
	EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
	List<EvmDataNode> dataNodes = new ArrayList<>();
	EvmDataNode dataNode = null;
	XPath xPath = XPathFactory.newInstance().newXPath();
	
	try {
		    
			Double valA = getDouble(xPath.compile(
					xPathString + question.getXpath() + "/"
							+ question.getEvmSubQuestions().get(0).getXpath()).evaluate(
					document));
			dataNode = new EvmDataNode();
			dataNode.setValue(valA.toString());
			dataNode.setName(question.getEvmSubQuestions().get(0).getQuestionName());
			dataNodes.add(dataNode);
			
			Double valB = getDouble(xPath.compile(
					xPathString + question.getXpath() + "/"
							+ question.getEvmSubQuestions().get(1).getXpath()).evaluate(
					document));
			dataNode = new EvmDataNode();
			dataNode.setValue(valB.toString());
			dataNode.setName(question.getEvmSubQuestions().get(1).getQuestionName());
			dataNodes.add(dataNode);
			
			Double valC = getDouble(xPath.compile(
					xPathString + question.getXpath() + "/"
							+ question.getEvmSubQuestions().get(2).getXpath()).evaluate(
					document));
			dataNode = new EvmDataNode();
			dataNode.setValue(valC.toString());
			dataNode.setName(question.getEvmSubQuestions().get(2).getQuestionName());
			dataNodes.add(dataNode);
			
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			Double var = (valC > valB ? 0 : (valB /valA * (question.getWeightage() / 2)) + (valC / valB * (question.getWeightage() / 2))) ;
			evmQuestionModel.setValue(var);
			return evmQuestionModel;
	}
	catch (Exception e) 
	{
		
		evmQuestionModel.setName(question.getQuestionName());
		evmQuestionModel.setDataNodes(dataNodes);
		evmQuestionModel.setValue(0.0);
		return evmQuestionModel;
	}
	
}
private Double getDouble(String str)
{
	try {
		return Double.parseDouble(str);
	} catch (Exception e) {
		return 0.0;
		
	}
}	

//public static void main(String[] args) {
//	Double valA = 0.0;
//	Double valB = 0.0;
//	
//	Double c = (valA/valB) + 7;
//	if(c.isNaN() || c.isInfinite()) {
//		System.out.println("not a number"+c);
//	}else{
//		System.out.println(c);
//	}
//	
//	String str = 2+"su"+.9;
//	System.out.println(str);
//	
//}


}
			