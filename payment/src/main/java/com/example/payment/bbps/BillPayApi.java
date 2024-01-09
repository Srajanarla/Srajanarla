package com.example.payment.bbps;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
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
import com.example.payment.request.BillPaymentDetailRequest;
import com.example.payment.request.BillPaymentRequest;
import com.example.payment.request.BillStatusRequest;
import com.example.payment.request.UserBillPaymentLiteRequest;
import com.example.payment.request.UserBillPaymentRequest;
import com.example.payment.service.TokenService;
import com.example.payment.util.CommonUtil;
import com.example.payment.util.HttpConnectionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/bbps/bill/payment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BillPayApi {
	
	
public static final Logger log = LoggerFactory.getLogger(BillPayApi.class);
	
	@Autowired
	public TokenService tokenService;
	
	@Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BillRepository billRepository;
    
    @Autowired
    private UserRepository userRepository;
	
	@Autowired
	public CommonUtil commonUtil;
	
	@Value("${bbps.setu.partnerId}")
	public String partner_id;
	
	@Value("${bbps.setu.agentId}")
	public String agent_id;
	
	@Value("${bbps.setu.bill.payment.url}")
	public String bill_payment_url;
	
	@Value("${bbps.setu.bill.payment.status.url}")
	public String bill_payment_status_url;
	
	
	@RequestMapping(value = "/direct/makePayment", method = RequestMethod.POST)
	public ResponseEntity<JSONObject>  getBill(@RequestBody UserBillPaymentRequest userBillPayReq) {
		
		String token = tokenService.getSetuToken();
		if(token == null) {
			log.info("no token found");
			return null;
		}
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Authorization", "Bearer "+token);
		headers.put("X-PARTNER-ID", partner_id);
		
		User user = new User();
		Payment payment = new Payment();
        Bill bill = new Bill();
		
		BillFetchCustomerRequest customer = new BillFetchCustomerRequest();
		customer.setMobile(userBillPayReq.getBillerParams().getMobile());
		user.setMobile(userBillPayReq.getBillerParams().getMobile());
		
		customer.setBillParameters(userBillPayReq.getBillerParams().getBillParameters());
		
		BillFetchBillerRequest biller = new BillFetchBillerRequest();
		biller.setId(userBillPayReq.getBillerId());
		bill.setBillerId(userBillPayReq.getBillerId());
		
		BillPaymentDetailRequest paymentDetails = new BillPaymentDetailRequest();
		paymentDetails.setAmount(userBillPayReq.getBillPay().getAmount());
		paymentDetails.setMode(userBillPayReq.getBillPay().getMode());
		paymentDetails.setPaymentParams(userBillPayReq.getBillPay().getPaymentParams());
		paymentDetails.setPaymentRefId(CommonUtil.generateRandomNo(userBillPayReq.getBillerParams().getMobile()));
		paymentDetails.setTimestamp(CommonUtil.getDate());
		
		
		BillPaymentRequest request = new BillPaymentRequest();
		request.setAgent(getAgentDetail());
		request.setCustomer(customer);
		request.setBiller(biller);
		request.setPaymentDetails(paymentDetails);
		
		payment.setAmount(userBillPayReq.getBillPay().getAmount());
//		System.out.println(userBillPayReq.getBillPay().getAmount());
        payment.setMode(userBillPayReq.getBillPay().getMode());
//        System.out.println(userBillPayReq.getBillPay().getMode());
		bill.setBillerId(userBillPayReq.getBillerId());

		ObjectMapper om = new ObjectMapper();
		String result;
		JSONObject response = null;
		try {
			
			String req = om.writeValueAsString(request);
			JSONObject jsonReq = om.readValue(req, JSONObject.class);
			
			System.out.println("jsonReq"+jsonReq);
		
			CloseableHttpResponse httpResponse = HttpConnectionUtil.PostJsonUsingHttpClient(jsonReq,bill_payment_url, headers, null);
			
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
				log.info("Setu bill pay error in response"+httpResponse.getStatusLine().getStatusCode());
				
				result = EntityUtils.toString(httpentity);
				log.info("error result"+result);
				
			}
			
		} catch (Exception e) {
			
			log.error("Error in bill fetch",e);
			return null;
		}
		userRepository.save(user);
        paymentRepository.save(payment);
        billRepository.save(bill);
		return new ResponseEntity<JSONObject>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/fetch/makePayment", method = RequestMethod.POST)
	public ResponseEntity<JSONObject>  makeDirectPayment(@RequestBody UserBillPaymentLiteRequest userBillPayReq) {
		
		String token = tokenService.getSetuToken();
		if(token == null) {
			log.info("no token found");
			return null;
		}
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Authorization", "Bearer "+token);
		headers.put("X-PARTNER-ID", partner_id);
        
		Date date = new Date(1607761920000L); // Replace this with your timestamp in milliseconds

        // Create a SimpleDateFormat object with the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

        // Set the time zone to match "+05:30" (India Standard Time)
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Kolkata");
        dateFormat.setTimeZone(timeZone);

        // Format the Date object to a string
        String formattedTimestamp = dateFormat.format(date);

		
		System.out.println("formattedTimestamp"+formattedTimestamp.toString());
		
		userBillPayReq.getPaymentDetails().setTimestamp(formattedTimestamp);
		

		ObjectMapper om = new ObjectMapper();
		String result;
		JSONObject response = null;
		try {
			
			// Map the fields to your entities
	        Payment payment = new Payment();

	        // Example mappings, replace with actual fields from userBillPayReq
	        payment.setAmount(userBillPayReq.getPaymentDetails().getAmount());
	        payment.setMode(userBillPayReq.getPaymentDetails().getMode());

	        // Set other fields as needed...

	        // Save entities to the database
	        paymentRepository.save(payment);
			
			String req = om.writeValueAsString(userBillPayReq);
			JSONObject jsonReq = om.readValue(req, JSONObject.class);
			
			
			System.out.println("jsonReq"+jsonReq);
		
			CloseableHttpResponse httpResponse = HttpConnectionUtil.PostJsonUsingHttpClient(jsonReq,bill_payment_url, headers, null);
			
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
				log.info("Setu bill pay error in response"+httpResponse.getStatusLine().getStatusCode());
				
				result = EntityUtils.toString(httpentity);
				log.info("error result"+result);
				
			}
			
		} catch (Exception e) {
			
			log.error("Error in bill fetch",e);
			return null;
		}
		return new ResponseEntity<JSONObject>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getPaymentStatus", method = RequestMethod.POST)
	public ResponseEntity<JSONObject>  getPaymentStatus(@RequestParam String refId) {
		
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
		
			CloseableHttpResponse httpResponse = HttpConnectionUtil.PostJsonUsingHttpClient(jsonReq,bill_payment_status_url, headers, null);
			
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
				log.info("Setu bill payment status error in response"+httpResponse.getStatusLine().getStatusCode());
				
				result = EntityUtils.toString(httpentity);
				log.info("error result"+result);
				
			}
			
		} catch (Exception e) {
			
			log.error("Error in bill fetch",e);
			return null;
		}
		
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

