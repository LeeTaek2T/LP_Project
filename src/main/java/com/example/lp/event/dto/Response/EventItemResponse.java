package com.example.lp.event.dto.Response;

public record EventItemResponse(Long eventItemId,
                                Long productSkuId,
                                Long salePrice,
                                Long quotaPerUser,
                                Long stock) {
}
