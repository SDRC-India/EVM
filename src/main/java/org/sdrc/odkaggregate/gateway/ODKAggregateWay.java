package org.sdrc.odkaggregate.gateway;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import org.sdrc.odkaggregate.domain.XForm;

public interface ODKAggregateWay {

	String enketoAPIPostAnInstanceForEditing(XForm xForm, String formTitle,
			String instance_id,String instance_xml) throws ProtocolException, MalformedURLException ,IOException;

}
