package com.example.lp.event.dto.Response;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record EventResponse (Long eventId,
                             String name,
                             OffsetDateTime startAt,
                             OffsetDateTime endAt,
                             String state,
                             String coverImageUrl){}
