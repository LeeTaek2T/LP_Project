package com.example.lp.product.repository;

import com.example.lp.product.entity.ProductSku;
import com.example.lp.product.entity.ProductSkuImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSkuImageRepository extends JpaRepository<ProductSkuImage, Long> {
    List<ProductSkuImage> findByProductSku(ProductSku productSku);
}
