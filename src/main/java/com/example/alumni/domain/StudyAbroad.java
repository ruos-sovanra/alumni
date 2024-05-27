package com.example.alumni.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "al_study_abroad")
@Getter
@Setter
@NoArgsConstructor
public class StudyAbroad {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String country;
}
