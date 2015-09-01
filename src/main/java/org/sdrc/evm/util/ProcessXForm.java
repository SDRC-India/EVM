package org.sdrc.evm.util;

import java.util.Map;

import org.sdrc.odkaggregate.domain.XForm;
import org.w3c.dom.Document;

public interface ProcessXForm {

	Map<String, Object> parseXML(Document document,XForm form);
}
