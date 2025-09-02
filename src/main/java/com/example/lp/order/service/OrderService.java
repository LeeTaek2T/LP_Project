package com.example.lp.order.service;

import com.example.lp.order.dto.request.OrderRequest;
import com.example.lp.order.entity.Order;
import com.example.lp.order.entity.OrderDetail;
import com.example.lp.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;

    public OrderService(OrderRepository orderRepository, OrderDetailService orderDetailService) {
        this.orderRepository = orderRepository;
        this.orderDetailService = orderDetailService;
    }

    public Long createOrder(OrderRequest orderRequest) {
        Order order = new Order(orderRequest.totalAmount(), orderRequest.address(), orderRequest.postCode());
        Order createdOrder = orderRepository.save(order);
        orderDetailService.createOrderDetail(createdOrder, orderRequest.orderProductRequestList());
        return createdOrder.getId();
    }
}
