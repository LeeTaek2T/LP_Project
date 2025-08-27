package com.example.lp.event.controller;

import com.example.lp.event.dto.Request.EventRequest;
import com.example.lp.event.dto.Request.EventStateRequest;
import com.example.lp.event.dto.Response.EventResponse;
import com.example.lp.event.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class EventController {
    private final EventService eventService;
    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @PostMapping("/event")
    public ResponseEntity<URI> registerEvent(@RequestBody EventRequest eventRequest){
        Long registeredEventId = eventService.registerEvent(eventRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(registeredEventId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/event")
    public ResponseEntity<List<EventResponse>> getAllEvent(){
        List<EventResponse> eventResponseList = eventService.getAllEvent();
        return ResponseEntity.ok(eventResponseList);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<EventResponse> getEvent(@PathVariable Long eventId){
        EventResponse eventResponse = eventService.getEvent(eventId);
        return ResponseEntity.ok(eventResponse);
    }

    @PutMapping("/event/{eventId}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable Long eventId,
                                                     @RequestBody EventStateRequest eventStateRequest){
        EventResponse eventResponse = eventService.updateEvent(eventId, eventStateRequest);
        return ResponseEntity.ok(eventResponse);
    }



}
