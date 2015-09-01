package org.sdrc.evm.service;

import javax.xml.xpath.XPathExpressionException;

import org.sdrc.evm.model.EvmQuestionModel;
import org.sdrc.odkaggregate.domain.EvmQuestion;
import org.w3c.dom.Document;

public interface E9Service {

	EvmQuestionModel calculateRange0To4(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException;
	
	EvmQuestionModel calculateValDivideBy4(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException;
	
	EvmQuestionModel calculateE9_24(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException;
	
	EvmQuestionModel calculateE9_20(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException;
}
