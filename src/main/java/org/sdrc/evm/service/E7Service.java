package org.sdrc.evm.service;

import javax.xml.xpath.XPathExpressionException;

import org.sdrc.evm.model.EvmQuestionModel;
import org.sdrc.odkaggregate.domain.EvmQuestion;
import org.w3c.dom.Document;

public interface E7Service {

	EvmQuestionModel calculateE7_2(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException;
	
	EvmQuestionModel calculateE7_5(Document document, EvmQuestion question,String xPathString) throws XPathExpressionException;
	
	EvmQuestionModel calculateRange0To4(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException;
	
	EvmQuestionModel calculateE7_14(Document document, EvmQuestion question,String xPathString) throws XPathExpressionException;
}
