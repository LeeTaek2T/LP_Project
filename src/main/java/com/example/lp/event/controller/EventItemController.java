package com.example.lp.event.controller;

import com.example.lp.event.dto.Request.EventItemRequest;
import com.example.lp.event.dto.Response.EventItemForSellerResponse;
import com.example.lp.event.dto.Response.EventItemResponse;
import com.example.lp.event.service.EventItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class EventItemController {
    private final EventItemService eventItemService;

    public EventItemController(EventItemService eventItemService){
        this.eventItemService = eventItemService;
    }

    //특정 이벤트의 모든 이벤트 상품 조회
    @GetMapping("/event/{eventId}/eventItem")
    public ResponseEntity<List<EventItemResponse>> getAllEventItemByEventId(@PathVariable Long eventId) {
        List<EventItemResponse> eventItemResponseList = eventItemService.getAllEventItemByEventId(eventId);
        return ResponseEntity.ok(eventItemResponseList);
    }

    //판매자가 특정 이벤트에 이벤트 아이템 등록
    @PostMapping("/seller/event/{eventId}/eventItem")
    public ResponseEntity<URI> registerEventItem(@PathVariable Long eventId,
                                                 @RequestBody EventItemRequest eventItemRequest){
        Long registeredEventItemId = eventItemService.registerEventItem(eventId, eventItemRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(registeredEventItemId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    //판매자가 특정 이벤트의 이벤트 아이템들 조회
    @GetMapping("/seller/event/{eventId}/eventItem")
    public ResponseEntity<List<EventItemForSellerResponse>> getAllEventItemByEventIdForSeller(@PathVariable Long eventId){
        List<EventItemForSellerResponse> responseList = eventItemService.getAllEventItemByEventIdForSeller(eventId);
        return ResponseEntity.ok(responseList);
    }

    //이벤트 아이템 상세 조회
    @GetMapping("/eventItem/{eventItemId}")
    public ResponseEntity<EventItemResponse> getEventItemByEventItemId(@PathVariable Long eventItemId){
        EventItemResponse eventItemResponse = eventItemService.getEventItemByEventItemId(eventItemId);
        return ResponseEntity.ok(eventItemResponse);
    }

    //판매자가 이벤트 아이템 상세 조회
    @GetMapping("seller/eventItem/{eventItemId}")
    public ResponseEntity<EventItemForSellerResponse> getEventItemByEventItemIdForSeller(@PathVariable Long eventItemId){
        EventItemForSellerResponse eventItemForSellerResponse = eventItemService.getEventItemByEventItemIdForSeller(eventItemId);
        return ResponseEntity.ok(eventItemForSellerResponse);
    }}
