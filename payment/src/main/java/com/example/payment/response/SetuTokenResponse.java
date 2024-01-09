package com.example.payment.response;

import java.io.Serializable;

public class SetuTokenResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int expiresIn;
	private String token;
	private String traceId;
	private Boolean success;
	
	
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTraceId() {
		return traceId;
	}
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	@Override
	public String toString() {
		return "SetuTokenResponse [expiresIn=" + expiresIn + ", token=" + token + ", traceId=" + traceId + ", success="
				+ success + "]";
	}
	
	
	

}
