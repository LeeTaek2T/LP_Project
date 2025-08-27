package com.example.lp.event.mapper;

import com.example.lp.event.dto.Request.EventItemRequest;
import com.example.lp.event.dto.Response.EventItemResponse;
import com.example.lp.event.entity.Event;
import com.example.lp.event.entity.EventItem;
import com.example.lp.product.entity.ProductSku;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EventItemMapper {
    public EventItem requestToEntity(EventItemRequest eventItemRequest, Event event, ProductSku productSku){
        return new EventItem(event, eventItemRequest.salePrice(), eventItemRequest.quotaPerUser(),
                eventItemRequest.stock(), LocalDateTime.now(), productSku);
    }

    public EventItemResponse entityToResponse(EventItem eventItem){
        return new EventItemResponse(eventItem.getId(), eventItem.getProductSku().getId(),
                eventItem.getSalePrice(), eventItem.getQuotaPerUser(), eventItem.getStock());
    }
}
