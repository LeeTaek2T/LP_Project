package com.example.lp.cart.dto.response;
import java.util.List;

public record CartResponse (Long productId,
                            Long productSkuId,
                            String name,
                            String color,
                            String size,
                            Long quantity,
                            Long price,
                            Boolean inActive) {
}
