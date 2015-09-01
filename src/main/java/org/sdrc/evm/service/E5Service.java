package org.sdrc.evm.service;

import javax.xml.xpath.XPathExpressionException;

import org.sdrc.evm.model.EvmQuestionModel;
import org.sdrc.odkaggregate.domain.EvmQuestion;
import org.w3c.dom.Document;

public interface E5Service {
	
	EvmQuestionModel multiplyWeight(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException;

	EvmQuestionModel e5_2_BooleanException(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException;
}
