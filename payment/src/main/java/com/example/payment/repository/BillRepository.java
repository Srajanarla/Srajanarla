package com.example.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.payment.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {
    // You can add custom query methods here if needed
	
}

