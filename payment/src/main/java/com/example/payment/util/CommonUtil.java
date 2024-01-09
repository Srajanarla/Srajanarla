package com.example.payment.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class CommonUtil {
	
	public static Long getTimestamp(String date) {
		
	    long milliseconds = 0;
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
		    Date d = f.parse(date);
		    milliseconds = d.getTime();
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return milliseconds;
	}
	
    public static String getDate() {
	   
    	String date = null;
	    SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
		   date = fm.format(new Date());
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return date;
	}
	
    public static String encodeBase64(String payload) {
    	
    	 byte[] payloadBytes = payload.getBytes(StandardCharsets.UTF_8);
         
         // Encode the payload using Base64
         String encodedPayload = Base64.getEncoder().encodeToString(payloadBytes); 
         
         return encodedPayload;
    }
    
    public static String hashWithSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // Convert the hash bytes to hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception
            e.printStackTrace();
        }
        return null;
    }
    
    public static String generateRandomNo(String mobile) {
    	
    	Calendar cal = Calendar.getInstance();
    	cal.getTimeInMillis();
    	
    	String uniqueNo = mobile+cal.getTimeInMillis();
    	
    	return uniqueNo;
    }
    
    public static String generateRandomNo() {
    	
    	Calendar cal = Calendar.getInstance();
    	cal.getTimeInMillis();
    	
    	String uniqueNo = "SETU"+cal.getTimeInMillis();
    	
    	return uniqueNo;
    }

}

