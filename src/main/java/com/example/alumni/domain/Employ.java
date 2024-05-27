package com.example.alumni.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "al_employ_types")
@Setter
@Getter
@NoArgsConstructor
public class Employ {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String employType;
}
