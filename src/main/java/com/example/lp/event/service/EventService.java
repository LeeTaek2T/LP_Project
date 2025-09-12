package com.example.lp.event.service;

import com.example.lp.event.dto.Request.EventRequest;
import com.example.lp.event.dto.Request.EventStateRequest;
import com.example.lp.event.dto.Response.EventResponse;
import com.example.lp.event.entity.Event;
import com.example.lp.event.mapper.EventMapper;
import com.example.lp.event.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventService(EventRepository eventRepository, EventMapper eventMapper){
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    public Long registerEvent(EventRequest eventRequest){
        Event event = eventMapper.requestToEntity(eventRequest);
        Event registerdEvent = eventRepository.save(event);
        return registerdEvent.getId();
    }

    public EventResponse getEventByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException());
        EventResponse eventResponse = eventMapper.entityToResponse(event);
        return eventResponse;
    }

    public List<EventResponse> getAllEvent() {
        List<Event> eventList = eventRepository.findAll();
        List<EventResponse> eventResponseList = convertToEventResponseList(eventList);
        return eventResponseList;
    }

    private List<EventResponse> convertToEventResponseList(List<Event> eventList){
        List<EventResponse> eventResponseList = new ArrayList<>();
        for(Event event : eventList){
            EventResponse eventResponse = eventMapper.entityToResponse(event);
            eventResponseList.add(eventResponse);
        }
        return eventResponseList;
    }
}
