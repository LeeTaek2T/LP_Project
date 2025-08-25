package com.example.lp.event.controller;

import com.example.lp.event.dto.Request.EventRequest;
import com.example.lp.event.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
        Long registerdEventId = eventService.registerEvent(eventRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(registerdEventId)
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
