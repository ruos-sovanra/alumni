package com.example.alumni.feature.social;


import com.example.alumni.domain.Social;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialRepository extends JpaRepository<Social, String> {
    Page<Social> findAll(Pageable pageable);
}
