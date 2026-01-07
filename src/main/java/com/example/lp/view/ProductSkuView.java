package com.example.lp.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.example.lp.product.entity.ProductSku;

@EntityView(ProductSku.class)
public interface ProductSkuView {
    @IdMapping
    Long getId();
    String getSize();
    String getColor();
    Long getQuantity();
}
