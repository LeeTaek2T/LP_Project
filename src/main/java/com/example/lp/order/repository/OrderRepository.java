package com.example.lp.order.repository;

import com.example.lp.order.entity.Order;
import com.example.lp.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByState(String state);

}
