package com.sparta.miniproject.repository;

import com.sparta.miniproject.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
    <T> List<T> findAllBy(Class<T> type);
    List<Post> findAllByUsername(String username);

}
