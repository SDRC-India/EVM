package org.sdrc.odkaggregate.gateway;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.sdrc.odkaggregate.domain.XForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class ODKAggregateWayImpl implements ODKAggregateWay {

	@Autowired
	private ResourceBundleMessageSource applicationMessageSource;
	
	@Override
	public String enketoAPIPostAnInstanceForEditing(XForm xForm,
			String formTitle, String instance_id,String instance_xml) throws ProtocolException, MalformedURLException ,IOException{
		
		String urlParameters = "server_url="+xForm.getServer_url()
							+"&form_id="+xForm.getForm_id()
							+"&instance="+instance_xml
							+"&instance_id="+instance_id
							+"&return_url="+ODKConstants.ENKETO_RETURN_URL;
		
		URL url_Obj = new URL(ODKConstants.ENKETO_API_V1_INSTANCE_URL);
		HttpURLConnection httpURLConnection   = (HttpURLConnection) url_Obj.openConnection();
		
		String token_id = xForm.getEnketoApiToken();
		byte[] encoded = Base64.encodeBase64((token_id + ":").getBytes());

		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Authorization", "Basic "+ new String(encoded));
		httpURLConnection.setRequestProperty("User-Agent", ODKConstants.USER_AGENT);
		httpURLConnection.setRequestProperty("Accept-Language", ODKConstants.ACCEPT_LANGUAGE_EN_US);
		httpURLConnection.setRequestProperty("Content-Type", ODKConstants.CONTENT_TYPE_APPLICATION);
		httpURLConnection.setDoOutput(true);
		
		DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = httpURLConnection.getResponseCode();
		System.out.println("Response : "+responseCode);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
		
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		String message = null;
		while ((inputLine = in.readLine()) != null) {
			if (inputLine.contains("edit_url"))
			{
				message = inputLine;
				System.out.println(message);
			}
			response.append(inputLine);
		}
		System.out.println(message);
		if(message.split("\"").length>=3 && message.split("\"")[3]!=null){
//			System.out.println( message.split("\"")[3].replaceAll("\\\\", ""));
			return message.split("\"")[3].replaceAll("\\\\", "");}
		else{
			System.out.println("return null");
			return ""+responseCode;}
		
		
	}

}
