package com.example.lp.tosspayment.dto.response;

import java.time.OffsetDateTime;

public record TossConfirmResponse(String orderId,
                                  String paymentKey,
                                  Long totalAmount,
                                  String method,
                                  String status,
                                  OffsetDateTime approvedAt) {}
