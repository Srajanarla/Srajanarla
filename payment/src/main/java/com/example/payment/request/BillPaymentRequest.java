package com.example.payment.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillPaymentRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BillFetchAgentRequest agent;
	private BillFetchCustomerRequest customer;
	private BillFetchBillerRequest biller;
	private BillPaymentDetailRequest paymentDetails;
	private String refId;
	
	
	public BillFetchAgentRequest getAgent() {
		return agent;
	}
	public void setAgent(BillFetchAgentRequest agent) {
		this.agent = agent;
	}
	public BillFetchCustomerRequest getCustomer() {
		return customer;
	}
	public void setCustomer(BillFetchCustomerRequest customer) {
		this.customer = customer;
	}
	public BillFetchBillerRequest getBiller() {
		return biller;
	}
	public void setBiller(BillFetchBillerRequest biller) {
		this.biller = biller;
	}
	public BillPaymentDetailRequest getPaymentDetails() {
		return paymentDetails;
	}
	public void setPaymentDetails(BillPaymentDetailRequest paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}

