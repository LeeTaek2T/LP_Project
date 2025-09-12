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

    @GetMapping("/event/{eventId}/eventItem")
    public ResponseEntity<List<EventItemResponse>> getAllEventItemByEventId(@PathVariable Long eventId){
        List<EventItemResponse> eventItemResponseList = eventItemService.getAllEventItemByEventId(eventId);
        return ResponseEntity.ok(eventItemResponseList);
    }

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

    @GetMapping("/seller/event/{eventId}/eventItem")
    public ResponseEntity<List<EventItemForSellerResponse>> getAllEventItemByEventIdForSeller(@PathVariable Long eventId){
        List<EventItemForSellerResponse> responseList = eventItemService.getAllEventItemByEventIdForSeller(eventId);
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/eventItem/{eventItemId}")
    public ResponseEntity<EventItemResponse> getEventItemByEventItemId(@PathVariable Long eventItemId){
        EventItemResponse eventItemResponse = eventItemService.getEventItemByEventItemId(eventItemId);
        return ResponseEntity.ok(eventItemResponse);
    }

    @GetMapping("seller/eventItem/{eventItemId}")
    public ResponseEntity<EventItemForSellerResponse> getEventItemByEventItemIdForSeller(@PathVariable Long eventItemId){
        EventItemForSellerResponse eventItemForSellerResponse = eventItemService.getEventItemByEventItemIdForSeller(eventItemId);
        return ResponseEntity.ok(eventItemForSellerResponse);
    }}
