package com.example.lp.cart.dto.response;
import java.util.List;

public record CartResponse (String name,
                            Long price,
                            Long quantity,
                            String coverImageUrl,
                            Long productSkuId,
                            String size,
                            String color) {
}
