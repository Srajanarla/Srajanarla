package com.example.payment.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Bill {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	private String category;
	private String billerName;
    private Double amount;
    private String billerId;
    
    public Bill() {
		super();
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getBillerId() {
		return billerId;
	}
	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBillerName() {
		return billerName;
	}
	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}
}

//@Entity
//@Table(name = "electricity_bills")
//public class ElectricityBill extends Bill {
//
//    @Column(name = "meter_number")
//    private String meterNumber;
//
//
//    public String getMeterNumber() {
//        return meterNumber;
//    }
//
//    public void setMeterNumber(String meterNumber) {
//        this.meterNumber = meterNumber;
//    }
//
//   
//}


