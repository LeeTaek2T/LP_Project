package com.example.lp.event.mapper;

import com.example.lp.event.dto.Request.EventRequest;
import com.example.lp.event.dto.Response.EventResponse;
import com.example.lp.event.entity.Event;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Component
public class EventMapper {

    public Event requestToEntity(EventRequest eventRequest){
        OffsetDateTime now   = OffsetDateTime.now(ZoneId.of("Asia/Seoul"));
        OffsetDateTime start = eventRequest.startAt();
        OffsetDateTime end   = eventRequest.endAt();
        String currentState;
        if (now.isBefore(start)) {
            currentState = "진행예정";
        } else if (!now.isAfter(end)) { // start <= now <= end
            currentState = "진행중";
        } else {
            currentState = "진행종료";
        }

        return new Event(eventRequest.name(), eventRequest.startAt(), eventRequest.endAt(), currentState,
                eventRequest.coverImageUrl());
    }

    public EventResponse entityToResponse(Event event){
        return new EventResponse(event.getId(), event.getName(), event.getStartAt(),
                event.getEndAt(), event.getState(), event.getCoverImageUrl());
    }
}
