package com.example.lp.product.dto.request;

import java.util.List;

public record ProductForRegisterationRequest(
        String name,
        Long price,
        String category,
        String coverImageUrl,
        List<ProductSkuForRegisterationRequest> productSkuForRegisterationRequest) {
}
