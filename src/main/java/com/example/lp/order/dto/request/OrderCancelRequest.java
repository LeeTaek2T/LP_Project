package com.example.lp.order.dto.request;

import java.util.List;

public record OrderCancelRequest(String cancelReason,
                                 Long orderId,
                                 List<Long> orderDetailIdList) {
}
