package com.example.lp.order.dto.request;

import java.util.List;

public record OrderRequest(
        Long totalPrice,
        List<OrderProductInfo> orderProductInfoList) {}
