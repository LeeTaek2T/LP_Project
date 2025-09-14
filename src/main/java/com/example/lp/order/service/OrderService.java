package com.example.lp.order.service;

import com.example.lp.member.entity.Member;
import com.example.lp.member.repository.MemberRepository;
import com.example.lp.order.dto.request.OrderCancelRequest;
import com.example.lp.order.dto.request.OrderRequest;
import com.example.lp.order.dto.response.OrderCancelPendingResponse;
import com.example.lp.order.dto.response.OrderCancelResponse;
import com.example.lp.order.dto.response.OrderDetailResponse;
import com.example.lp.order.entity.Order;
import com.example.lp.order.entity.OrderDetail;
import com.example.lp.order.repository.OrderDetailRepository;
import com.example.lp.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;
    private final MemberRepository memberRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailService orderDetailService,
                        MemberRepository memberRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailService = orderDetailService;
        this.memberRepository = memberRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Transactional
    public Long createOrder(Authentication auth, OrderRequest orderRequest) {
        Member member = memberRepository.findByEmail(auth.getName())
                .orElseThrow(()-> new RuntimeException());
        Order order = new Order(orderRequest.totalPrice(), orderRequest.dearName() ,orderRequest.phoneNumber(),
                orderRequest.address(), orderRequest.addressDetail(), orderRequest.postcode(), member);
        Order createdOrder = orderRepository.save(order);

        orderDetailService.createOrderDetail(createdOrder, member, orderRequest.orderProductInfoList());
        return createdOrder.getId();
    }

    public List<OrderCancelPendingResponse> getAllOrderCancelPending() {
        List<Order> orderList = orderRepository.findByState("CANCEL_PENDING");
        List<OrderCancelPendingResponse> orderCancelPendingResponseList =
                convertOrderCancelPendingResponseList(orderList);
        return orderCancelPendingResponseList;
    }

    private List<OrderCancelPendingResponse> convertOrderCancelPendingResponseList(List<Order> orderList) {
        List<OrderCancelPendingResponse> orderCancelPendingResponseList = new ArrayList<>();
        for (Order order : orderList) {
            List<OrderDetailResponse> orderDetailResponseList = getOrderDetailByOrderId(order.getId());
            OrderCancelPendingResponse orderCancelPendingResponse = new OrderCancelPendingResponse(order.getId(),
                    orderDetailResponseList, order.getTotalPrice(), order.getCancelReason());
            orderCancelPendingResponseList.add(orderCancelPendingResponse);
        }
        return orderCancelPendingResponseList;
    }

    public List<OrderDetailResponse> getOrderDetailByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("<UNK>"));
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrder(order);

        //        List<OrderDetail> orderDetailList = order.getOrderDetailList();

        List<OrderDetailResponse> orderDetailResponseList = convertToOrderDetailResponseList(orderDetailList);
        return orderDetailResponseList;
    }

    private List<OrderDetailResponse> convertToOrderDetailResponseList(List<OrderDetail> orderDetailList) {
        List<OrderDetailResponse> orderDetailResponseList = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList) {
            OrderDetailResponse orderDetailResponse = new OrderDetailResponse(orderDetail.getId(),
                    orderDetail.getPrice(), orderDetail.getName(), orderDetail.getQuantity(),
                    orderDetail.getSize() ,orderDetail.getColor(),orderDetail.getProductSku().getId());
            orderDetailResponseList.add(orderDetailResponse);
        }
        return orderDetailResponseList;
    }

    @Transactional
    public void cancelOrder(Long orderId, OrderCancelRequest orderCancelRequest) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("<UNK>"));
        order.changeState("CANCEL_PENDING");
    }
}
