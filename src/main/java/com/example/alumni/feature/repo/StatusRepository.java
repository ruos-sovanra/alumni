package com.example.alumni.feature.repo;

import com.example.alumni.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, String> {

    Status findByStatus(String name);
}
