package org.sdrc.odkaggregate.gateway;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;


@Component
public class AggregateFormGetwayImpl implements AggregateFormGetway{

	private final String USER_AGENT = "Mozilla/5.0";
	@Override
	public String getAllForms() throws Exception{
		String url = "https://enketo.org/api_v1/surveys/list?server_url=http://203.129.205.37:8077/RIPAS";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");
		
		String decodestring = "ojfknse1icin3ik9:";
		byte[] encoded = Base64.encodeBase64(decodestring.getBytes());
//		System.out.println("Base64 Encoded String : " + new String(encoded));

		con.setRequestProperty("Authorization", "Basic "+new String(encoded));
		con.setRequestProperty("User-Agent", USER_AGENT);
		 
		int responseCode = con.getResponseCode();
//		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
 
		
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
//		System.out.println(in.toString());
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
//			if(inputLine.contains("https")){
//				responseURL = inputLine;
//			}
			response.append(inputLine);
		}
		in.close();
//		System.out.println(response.toString());

		return response.toString();
	}

}
