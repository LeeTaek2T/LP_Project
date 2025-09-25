package com.example.lp.product.dto.request;

import java.util.List;

public record ProductSkuForRegisterationRequest(
        String size,
        String color,
        Long quantity,
        List<String> productSkuImageUrlList
) {
}
