package com.example.lp.tosspayment.dto.request;

public record ConfirmRequest(String tossPaymentKey,
                             String tossOrderId,
                             Long amount) {
}
