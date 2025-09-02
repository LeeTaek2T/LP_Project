package com.example.lp.order.dto.request;

public record ConfirmRequest(String paymentKey,
                             String orderId,
                             Long amount) {
}
