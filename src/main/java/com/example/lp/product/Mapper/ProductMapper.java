package com.example.lp.product.Mapper;

import com.example.lp.product.dto.request.ProductRequest;
import com.example.lp.product.dto.response.ProductResponse;
import com.example.lp.product.entity.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductMapper {

    public Product requestToEntity(ProductRequest productRequest){
        return new Product(productRequest.name(), LocalDateTime.now());
    }

    public ProductResponse entityToResponse(Product product){
        return new ProductResponse(product.getId(), product.getName());
    }

}