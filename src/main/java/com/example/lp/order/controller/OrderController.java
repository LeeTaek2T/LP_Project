package com.example.lp.order.controller;

import com.example.lp.order.dto.request.OrderRequest;
import com.example.lp.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
}
