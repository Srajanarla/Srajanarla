package com.example.payment.service;

import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
//import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.payment.response.SetuTokenResponse;
import com.example.payment.util.HttpConnectionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TokenService {

	public static final Logger log = LoggerFactory.getLogger(TokenService.class);
	
	@Value("${bbps.setu.authenticate.token.url}")
	String setu_token_url;

	@Value("${bbps.setu.client.secretId}")
	String client_secret_id;

	@Value("${bbps.setu.clientId}")
	String client_id;

	@SuppressWarnings("unchecked")
	public String getSetuToken() {
		
		log.info(client_id);
		log.info(client_secret_id);
		
		String client_id = "Pockt";
	    String client_secret_id = "2f05a0ed-80ac-498a-9773-dc2295d8829c";

		SetuTokenResponse response = null;

		try {

			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");

			JSONObject req = new JSONObject();
			req.put("clientID", client_id);
			req.put("secret", client_secret_id);

			CloseableHttpResponse httpResponse = HttpConnectionUtil.PostJsonUsingHttpClient(req, setu_token_url,
					headers, null);
			if(httpResponse.getStatusLine().getStatusCode() == 200) {
				
				String result;
				ObjectMapper om = new ObjectMapper();
				HttpEntity httpentity = httpResponse.getEntity();
				result = EntityUtils.toString(httpentity);
				log.info("result" + result);
				response = om.readValue(result, SetuTokenResponse.class);
				
			} else {
				log.info("Token bad response");
				return null;
			}

			

		} catch (Exception e) {

			log.error("Error while generating setu token" + e);
			return null;

		}

		return response.getToken();

	}

}
