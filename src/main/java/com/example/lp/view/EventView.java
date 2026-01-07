package com.example.lp.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.example.lp.event.entity.Event;
import com.example.lp.event.entity.EventItem;

import java.util.List;

@EntityView(Event.class)
public interface EventView {
    @IdMapping
    Long getId();
    String getName();
    String getState();
    List<EventItemView> getEventItemList();
}
