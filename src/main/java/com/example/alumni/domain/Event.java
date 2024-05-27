package com.example.alumni.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@Table(name = "al_events")
@Setter
@Getter
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String eventName;
    private String eventPoster;
    @Column(columnDefinition = "TEXT")
    private String eventDescription;
    private LocalDateTime scheduledDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    private Time eventStart;
    private Time eventEnd;

    //posttype relatioship

    @ManyToOne
    @JoinColumn(name = "post_type")
    private Post postType;

    @ManyToOne
    @JoinColumn(name = "event_type")
    private EventType eventType;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;


}
