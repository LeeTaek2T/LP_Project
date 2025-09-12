package com.example.lp.event.dto.Request;

import java.time.LocalDateTime;

public record EventItemRequest(Long productId,
                               Long salePrice,
                               Long quotaPerUser) {}
