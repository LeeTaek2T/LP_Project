package com.example.lp.order.dto.response;

import java.time.OffsetDateTime;

public record OrderCancelResponse(
        Long orderId,
        Long totalAmount,
        String address,
        String postCode,
        OffsetDateTime createdAt,
        String state,
        String cancelReason) {}
