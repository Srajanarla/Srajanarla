package com.example.payment.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Embeddable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillPaymentDetailRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int amount;
	private String mode;
	private List<BillerParamRequest> paymentParams;
	private String paymentRefId;
	private String timestamp;
	
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public List<BillerParamRequest> getPaymentParams() {
		return paymentParams;
	}
	public void setPaymentParams(List<BillerParamRequest> paymentParams) {
		this.paymentParams = paymentParams;
	}
	public String getPaymentRefId() {
		return paymentRefId;
	}
	public void setPaymentRefId(String paymentRefId) {
		this.paymentRefId = paymentRefId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

	

}

