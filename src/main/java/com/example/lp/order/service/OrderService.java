package com.example.lp.order.service;

import com.example.lp.member.entity.Member;
import com.example.lp.member.repository.MemberRepository;
import com.example.lp.order.dto.request.AddressRequest;
import com.example.lp.order.dto.request.OrderCancelRequest;
import com.example.lp.order.dto.request.OrderProductInfo;
import com.example.lp.order.dto.request.OrderRequest;
import com.example.lp.order.dto.response.OrderCancelPendingResponse;
import com.example.lp.order.dto.response.OrderDetailResponse;
import com.example.lp.order.dto.response.OrderResponse;
import com.example.lp.order.entity.Order;
import com.example.lp.order.entity.OrderDetail;
import com.example.lp.order.repository.OrderDetailRepository;
import com.example.lp.order.repository.OrderRepository;
import com.example.lp.product.entity.ProductSku;
import com.example.lp.product.repository.ProductSkuRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;
    private final MemberRepository memberRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final StockService stockService;
    private final ProductSkuRepository productSkuRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailService orderDetailService,
                        MemberRepository memberRepository, OrderDetailRepository orderDetailRepository,
                        StockService stockService, ProductSkuRepository productSkuRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailService = orderDetailService;
        this.memberRepository = memberRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.stockService = stockService;
        this.productSkuRepository = productSkuRepository;
    }


//    @Transactional
//    public Long createOrder(Authentication auth, OrderRequest orderRequest) {
//        // 1. Redis 재고 선점을 먼저 시도합니다.
//        boolean stockDecreased = stockService.decreaseStock(orderRequest);
//
//        // 2. 재고 선점에 실패하면, 예외를 발생시켜 주문 절차를 중단합니다.
//        if (!stockDecreased) {
//            throw new RuntimeException("재고가 부족합니다.");
//        }
//        Member member = memberRepository.findByEmail(auth.getName())
//                .orElseThrow(()-> new RuntimeException("service : 멤버가 없습니다."));
//        Order order = new Order(orderRequest.totalPrice(), member);
//        Order createdOrder = orderRepository.save(order);
//
//        orderDetailService.createOrderDetail(createdOrder, member, orderRequest.orderProductInfoList());
//        return createdOrder.getId();
//    }

    @Transactional
    public synchronized Long createOrder(Authentication auth, OrderRequest orderRequest) {

        for (OrderProductInfo orderProductInfo : orderRequest.orderProductInfoList()) {
            ProductSku productSku = productSkuRepository.findByIdForUpdate(orderProductInfo.productSkuId()).
                    orElseThrow(() -> new RuntimeException());
            if (productSku.getQuantity()-orderProductInfo.quantity()<0){
                throw new RuntimeException("재고가 부족합니다.");
            }
            productSku.reduceAmount(orderProductInfo.quantity());
        }
        Member member = memberRepository.findByEmail(auth.getName())
                .orElseThrow(()-> new RuntimeException("service : 멤버가 없습니다."));
        Order order = new Order(orderRequest.totalPrice(), member);
        Order createdOrder = orderRepository.save(order);

        orderDetailService.createOrderDetail(createdOrder, member, orderRequest.orderProductInfoList());
        return createdOrder.getId();
    }

    public List<OrderCancelPendingResponse> getAllOrderCancelPending() {
        List<Order> orderList = orderRepository.findByState("취소요청");
        List<OrderCancelPendingResponse> orderCancelPendingResponseList =
                convertOrderCancelPendingResponseList(orderList);
        return orderCancelPendingResponseList;
    }

    private List<OrderCancelPendingResponse> convertOrderCancelPendingResponseList(List<Order> orderList) {
        List<OrderCancelPendingResponse> orderCancelPendingResponseList = new ArrayList<>();
        for (Order order : orderList) {
            List<OrderDetailResponse> orderDetailResponseList =
                    getOrderDetailByOrderAndOrderDetailState(order);
            OrderCancelPendingResponse orderCancelPendingResponse = new OrderCancelPendingResponse(order.getId(),
                    orderDetailResponseList, order.getTotalPrice(), order.getCancelReason());
            orderCancelPendingResponseList.add(orderCancelPendingResponse);
        }
        return orderCancelPendingResponseList;
    }

    public List<OrderDetailResponse> getOrderDetailByOrderAndOrderDetailState(Order order) {
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderAndState(order, "취소요청");
        List<OrderDetailResponse> orderDetailResponseList = convertToOrderDetailResponseList(orderDetailList);
        return orderDetailResponseList;
    }

    private List<OrderDetailResponse> convertToOrderDetailResponseList(List<OrderDetail> orderDetailList) {
        List<OrderDetailResponse> orderDetailResponseList = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList) {
            OrderDetailResponse orderDetailResponse = new OrderDetailResponse(orderDetail.getId(),
                    orderDetail.getPrice(), orderDetail.getName(), orderDetail.getQuantity(),
                    orderDetail.getSize() ,orderDetail.getColor());
            orderDetailResponseList.add(orderDetailResponse);
        }
        return orderDetailResponseList;
    }

    @Transactional
    public void cancelOrder(OrderCancelRequest orderCancelRequest) {
        Order order = orderRepository.findById(orderCancelRequest.orderId())
                .orElseThrow(() -> new RuntimeException());
        order.changeState("취소요청");
        for(Long orderDetailId : orderCancelRequest.orderDetailIdList()){
            OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                    .orElseThrow(() -> new RuntimeException("<UNK>"));
            orderDetail.changeState("취소요청");
        }
    }

    @Transactional
    public void confirmOrder(AddressRequest addressRequest, Authentication auth) {
        Order order = orderRepository.findById(addressRequest.orderId())
                .orElseThrow(() -> new RuntimeException());
        order.changeState("결제대기");
        order.setAddress(addressRequest.address());
        order.setAddressDetail(addressRequest.addressDetail());
        order.setDearName(addressRequest.dearName());
        order.setPostCode(addressRequest.postcode());
        order.setPhoneNumber(addressRequest.phoneNumber());
    }

    public List<OrderResponse> getAllOrder(Authentication auth, OffsetDateTime startDate,
                                           OffsetDateTime endDate) {
        Member member = memberRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("이메일에 해당하는 회원이 없습니다."));
        List<Order> orderList = orderRepository.findOrderAndOrderDetailByMemberAndDate(member, startDate, endDate);
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for (Order order : orderList) {
            List<OrderDetailResponse> orderDetailResponseList = new ArrayList<>();
            for (OrderDetail orderDetail : order.getOrderDetailList()) {
                OrderDetailResponse orderDetailReponse = new OrderDetailResponse(orderDetail.getId(),
                        orderDetail.getPrice(), orderDetail.getName(), orderDetail.getQuantity(),
                        orderDetail.getColor(), orderDetail.getSize());
                orderDetailResponseList.add(orderDetailReponse);
            }
            OrderResponse orderResponse = new OrderResponse(order.getId(), member.getId(), order.getTotalPrice(),
                    order.getAddress(), order.getAddressDetail(), order.getPostCode(), order.getDearName(),
                    order.getPhoneNumber(), orderDetailResponseList);
            orderResponseList.add(orderResponse);
        }
        return orderResponseList;
    }
}
