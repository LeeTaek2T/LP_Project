package com.example.lp.product.repository;

import com.example.lp.product.entity.Product;
import com.example.lp.product.entity.ProductSku;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

@Repository
public interface ProductSkuRepository extends JpaRepository<ProductSku, Long> {
    List<ProductSku> findAllByProductId(Long productId);

    Optional<ProductSku> findByProductAndId(Product product, Long productSkuId);
}
