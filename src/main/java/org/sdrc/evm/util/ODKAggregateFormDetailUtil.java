package org.sdrc.evm.util;

import org.sdrc.odkaggregate.gateway.FormXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ODKAggregateFormDetailUtil implements ODKAggregateFormDetail {

	@Autowired
	private FormXML formXML;

	@Override
	public void testXML(String formID) throws Exception {

	}

}
