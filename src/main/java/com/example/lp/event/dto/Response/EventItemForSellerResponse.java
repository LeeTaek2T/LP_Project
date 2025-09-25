package com.example.lp.event.dto.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record EventItemForSellerResponse(Long eventItemId,
                                         String productName,
                                         Long price,
                                         Long salePrice) {
}
