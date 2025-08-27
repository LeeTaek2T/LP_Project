package com.example.lp.product.service;

import com.example.lp.product.Mapper.ProductMapper;
import com.example.lp.product.dto.request.ProductRequest;
import com.example.lp.product.dto.response.ProductResponse;
import com.example.lp.product.entity.Product;
import com.example.lp.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper){
        this.productRepository=productRepository;
        this.productMapper=productMapper;
    }

    public Long registerProduct(ProductRequest productRequest){
        Product product = productMapper.requestToEntity(productRequest);
        Product savedProduct = productRepository.save(product);
        return savedProduct.getId();
    }

    public List<ProductResponse> getAllProduct() {
        List<Product> productList = productRepository.findAll();
        List<ProductResponse> productRequestList = convertToProductResponseList(productList);
        return productRequestList;
    }

    private List<ProductResponse> convertToProductResponseList(List<Product> productList){
        List<ProductResponse> productResponseList = new ArrayList<>();
        for(Product product : productList){
            ProductResponse productResponse = productMapper.entityToResponse(product);
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }

    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException());
        ProductResponse productResponse = productMapper.entityToResponse(product);
        return productResponse;
    }
}