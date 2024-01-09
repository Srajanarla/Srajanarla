package com.example.payment.request;

import java.io.Serializable;
import java.util.List;

import org.json.simple.JSONArray;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Embeddable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillFetchCustomerRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mobile;
	private List<BillerParamRequest> billParameters;
	
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public List<BillerParamRequest> getBillParameters() {
		return billParameters;
	}
	public void setBillParameters(List<BillerParamRequest> billParameters) {
		this.billParameters = billParameters;
	}
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	

}

