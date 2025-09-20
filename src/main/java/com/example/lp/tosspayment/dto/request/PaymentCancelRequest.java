package com.example.lp.tosspayment.dto.request;

import java.util.List;

public record PaymentCancelRequest(Long orderId,
                                   String cancelReason,
                                   List<Long> orderDetailId) {
}
