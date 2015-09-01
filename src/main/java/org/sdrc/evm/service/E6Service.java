package org.sdrc.evm.service;

import javax.xml.xpath.XPathExpressionException;

import org.sdrc.evm.model.EvmQuestionModel;
import org.sdrc.odkaggregate.domain.EvmQuestion;
import org.w3c.dom.Document;

public interface E6Service {
	
	EvmQuestionModel calculateE6_16(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException;
	
	EvmQuestionModel calculateE6_21(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException;
	
	EvmQuestionModel calculateE6_22(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException;
	
	EvmQuestionModel calculateE6_23(Document document, EvmQuestion question, String xPathString) throws XPathExpressionException;

}
