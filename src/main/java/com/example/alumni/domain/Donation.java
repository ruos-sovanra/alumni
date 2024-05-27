package com.example.alumni.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "al_donations")
@Getter
@Setter
@NoArgsConstructor
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "donation_type_id")
    private DonationType donationType;

    @ManyToOne
    @JoinColumn(name = "support_type_id")
    private SupportType supportType;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    private String thumbnail;
}
