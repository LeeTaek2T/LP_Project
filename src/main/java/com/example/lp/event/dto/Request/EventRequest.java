package com.example.lp.event.dto.Request;
import java.time.OffsetDateTime;

public record EventRequest(String name,
                           OffsetDateTime startAt,
                           OffsetDateTime endAt) {}
