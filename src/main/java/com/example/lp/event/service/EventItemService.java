package com.example.lp.event.service;

import com.example.lp.event.dto.Request.EventItemRequest;
import com.example.lp.event.dto.Response.EventItemResponse;
import com.example.lp.event.dto.Response.EventResponse;
import com.example.lp.event.entity.Event;
import com.example.lp.event.entity.EventItem;
import com.example.lp.event.mapper.EventItemMapper;
import com.example.lp.event.mapper.EventMapper;
import com.example.lp.event.repository.EventItemRepository;
import com.example.lp.event.repository.EventRepository;
import com.example.lp.product.entity.ProductSku;
import com.example.lp.product.repository.ProductSkuRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public Long registerEventItem(Long eventId, EventItemRequest eventItemRequest){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException());
        ProductSku productSku = productSkuRepository.findById(eventItemRequest.productSkuId())
                .orElseThrow(() -> new RuntimeException());
        EventItem eventItem = eventItemMapper.requestToEntity(eventItemRequest, event, productSku);
        EventItem registeredEventItem = eventItemRepository.save(eventItem);
        return registeredEventItem.getId();
    }

    public List<EventItemResponse> getAllEventItem(Long eventId) {
        List<EventItem> eventItemList = eventItemRepository.findByEventId(eventId);
        List<EventItemResponse> eventItemResponseList = convertToEventItemResponseList(eventItemList);
        return eventItemResponseList;
    }

    public EventItemResponse getEventItem(Long eventItemId) {
        EventItem eventItem = eventItemRepository.findById(eventItemId)
                .orElseThrow(() -> new RuntimeException());
        EventItemResponse eventItemResponse = eventItemMapper.entityToResponse(eventItem);
        return eventItemResponse;
    }

    private List<EventItemResponse> convertToEventItemResponseList(List<EventItem> eventItemList){
        List<EventItemResponse> eventItemResponseList = new ArrayList<>();
        for (EventItem eventItem : eventItemList) {
            EventItemResponse eventItemResponse = new EventItemResponse(eventItem.getId(),
                    eventItem.getProductSku().getId(), eventItem.getSalePrice(),
                    eventItem.getQuotaPerUser(), eventItem.getStock());
            eventItemResponseList.add(eventItemResponse);
        }
        return eventItemResponseList;
    }
}
