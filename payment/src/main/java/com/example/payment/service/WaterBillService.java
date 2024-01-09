package com.example.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.payment.model.WaterBill;
import com.example.payment.repository.WaterBillRepository;

import java.util.List;

@Service
public class WaterBillService {

    private final WaterBillRepository waterBillRepository;

    @Autowired
    public WaterBillService(WaterBillRepository waterBillRepository) {
        this.waterBillRepository = waterBillRepository;
    }

    public List<WaterBill> getAllBills() {
        return waterBillRepository.findAll();
    }

    public void saveBill(WaterBill bill) {
    	waterBillRepository.save(bill);
    }

}
