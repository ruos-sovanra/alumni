package com.example.alumni.feature.share_comment;

import com.example.alumni.domain.ShareComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareCommentRepository extends JpaRepository<ShareComment, String> {
    Page<ShareComment> findAll(Pageable pageable);
}
