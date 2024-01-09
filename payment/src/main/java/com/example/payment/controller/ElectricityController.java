package com.example.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.payment.model.Bill;
import com.example.payment.model.Bill;
import com.example.payment.service.ElectricityService;

import java.util.List;

@RestController
@RequestMapping("/api/electricity")
public class ElectricityController {

    private final ElectricityService billService;

    @Autowired
    public ElectricityController(ElectricityService billService) {
        this.billService = billService;
    }

    @GetMapping("/bills")
    public List<Bill> getAllBills() {
        return billService.getAllBills();
    }

    @PostMapping("/addBill")
    public ResponseEntity<String> addBill(@RequestBody Bill bill) {
        billService.saveBill(bill);
        return new ResponseEntity<>("Electricity bill created successfully", HttpStatus.CREATED);
    }

}
