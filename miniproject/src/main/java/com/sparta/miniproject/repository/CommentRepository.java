package com.sparta.miniproject.repository;

import com.sparta.miniproject.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.postId = :postId")
    List<Comment> findAllByOrderByCreatedAtDesc(Long postId);

    int countByPostId(Long PostId);

    @Transactional
    void deleteByPostId(Long PostId);
}
