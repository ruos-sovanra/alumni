package com.example.alumni.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "al_status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String status;
}
