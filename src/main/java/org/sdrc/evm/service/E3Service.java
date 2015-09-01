package org.sdrc.evm.service;

import javax.xml.xpath.XPathExpressionException;

import org.sdrc.odkaggregate.domain.EvmQuestion;
import org.w3c.dom.Document;
import org.sdrc.evm.model.EvmQuestionModel;

public interface E3Service {
	EvmQuestionModel calculateNetStorage(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException;	
	
	EvmQuestionModel calculateE3_09(Document document,EvmQuestion question,String xPathString) throws XPathExpressionException;
		
}
