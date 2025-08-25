package com.example.lp.event.service;

import com.example.lp.event.dto.Request.EventItemRequest;
import com.example.lp.event.entity.Event;
import com.example.lp.event.entity.EventItem;
import com.example.lp.event.mapper.EventItemMapper;
import com.example.lp.event.mapper.EventMapper;
import com.example.lp.event.repository.EventItemRepository;
import com.example.lp.event.repository.EventRepository;
import com.example.lp.product.entity.ProductSku;
import com.example.lp.product.repository.ProductSkuRepository;
import org.springframework.stereotype.Service;

@Service
public class EventItemService {

    private final EventItemRepository eventItemRepository;
    private final EventRepository eventRepository;
    private final EventItemMapper eventItemMapper;
    private final ProductSkuRepository productSkuRepository;

    public EventItemService(EventItemRepository eventItemRepository, EventItemMapper eventItemMapper,
                            EventRepository eventRepository, ProductSkuRepository productSkuRepository){
        this.eventItemRepository = eventItemRepository;
        this.eventItemMapper = eventItemMapper;
        this.eventRepository = eventRepository;
        this.productSkuRepository = productSkuRepository;
    }

    public Long registerEventItem(EventItemRequest eventItemRequest){
        Event event = eventRepository.findById(eventItemRequest.eventId())
                .orElseThrow(() -> new RuntimeException());
        ProductSku productSku = productSkuRepository.findById(eventItemRequest.skuId())
                .orElseThrow(() -> new RuntimeException());
        EventItem eventItem = eventItemMapper.requestToEntity(eventItemRequest, event, productSku);
        EventItem registeredEventItem = eventItemRepository.save(eventItem);
        return registeredEventItem.getId();
    }
}
