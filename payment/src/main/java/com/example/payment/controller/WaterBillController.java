package com.example.payment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.payment.model.Bill;
import com.example.payment.model.WaterBill;
import com.example.payment.service.ElectricityService;
import com.example.payment.service.WaterBillService;

@RestController
@RequestMapping("/api/water")
public class WaterBillController {
	
	private final WaterBillService waterBillService;

    @Autowired
    public WaterBillController(WaterBillService waterBillService) {
        this.waterBillService = waterBillService;
    }

    @PostMapping("/addBill")
    public ResponseEntity<String> createWaterBill(@RequestBody WaterBill waterBill) {
    	waterBillService.saveBill(waterBill);
        return new ResponseEntity<>("Water bill created successfully", HttpStatus.CREATED);
    }
    @GetMapping("/bills")
    public List<WaterBill> getAllBills() {
        return waterBillService.getAllBills();
    }
}
