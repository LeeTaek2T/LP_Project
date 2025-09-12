package com.example.lp.event.dto.Response;

import com.example.lp.product.dto.response.ProductSkuResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record EventItemResponse(Long productId,
                                String name,
                                Long price,
                                String coveImageUrl,
                                Long eventItemId,
                                Long salePrice,
                                List<ProductSkuResponse> productSkuResponseList) {
}
