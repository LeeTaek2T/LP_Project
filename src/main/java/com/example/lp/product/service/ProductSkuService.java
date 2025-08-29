package com.example.lp.product.service;

import com.example.lp.product.Mapper.ProductSkuMapper;
import com.example.lp.product.dto.request.ProductSkuRequest;
import com.example.lp.product.dto.response.ProductSkuResponse;
import com.example.lp.product.entity.Product;
import com.example.lp.product.entity.ProductSku;
import com.example.lp.product.repository.ProductRepository;
import com.example.lp.product.repository.ProductSkuRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductSkuService {

    private final ProductSkuRepository productSkuRepository;
    private final ProductRepository productRepository;
    private final ProductSkuMapper productSkuMapper;

    public ProductSkuService(ProductSkuRepository productSkuRepository, ProductSkuMapper productSkuMapper,
                             ProductRepository productRepository){
        this.productSkuRepository = productSkuRepository;
        this.productRepository = productRepository;
        this.productSkuMapper = productSkuMapper;
    }

    public Long registerProductSku(ProductSkuRequest productSkuRequest, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(""));
        ProductSku productSku = productSkuMapper.requestToEntity(productSkuRequest, product);
        ProductSku registerdProductSku = productSkuRepository.save(productSku);
        return registerdProductSku.getId();
    }

    public List<ProductSkuResponse> getAllProductSku(Long productId){
        List<ProductSku> productSkuList = productSkuRepository.findAllByProductId(productId);
        List<ProductSkuResponse> productSkuResponseList = convertToProductSkuResponseList(productSkuList);
        return productSkuResponseList;
    }

    private List<ProductSkuResponse> convertToProductSkuResponseList(List<ProductSku> productSkuList){
        List<ProductSkuResponse> productSkuResponseList = new ArrayList<>();
        for(ProductSku productSku : productSkuList){
            ProductSkuResponse productSkuResponse = productSkuMapper.entityToResponse(productSku);
            productSkuResponseList.add(productSkuResponse);
        }
        return productSkuResponseList;
    }
}
