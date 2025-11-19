package com.example.lp.product.repository;

import com.example.lp.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p JOIN FETCH p.productSkuList")
    List<Product> findAllWithSku();
}
