package com.example.lp.tosspayment.dto.request;

public record TossConfirmRequest(String paymentKey,
                                 String orderId,
                                 Long amount) {
}
