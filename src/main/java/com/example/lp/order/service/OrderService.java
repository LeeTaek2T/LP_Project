package com.example.lp.order.service;

import com.example.lp.member.entity.Member;
import com.example.lp.member.repository.MemberRepository;
import com.example.lp.order.dto.request.OrderRequest;
import com.example.lp.order.entity.Order;
import com.example.lp.order.entity.OrderDetail;
import com.example.lp.order.repository.OrderRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;
    private final MemberRepository memberRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailService orderDetailService,
                        MemberRepository memberRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailService = orderDetailService;
        this.memberRepository = memberRepository;
    }

    public Long createOrder(Authentication auth, OrderRequest orderRequest) {
        Member member = memberRepository.findByEmail(auth.getName())
                .orElseThrow(()-> new RuntimeException("<UNK>"));
        Order order = new Order(member, orderRequest.totalAmount(), orderRequest.address(), orderRequest.postCode());
        Order createdOrder = orderRepository.save(order);
        orderDetailService.createOrderDetail(createdOrder, member, orderRequest.orderProductRequestList());
        return createdOrder.getId();
    }
}
