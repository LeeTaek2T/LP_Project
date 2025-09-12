package com.example.lp.event.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "start_at", nullable = false)
    private OffsetDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private OffsetDateTime endAt;

    @Column(name = "state", length = 20, nullable = false)
    private String state;

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventItem> eventItemList = new ArrayList<>();

    public Event(){}

    public Event(String name, OffsetDateTime startAt, OffsetDateTime endAt, String state, String coverImageUrl) {
        this.name = name;
        this.startAt = startAt;
        this.endAt = endAt;
        this.state = state;
        this.coverImageUrl = coverImageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public OffsetDateTime getStartAt() {
        return startAt;
    }

    public OffsetDateTime getEndAt() {
        return endAt;
    }

    public String getState() {
        return state;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }
}
