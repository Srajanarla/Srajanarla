package com.example.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.payment.model.WaterBill;

public interface WaterBillRepository extends JpaRepository<WaterBill, Long> {
    // You can add custom query methods here if needed
}

