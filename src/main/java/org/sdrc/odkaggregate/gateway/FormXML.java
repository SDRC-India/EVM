package org.sdrc.odkaggregate.gateway;

import java.io.IOException;
import java.io.StringWriter;

import org.opendatakit.briefcase.model.XmlDocumentFetchException;
import org.sdrc.odkaggregate.domain.XForm;

public interface FormXML {

	StringWriter getXML(XForm xForm) throws XmlDocumentFetchException, IllegalArgumentException, IllegalStateException, IOException;

}
