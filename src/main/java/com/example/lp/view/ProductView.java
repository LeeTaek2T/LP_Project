package com.example.lp.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.example.lp.product.entity.Product;

import java.util.List;

@EntityView(Product.class)
public interface ProductView {
    @IdMapping
    Long getId();

    String getName();

    String getCoverImageUrl();

    Long getPrice();

    List<ProductSkuView> getProductSkuList();
}
