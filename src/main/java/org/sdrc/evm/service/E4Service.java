package org.sdrc.evm.service;

import javax.xml.xpath.XPathExpressionException;

import org.sdrc.evm.model.EvmQuestionModel;
import org.sdrc.odkaggregate.domain.EvmQuestion;
import org.w3c.dom.Document;

public interface E4Service {
	
	EvmQuestionModel mulWeightDivide2(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException;
	EvmQuestionModel mulWeightDivide(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException;
//	Double calculateE4_27(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException;
	
}
