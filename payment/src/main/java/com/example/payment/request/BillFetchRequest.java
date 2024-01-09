package com.example.payment.request;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillFetchRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private BillFetchAgentRequest agent;
	private BillFetchCustomerRequest customer;
	private BillFetchBillerRequest biller;
	
	
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
}
