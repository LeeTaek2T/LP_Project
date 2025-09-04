package com.example.lp.tosspayment.dto.request;

public record ConfirmRequest(String paymentKey,
                             String orderId,
                             Long amount) {
}
