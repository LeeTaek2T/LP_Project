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

    //이벤트 등록
    @PostMapping("/seller/event")
    public ResponseEntity<URI> registerEvent(@RequestBody EventRequest eventRequest){
        Long registeredEventId = eventService.registerEvent(eventRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(registeredEventId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    //모든 이벤트 조회
    @GetMapping("/event")
    public ResponseEntity<List<EventResponse>> getAllEvent(){
        List<EventResponse> eventResponseList = eventService.getAllEvent();
        return ResponseEntity.ok(eventResponseList);
    }

    //판매자를 위한 모든 이벤트 조회
    @GetMapping("/seller/event")
    public ResponseEntity<List<EventResponse>> getAllEventForSeller(){
        List<EventResponse> eventResponseList = eventService.getAllEvent();
        return ResponseEntity.ok(eventResponseList);
    }

    //특정이벤트 가져오기
    @GetMapping("/seller/event/{eventId}")
    public ResponseEntity<EventResponse> getEventByEventId(@PathVariable Long eventId){
        EventResponse eventResponse = eventService.getEventByEventId(eventId);
        return ResponseEntity.ok(eventResponse);
    }
}
