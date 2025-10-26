package com.example.lp.product.repository;

import com.example.lp.product.entity.Product;
import com.example.lp.product.entity.ProductSku;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

@Repository
public interface ProductSkuRepository extends JpaRepository<ProductSku, Long> {
    List<ProductSku> findAllByProductId(Long productId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ProductSku p WHERE p.id = :id")
    Optional<ProductSku> findByIdForUpdate(Long id);
    Optional<ProductSku> findByProductAndId(Product product, Long productSkuId);
}
