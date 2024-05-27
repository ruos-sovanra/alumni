package com.example.alumni.feature.study_abroad;

import com.example.alumni.domain.StudyAbroad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyAbroadRepository extends JpaRepository<StudyAbroad, String> {

    Optional<StudyAbroad> findByCountry(String title);
}
