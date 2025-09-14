package com.example.lp.product.service;

import com.example.lp.product.entity.ProductSku;
import com.example.lp.product.entity.ProductSkuImage;
import com.example.lp.product.repository.ProductSkuImageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductSkuImageService {
    private final ProductSkuImageRepository productSkuImageRepository;

    public ProductSkuImageService(ProductSkuImageRepository productSkuImageRepository) {
        this.productSkuImageRepository = productSkuImageRepository;
    }

    public void registerProductSkuImages(String productSkuImageUrl, ProductSku productSku) {
        ProductSkuImage productSkuImage = new ProductSkuImage(productSkuImageUrl, productSku);
        productSkuImageRepository.save(productSkuImage);
    }

    public List<String> getAllProductSkuImageUrl(ProductSku productSku) {
        List<String> productSkuImageUrlList = new ArrayList<>();
        List<ProductSkuImage> productSkuImageList = productSkuImageRepository.findByProductSku(productSku);
        for (ProductSkuImage productSkuImage : productSkuImageList) {
            String productSkuImageUrl = productSkuImage.getProductSkuImageUrl();
            productSkuImageUrlList.add(productSkuImageUrl);
        }
        return productSkuImageUrlList;
    }
}
