package com.example.lp.event.dto.Response;

public record EventItemCacheResponse(
        Long id,
        Long eventItemId,
        Long eventId,
        Long productId,
        String productName,
        Long productPrice,
        String productCoverImageUrl,
        Long salePrice,
        Long productSkuId,
        String size,
        String color,
        Long quantity
) {}
