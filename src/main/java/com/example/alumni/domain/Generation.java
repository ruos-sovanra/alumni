package com.example.alumni.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "al_generations")
@Getter
@Setter
@NoArgsConstructor
public class Generation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String genType;
    private Integer numGen;
}
