package com.example.alumni.feature.repo;

import com.example.alumni.domain.DonationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationTypeRepository extends JpaRepository<DonationType, String> {
}
