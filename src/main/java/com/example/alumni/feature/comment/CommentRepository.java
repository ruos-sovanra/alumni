package com.example.alumni.feature.comment;


import com.example.alumni.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    List<Comment> findBySocialId(String socialId);
    List<Comment> findByUserId(String userId);
    List<Comment> findByParentCommentId(String parentCommentId);

    Page<Comment> findAll(Pageable pageable);
}
