package com.example.lp.product.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record ProductAndSkuGroupResponse(
        Long productId,
        String name,
        Long price,
        String category,
        String coverImageUrl,
        Boolean isSaled,
        Long salePrice,
        List<ProductSkuGroupResponse> productSkuGroupResponseList){
}