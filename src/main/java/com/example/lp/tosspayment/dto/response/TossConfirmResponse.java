package com.example.lp.tosspayment.dto.response;

import com.example.lp.order.enums.TossPaymentStatus;

import java.time.OffsetDateTime;

public record TossConfirmResponse(String orderId,
                                  String paymentKey,
                                  Long totalAmount,
                                  String method,
                                  TossPaymentStatus status,
                                  OffsetDateTime approvedAt) {}
