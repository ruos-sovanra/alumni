package com.example.alumni.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "al_donation_types")
@Getter
@Setter
@NoArgsConstructor
public class DonationType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String donationType;
}
