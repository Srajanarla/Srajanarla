package com.example.payment.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Embeddable;

@JsonIgnoreProperties(ignoreUnknown =true)
public class UserBillPaymentRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BillFetchCustomerRequest billerParams;
	private BillPaymentDetailRequest billPay;
	private String billerId;
	private String refId;
	
	
	public BillFetchCustomerRequest getBillerParams() {
		return billerParams;
	}
	public void setBillerParams(BillFetchCustomerRequest billerParams) {
		this.billerParams = billerParams;
	}
	public BillPaymentDetailRequest getBillPay() {
		return billPay;
	}
	public void setBillPay(BillPaymentDetailRequest billPay) {
		this.billPay = billPay;
	}
	public String getBillerId() {
		return billerId;
	}
	public void setBillerId(String billerId) {
		this.billerId = billerId;
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
