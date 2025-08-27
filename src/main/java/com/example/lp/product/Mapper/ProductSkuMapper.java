package com.example.lp.product.Mapper;

import com.example.lp.product.dto.request.ProductSkuRequest;
import com.example.lp.product.dto.response.ProductSkuResponse;
import com.example.lp.product.entity.Product;
import com.example.lp.product.entity.ProductSku;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductSkuMapper {

    public ProductSku requestToEntity(ProductSkuRequest productSkuRequest, Product product){
        return new ProductSku(product, productSkuRequest.skuCode(), productSkuRequest.color(), productSkuRequest.size(),
                productSkuRequest.stock(), productSkuRequest.price(), productSkuRequest.inActive(),
                LocalDateTime.now(), LocalDateTime.now());
    }

    public ProductSkuResponse entityToResponse(ProductSku productSku){
        return new ProductSkuResponse(productSku.getId(), productSku.getProduct().getId(),productSku.getProductSkuCode(), productSku.getColor(),
                productSku.getSize(), productSku.getStock(), productSku.getPrice(), productSku.getInActive());
    }
}
