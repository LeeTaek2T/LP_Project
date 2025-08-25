package com.example.lp.event.service;

import com.example.lp.event.dto.Request.EventRequest;
import com.example.lp.event.entity.Event;
import com.example.lp.event.mapper.EventMapper;
import com.example.lp.event.repository.EventRepository;
import org.springframework.stereotype.Service;

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
}
