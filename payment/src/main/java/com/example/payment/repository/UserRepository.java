package com.example.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.payment.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // You can add custom query methods here if needed
	
}
