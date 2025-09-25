package com.example.lp.tosspayment.repository;

import com.example.lp.order.entity.Order;
import com.example.lp.tosspayment.entity.TossPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TossPaymentRepository extends JpaRepository<TossPayment, Long> {
    Optional<TossPayment> findByTossOrderId(String tossOrderId);
    Optional<TossPayment> findByOrder(Order order);
}
