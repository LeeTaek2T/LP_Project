package com.example.lp.event.dto.Response;

import java.time.LocalDateTime;

public record EventResponse (Long eventId,
                             String name,
                             LocalDateTime startAt,
                             LocalDateTime endAt,
                             String state){}
