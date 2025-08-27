package com.example.lp.event.controller;

import com.example.lp.event.dto.Request.EventItemRequest;
import com.example.lp.event.service.EventItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping
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
}
