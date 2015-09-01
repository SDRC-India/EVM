package org.sdrc.odkaggregate.gateway;

import java.io.IOException;
import java.io.StringWriter;

import org.kxml2.io.KXmlSerializer;
import org.opendatakit.briefcase.model.DocumentDescription;
import org.opendatakit.briefcase.model.ServerConnectionInfo;
import org.opendatakit.briefcase.model.TerminationFuture;
import org.opendatakit.briefcase.model.XmlDocumentFetchException;
import org.opendatakit.briefcase.util.AggregateUtils;
import org.sdrc.odkaggregate.domain.XForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.xmlpull.v1.XmlSerializer;


@Component
public class FormXMLImpl implements FormXML{

	@Autowired
	private ResourceBundleMessageSource applicationMessageSource;
	
	@Override
	public StringWriter getXML(XForm xForm) throws XmlDocumentFetchException, IllegalArgumentException, IllegalStateException, IOException {
		AggregateUtils.DocumentFetchResult result = null;
		XmlSerializer serializer = new KXmlSerializer();
		StringWriter base_xml = new StringWriter();
		
		String base_xml_download_url = xForm.getServer_url()+ODKConstants.ORIGINAL_FORM_URL+"?formId="+xForm.getForm_id();
		ServerConnectionInfo serverInfo = new ServerConnectionInfo(xForm.getServer_url(), xForm.getUsername(),xForm.getPassword().toCharArray());
		DocumentDescription submissionDescription = new DocumentDescription(
				"Fetch of manifest failed. Detailed reason: ",
				"Fetch of manifest failed ", "form manifest",
				new TerminationFuture());
		
		// Result for downloading xml template file for xform
		result = AggregateUtils.getXmlDocument(base_xml_download_url,serverInfo, false, submissionDescription, null);
		serializer.setOutput(base_xml);
		result.doc.write(serializer);
//		new FileOutputStream(new File("D:/base_xml.xml")).write(base_xml.toString().getBytes());;
		return base_xml;
	}

}
