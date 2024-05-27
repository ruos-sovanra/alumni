package com.example.alumni.feature.repo;

import com.example.alumni.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

  Post findByType(String name);

  Optional<Post> findPostByType (String type);
}
