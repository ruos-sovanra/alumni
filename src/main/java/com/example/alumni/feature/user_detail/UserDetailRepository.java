package com.example.alumni.feature.user_detail;


import com.example.alumni.domain.UserDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, String>, JpaSpecificationExecutor<UserDetail> {
    Page<UserDetail> findAll(Pageable pageable);
}
