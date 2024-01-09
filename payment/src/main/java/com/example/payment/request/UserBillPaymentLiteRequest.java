package com.example.payment.request;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown =true)
public class UserBillPaymentLiteRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BillPaymentDetailRequest paymentDetails;
	private String refId;
	
	
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

