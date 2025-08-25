package com.example.lp.event.mapper;

import com.example.lp.event.dto.Request.EventRequest;
import com.example.lp.event.entity.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public Event requestToEntity(EventRequest eventRequest){
        return new Event(eventRequest.name(), eventRequest.startAt(), eventRequest.endAt(), eventRequest.state());
    }
}
