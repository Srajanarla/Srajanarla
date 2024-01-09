package com.example.payment.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.json.simple.JSONObject;
//import org.json.JSONObject;


public class HttpConnectionUtil {

	public static CloseableHttpResponse PostJsonUsingHttpClient(JSONObject reqdata, String url,
			HashMap<String, String> headersMap, List<NameValuePair> params) throws ClientProtocolException, IOException {

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		
		if (params != null) {
			URI uri;
			try {
				uri = new URIBuilder(httpPost.getURI()).addParameters(params).build();
				httpPost.setURI(uri);
			} catch (URISyntaxException e) {

				e.printStackTrace();
			}

		}
		
		Header[] headers = new Header[headersMap.size()];
		int index = 0;
		for (String key : headersMap.keySet()) {
			String value = headersMap.get(key);
			headers[index++] = new BasicHeader(key, value);
		}

		StringEntity entity = null;
		
		if(reqdata != null) {
			 entity = new StringEntity(reqdata.toString());
			 httpPost.setEntity(entity);
		}
		
		httpPost.setHeaders(headers);
		CloseableHttpResponse response = client.execute(httpPost);

		return response;
	}

	public static CloseableHttpResponse getJsonUsingHttpClient(String url, HashMap<String, String> headersMap,
			List<NameValuePair> params) throws ClientProtocolException, IOException {

		Header[] headers = new Header[headersMap.size()];
		int index = 0;
		for (String key : headersMap.keySet()) {
			String value = headersMap.get(key);
			headers[index++] = new BasicHeader(key, value);
		}

		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);

		if (params != null) {
			URI uri;
			try {
				uri = new URIBuilder(httpGet.getURI()).addParameters(params).build();
				httpGet.setURI(uri);
			} catch (URISyntaxException e) {

				e.printStackTrace();
			}

		}
		httpGet.setHeaders(headers);

		CloseableHttpResponse response = client.execute(httpGet);
//			    HttpEntity httpentity = response.getEntity();
//				String result = EntityUtils.toString(httpentity);
//			    client.close();

		return response;
	}

}
