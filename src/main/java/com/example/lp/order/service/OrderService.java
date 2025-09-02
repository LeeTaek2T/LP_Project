package com.example.lp.order.service;

import com.example.lp.order.dto.request.OrderRequest;
import com.example.lp.order.entity.Order;
import com.example.lp.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Long createOrder(OrderRequest orderRequest) {
        Order order = new Order(orderRequest.totalAmount(), orderRequest.address(), orderRequest.postCode());
        Order createdOrder = orderRepository.save(order);
        return createdOrder.getId();
    }
}
