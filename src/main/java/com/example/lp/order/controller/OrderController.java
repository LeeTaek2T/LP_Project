package com.example.lp.order.controller;

import com.example.lp.order.dto.request.OrderCancelRequest;
import com.example.lp.order.dto.request.OrderRequest;
import com.example.lp.order.dto.response.OrderCancelPendingResponse;
import com.example.lp.order.dto.response.OrderCancelResponse;
import com.example.lp.order.dto.response.OrderDetailResponse;
import com.example.lp.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<URI> createOrder(Authentication auth, @RequestBody OrderRequest orderRequest) {
        Long createdOrderId = orderService.createOrder(auth, orderRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdOrderId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

//    @GetMapping("/order/{orderId}/orderDetail")
//    public ResponseEntity<List<OrderDetailResponse>> getOrderDetailByOrderId(Authentication auth,
//                                                                             @PathVariable Long orderId) {
//        List<OrderDetailResponse> orderDetailResponseList = orderService.getOrderDetailByOrderId(orderId);
//        return ResponseEntity.ok(orderDetailResponseList);
//    }

    //구매자가 판매자에게 환불요청
    @PostMapping("/order/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(Authentication auth, @PathVariable Long orderId,
                                                     @RequestBody OrderCancelRequest orderCancelRequest) {
        orderService.cancelOrder(orderId, orderCancelRequest);
        return ResponseEntity.ok().build();
    }

    //판매자에게 구매자들의 환불요청들을 보여주는 API
    //이건 auth가 판매자인지 확인을 해야함
    @GetMapping("/order/cancelPending")
    public ResponseEntity<List<OrderCancelPendingResponse>> getAllOrderCancelPending(Authentication auth) {
        List<OrderCancelPendingResponse> orderCancelPendingResponseList = orderService.getAllOrderCancelPending();
        return ResponseEntity.ok(orderCancelPendingResponseList);
    }
}
