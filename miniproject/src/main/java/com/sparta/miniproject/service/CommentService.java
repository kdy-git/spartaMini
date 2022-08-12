package com.sparta.miniproject.service;


import com.sparta.miniproject.dto.CommentRequestDto;
import com.sparta.miniproject.dto.CommentResponseDto;
import com.sparta.miniproject.model.Comment;
import com.sparta.miniproject.model.Post;
import com.sparta.miniproject.repository.CommentRepository;
import com.sparta.miniproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    // 댓글 조회
    public List<CommentResponseDto> getComment(Long contentId) {
        List<Comment> comments = commentRepository.findAllByOrderByCreatedAtDesc(contentId);
        if(comments == null) {
            throw new IllegalArgumentException("댓글이 없습니다.");
        }
        List<CommentResponseDto> commentList = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResponseDto commentResponseDto = CommentResponseDto.builder()
                    .comment(comment)
                    .build();
            commentList.add(commentResponseDto);
        }
        return commentList;
    }

    // 댓글 생성
    @Transactional
    public void createComment(HashMap data) {
        String commentContent = (String) data.get("comment");

        Comment comment = Comment.builder()
                .commentContent(commentContent)
                .build();
        commentRepository.save(comment);
    }

    // 댓글 수정
    @Transactional
    public void updateComment(Long commentId, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        comment.update(requestDto.getCommentContent());
    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다"));
        commentRepository.deleteById(commentId);
    }
}




