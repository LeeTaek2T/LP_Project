package com.example.lp.event.controller;

import com.example.lp.event.dto.Request.EventItemRequest;
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

    @PostMapping("/{eventId}/eventItem")
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

    @GetMapping("/{eventId}/eventItem")
    public ResponseEntity<List<EventItemResponse>> getAllEventItem(@PathVariable Long eventId){
        List<EventItemResponse> eventItemResponseList = eventItemService.getAllEventItem(eventId);
        return ResponseEntity.ok(eventItemResponseList);
    }

    @GetMapping("/eventItem/{eventItemId}")
    public ResponseEntity<EventItemResponse> getEventItem(@PathVariable Long eventItemId){
        EventItemResponse eventItemResponse = eventItemService.getEventItem(eventItemId);
        return ResponseEntity.ok(eventItemResponse);
    }
}
