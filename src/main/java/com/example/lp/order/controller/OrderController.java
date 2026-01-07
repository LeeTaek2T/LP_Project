package com.example.lp.order.controller;

import com.example.lp.order.dto.request.AddressRequest;
import com.example.lp.order.dto.request.OrderCancelRequest;
import com.example.lp.order.dto.request.OrderRequest;
import com.example.lp.order.dto.response.OrderCancelPendingResponse;
import com.example.lp.order.dto.response.OrderResponse;
import com.example.lp.order.service.OrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //배송지 정보없이 어떤 상품을 구매하는지 정보가 들어와서 order와 orderDetail생성
    @PostMapping("/orderPre")
    public ResponseEntity<URI> createOrder(Authentication auth, @RequestBody OrderRequest orderRequest) {
        Long createdOrderId = orderService.createOrder(auth, orderRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdOrderId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    // 배송지 정보까지 입력되며 주문까지 완료(결제는 아직)
    @PutMapping("/order/confirm")
    public ResponseEntity<Void> confirmOrder(Authentication auth, @RequestBody AddressRequest addressRequest) {
        orderService.confirmOrder(addressRequest,auth);
        return ResponseEntity.noContent().build();
    }


//    @GetMapping("/order/{orderId}/orderDetail")
//    public ResponseEntity<List<OrderDetailResponse>> getOrderDetailByOrderId(Authentication auth,
//                                                                             @PathVariable Long orderId) {
//        List<OrderDetailResponse> orderDetailResponseList = orderService.getOrderDetailByOrderId(orderId);
//        return ResponseEntity.ok(orderDetailResponseList);
//    }

    //구매자가 판매자에게 환불요청
    @PostMapping("/order/orderDetail/cancel")
    public ResponseEntity<Void> cancelOrder(Authentication auth, @RequestBody OrderCancelRequest orderCancelRequest) {
        orderService.cancelOrder(orderCancelRequest);
        return ResponseEntity.ok().build();
    }

    //판매자에게 구매자들의 환불요청들을 보여주는 API
    @GetMapping("/seller/order/cancelPending")
    public ResponseEntity<List<OrderCancelPendingResponse>> getAllOrderCancelPending(Authentication auth) {
        List<OrderCancelPendingResponse> orderCancelPendingResponseList = orderService.getAllOrderCancelPending();
        return ResponseEntity.ok(orderCancelPendingResponseList);
    }

    @GetMapping("/order")
    public ResponseEntity<List<OrderResponse>> getAllOrder(Authentication auth,
                                                           @RequestParam(required = false)
                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                           OffsetDateTime startDate,
                                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                           OffsetDateTime endDate) {
        List<OrderResponse> orderResponseList = orderService.getAllOrder(auth, startDate, endDate);
        return ResponseEntity.ok(orderResponseList);
    }
}
