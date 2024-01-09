package com.example.payment.bbps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
//import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.payment.model.Bill;
import com.example.payment.model.Payment;
import com.example.payment.model.User;
import com.example.payment.repository.BillRepository;
import com.example.payment.repository.PaymentRepository;
import com.example.payment.repository.UserRepository;
import com.example.payment.request.BillFetchAgentRequest;
import com.example.payment.request.BillFetchBillerRequest;
import com.example.payment.request.BillFetchCustomerRequest;
import com.example.payment.request.BillFetchRequest;
import com.example.payment.request.BillStatusRequest;
import com.example.payment.request.UserBillFetchRequest;
import com.example.payment.service.TokenService;
import com.example.payment.util.HttpConnectionUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.pockt.pocktservice.request.BillFetchAgentRequest;
//import com.pockt.pocktservice.request.BillFetchBillerRequest;
//import com.pockt.pocktservice.request.BillFetchCustomerRequest;
//import com.pockt.pocktservice.request.BillFetchRequest;
//import com.pockt.pocktservice.request.BillStatusRequest;
//import com.pockt.pocktservice.request.UserBillFetchRequest;
//import com.pockt.pocktservice.util.HttpConnectionUtil;
//import com.pockt.pocktservice.service.TokenService;

@RestController
@RequestMapping("/api/bbps/bill/fetch")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BillFetchApi {
	
	public static final Logger log = LoggerFactory.getLogger(BillFetchApi.class);
	
	@Autowired
	public TokenService tokenService;
	
	@Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BillRepository billRepository;
    
    @Autowired
    private UserRepository userRepository;
	
	
	@Value("${bbps.setu.partnerId}")
	public String partner_id;
	
	@Value("${bbps.setu.bill.fetch.url}")
	public String bill_fetch_url;
	
	@Value("${bbps.setu.bill.fetched.url}")
	public String bill_fetched_url;
	
	@Value("${bbps.setu.agentId}")
	public String agent_id;
	
	@RequestMapping(value = "/getFetchedBill", method = RequestMethod.POST)
	public ResponseEntity<JSONObject>  getFetchedBill(@RequestParam String refId) {
		
		String token = tokenService.getSetuToken();
		if(token == null) {
			log.info("no token found");
			return null;
		}
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Authorization", "Bearer "+token);
		headers.put("X-PARTNER-ID", partner_id);
		
		ObjectMapper om = new ObjectMapper();
		String result;
		JSONObject response = null;
		try {
			BillStatusRequest request = new BillStatusRequest();
			request.setRefId(refId);
           
			String req = om.writeValueAsString(request);
			JSONObject jsonReq = om.readValue(req, JSONObject.class);
			
			System.out.println("jsonReq"+jsonReq);
		
			CloseableHttpResponse httpResponse = HttpConnectionUtil.PostJsonUsingHttpClient(jsonReq,bill_fetched_url, headers, null);
			
			HttpEntity httpentity = httpResponse.getEntity();
			
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				
				try {
					result = EntityUtils.toString(httpentity);
					response = om.readValue(result, JSONObject.class);
					log.info("respone"+response);
					
				} catch (Exception e) {
					
					e.printStackTrace();
				} 
				

			} else {
				log.info("Setu bill fetched eror in response"+httpResponse.getStatusLine().getStatusCode());
				
				result = EntityUtils.toString(httpentity);
				log.info("error result"+result);
				
			}
			
		} catch (Exception e) {
			
			log.error("Error in bill fetch",e);
			return null;
		}
		
		return new ResponseEntity<JSONObject>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getBill", method = RequestMethod.POST)
	public ResponseEntity<JSONObject>  getBill(@RequestBody UserBillFetchRequest userBillReq) {
		
		String token = tokenService.getSetuToken();
		if(token == null) {
			log.info("no token found");
			return null;
		}
		
		User user = new User();
		Bill bill = new Bill();
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Authorization", "Bearer "+token);
		headers.put("X-PARTNER-ID", partner_id);
		
		BillFetchCustomerRequest customer = new BillFetchCustomerRequest();
		customer.setMobile(userBillReq.getMobile());
		System.out.println(userBillReq.getMobile());
		customer.setBillParameters(userBillReq.getBillParameters());
		user.setMobile(userBillReq.getMobile());

		
		BillFetchBillerRequest biller = new BillFetchBillerRequest();
		biller.setId(userBillReq.getBillerId());
		bill.setBillerId(userBillReq.getBillerId());
		
		BillFetchRequest request = new BillFetchRequest();
		request.setAgent(getAgentDetail());
		request.setCustomer(customer);
		request.setBiller(biller);

		ObjectMapper om = new ObjectMapper();
		String result;
		JSONObject response = null;
		try {
			
			String req = om.writeValueAsString(request);
			JSONObject jsonReq = om.readValue(req, JSONObject.class);
			
			System.out.println("jsonReq"+jsonReq);
		
			CloseableHttpResponse httpResponse = HttpConnectionUtil.PostJsonUsingHttpClient(jsonReq,bill_fetch_url, headers, null);
			
			HttpEntity httpentity = httpResponse.getEntity();
			
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				
				try {
					result = EntityUtils.toString(httpentity);
					response = om.readValue(result, JSONObject.class);
					log.info("respone"+response);
					
				} catch (Exception e) {
					
					e.printStackTrace();
				} 
				

			} else {
				log.info("Setu bill fetch eror in response"+httpResponse.getStatusLine().getStatusCode());
				
				result = EntityUtils.toString(httpentity);
				log.info("error result"+result);
				
			}
			
		} catch (Exception e) {
			
			log.error("Error in bill fetch",e);
			return null;
		}
		userRepository.save(user);
//        paymentRepository.save(payment);
        billRepository.save(bill);
		return new ResponseEntity<JSONObject>(response, HttpStatus.OK);
	}
	
	
	public BillFetchAgentRequest getAgentDetail() {
		
		BillFetchAgentRequest agent = new BillFetchAgentRequest();
		agent.setApp("SmartPay");
		agent.setChannel("MOB");
		agent.setId(agent_id);
		agent.setIp("124.170.23.24");
//		agent.setMac("48-4D-7E-CB-DB-6F");
		agent.setImei("123456789012345");
		agent.setOs("iOS");
		
		return agent;
		
	}
	
}

