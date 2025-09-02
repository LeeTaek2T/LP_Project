package com.example.lp.order.dto.response;

import java.time.LocalDateTime;

public record OrderResponse(
        String paymentKey,
        String orderId,
        Long amount,
        LocalDateTime createdAt
) {}