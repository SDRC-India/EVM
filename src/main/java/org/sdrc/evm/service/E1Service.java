package org.sdrc.evm.service;

import javax.xml.xpath.XPathExpressionException;

import org.sdrc.odkaggregate.domain.EvmQuestion;
import org.w3c.dom.Document;
import org.sdrc.evm.model.EvmQuestionModel;

public interface E1Service {
	
	EvmQuestionModel calculateE1_03(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException;
	
	EvmQuestionModel calculateE1_05(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException;
	
	EvmQuestionModel calculateE1_06(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException;
	
	EvmQuestionModel calculateE1_19(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException;
}
