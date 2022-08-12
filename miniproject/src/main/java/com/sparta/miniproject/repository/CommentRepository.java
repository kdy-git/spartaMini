package com.sparta.miniproject.repository;

import com.sparta.miniproject.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.post.postId = :postId")
    List<Comment> findAllByOrderByCreatedAtDesc(Long postId);
}
