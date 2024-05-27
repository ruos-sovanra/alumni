package com.example.alumni.feature.employ;

import com.example.alumni.domain.Employ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployRepository extends JpaRepository<Employ, String> {
    Optional<Employ> findByEmployType(String employType);
}
