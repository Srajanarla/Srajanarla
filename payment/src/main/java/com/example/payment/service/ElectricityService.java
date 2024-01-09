package com.example.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.payment.model.Bill;
import com.example.payment.repository.BillRepository;

import java.util.List;

@Service
public class ElectricityService {

    private final BillRepository billRepository;

    @Autowired
    public ElectricityService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public void saveBill(Bill bill) {
        billRepository.save(bill);
    }

}

