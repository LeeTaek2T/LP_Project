package com.example.lp.order.dto.request;

public record OrderProductInfo(String name,
                               Long price,
                               Long quantity,
                               String size,
                               String color,
                               Long productSkuId,
                               String coverImageUrl) {
}
