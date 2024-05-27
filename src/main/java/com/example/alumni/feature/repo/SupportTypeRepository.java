package com.example.alumni.feature.repo;

import com.example.alumni.domain.SupportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportTypeRepository extends JpaRepository<SupportType, String> {
}
