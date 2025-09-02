package com.example.lp.order.repository;

import com.example.lp.order.entity.TossPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TossPaymentRepository extends JpaRepository<TossPayment, Long> {
}
