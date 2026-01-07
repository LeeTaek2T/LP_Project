package com.example.lp.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.example.lp.event.entity.EventItem;


@EntityView(EventItem.class)
public interface EventItemView {
    @IdMapping
    Long getId();
    Long getSalePrice();
    ProductView getProduct();
}
