package com.example.lp.tosspayment.dto.response;

import java.time.OffsetDateTime;

public record ConfirmResponse(String tossOrderId,
                              String tossPaymentKey,
                              Long amount,
                              String status,
                              OffsetDateTime approvedAt) {
}
