package com.example.alumni.feature.share;


import com.example.alumni.domain.Share;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends JpaRepository<Share, String> {
    Page<Share> findAll(Pageable pageable);
}
