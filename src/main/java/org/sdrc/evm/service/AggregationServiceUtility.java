package org.sdrc.evm.service;

import java.util.Map;

import org.sdrc.odkaggregate.domain.XForm;
import org.w3c.dom.Document;

public interface AggregationServiceUtility {

	void ProcessXform(XForm form);
	Map<String, Object> parseXml(Document document,XForm xForm);

}			
