package com.example.lp.event.service;

import com.example.lp.event.dto.Request.EventItemRequest;
import com.example.lp.event.dto.Response.EventItemForSellerResponse;
import com.example.lp.event.dto.Response.EventItemResponse;
import com.example.lp.event.entity.Event;
import com.example.lp.event.entity.EventItem;
import com.example.lp.event.repository.EventItemRepository;
import com.example.lp.event.repository.EventRepository;
import com.example.lp.product.dto.response.ProductSkuResponse;
import com.example.lp.product.entity.Product;
import com.example.lp.product.repository.ProductRepository;
import com.example.lp.product.repository.ProductSkuRepository;
import com.example.lp.product.service.ProductService;
import com.example.lp.product.service.ProductSkuService;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventItemService {

    private final EventItemRepository eventItemRepository;
    private final EventRepository eventRepository;
    private final ProductSkuRepository productSkuRepository;
    private final ProductSkuService productSkuService;
    private final ProductRepository productRepository;
    private final ProductService productService;

    public EventItemService(EventItemRepository eventItemRepository,
                            EventRepository eventRepository, ProductSkuRepository productSkuRepository,
                            ProductSkuService productSkuService, ProductRepository productRepository,
                            ProductService productService) {
        this.eventItemRepository = eventItemRepository;
        this.eventRepository = eventRepository;
        this.productSkuRepository = productSkuRepository;
        this.productSkuService = productSkuService;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @Transactional
    public Long registerEventItem(Long eventId, EventItemRequest eventItemRequest){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException());
        Product product = productRepository.findById(eventItemRequest.productId())
                .orElseThrow(() -> new RuntimeException());

        EventItem eventItem = requeustToEntityAndChangeProductSaleState(eventItemRequest, event, product);
        EventItem registeredEventItem = eventItemRepository.save(eventItem);
        return registeredEventItem.getId();
    }

    private EventItem requeustToEntityAndChangeProductSaleState(EventItemRequest eventItemRequest, Event event, Product product){
        String currentState = event.getState();
        if (currentState.equals("진행중")){
            product.changeOnIsSaled();
        }
        return new EventItem(event, eventItemRequest.salePrice(), eventItemRequest.quotaPerUser(), product);

    }

    @Cacheable(
            value = "event:eventItem:list",
            key = "'items_of_event' + #eventId",
            sync = true
    )
    public List<EventItemResponse> getAllEventItemByEventId(Long eventId) {
        List<EventItem> eventItemList = eventItemRepository.findByEventId(eventId);
        List<EventItemResponse> eventItemResponseList = convertToEventItemResponseList(eventItemList);
        return eventItemResponseList;
    }

    private List<EventItemResponse> convertToEventItemResponseList(List<EventItem> eventItemList){
        List<EventItemResponse> eventItemResponseList = new ArrayList<>();
        for (EventItem eventItem : eventItemList) {
            Product product = eventItem.getProduct();
            List<ProductSkuResponse> productSkuResponseListForEventItem =
                    productSkuService.convertToProductSkuResponseListForEventItem(product);
            EventItemResponse eventItemResponse = new EventItemResponse(product.getId(), product.getName(),
                    product.getPrice(), product.getCoverImageUrl(), eventItem.getId(), eventItem.getSalePrice(),
                    productSkuResponseListForEventItem);
            eventItemResponseList.add(eventItemResponse);
        }
        return eventItemResponseList;
    }

    public List<EventItemForSellerResponse> getAllEventItemByEventIdForSeller(Long eventId) {
        List<EventItem> eventItemList = eventItemRepository.findByEventId(eventId);
        List<EventItemForSellerResponse> eventItemForSellerResponseList = eventItemListToEventItemForSellerResponse(eventItemList);
        return eventItemForSellerResponseList;

    }

    private List<EventItemForSellerResponse> eventItemListToEventItemForSellerResponse(List<EventItem> eventItemList){
        List<EventItemForSellerResponse> eventItemForSellerResponseList = new ArrayList<>();
        for (EventItem eventItem : eventItemList) {
            Product product = eventItem.getProduct();
            EventItemForSellerResponse eventItemForSellerResponse = new EventItemForSellerResponse(eventItem.getId(),
                    product.getName(), product.getPrice(), eventItem.getSalePrice());
            eventItemForSellerResponseList.add(eventItemForSellerResponse);
        }
        return eventItemForSellerResponseList;
    }

    @Cacheable(
            value = "eventItem:detail",
            key = "'eventItemId' + #eventItemId",
            sync = true
    )
    public EventItemResponse getEventItemByEventItemId(Long eventItemId) {
        EventItem eventItem = eventItemRepository.findById(eventItemId)
                .orElseThrow(() -> new RuntimeException());
        Product product = eventItem.getProduct();
        List<ProductSkuResponse> productSkuResponseListForIndividualProduct =
                productSkuService.convertToProductSkuResponseListForIndividualProduct(product);

        EventItemResponse eventItemResponse = new EventItemResponse(null, product.getName(),
                product.getPrice(), product.getCoverImageUrl(), eventItemId, eventItem.getSalePrice(),
                productSkuResponseListForIndividualProduct);
        return eventItemResponse;
    }

    public EventItemForSellerResponse getEventItemByEventItemIdForSeller(Long eventItemId) {
        EventItem eventItem = eventItemRepository.findById(eventItemId)
                .orElseThrow(() -> new RuntimeException());
        Product product = eventItem.getProduct();
        EventItemForSellerResponse eventItemForSellerResponse = new EventItemForSellerResponse(
                null, product.getName(), product.getPrice(), null);
        return eventItemForSellerResponse;

    }
}
