package com.sparta.miniproject.service;


import com.sparta.miniproject.dto.CommentRequestDto;
import com.sparta.miniproject.exception.BusinessException;
import com.sparta.miniproject.exception.ErrorCode;
import com.sparta.miniproject.model.Comment;
import com.sparta.miniproject.repository.CommentRepository;
import com.sparta.miniproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;

    // 댓글 조회
    public List<Comment> getComment(Long postId) {
        return commentRepository.findAllByOrderByCreatedAtDesc(postId);
    }

    private String getUser() {
        return userService.getMyInfo().getUsername();
    }

    // 댓글 생성
    @Transactional
    public Comment createComment(Long postId, CommentRequestDto requestDto) {
        requestDto.setAuthor(getUser());
        return commentRepository.save(new Comment(postId, requestDto));
    }

    // 댓글 수정
    @Transactional
    public Comment updateComment(Long postId, Long commentId, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND));
        if(!getUser().equals(comment.getAuthor())) {
            throw new BusinessException(ErrorCode.COMMENT_UNAUTHORIZED);
        }
        comment.update(requestDto);
        return comment;
    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND));
        if(!getUser().equals(comment.getAuthor())) {
            throw new BusinessException(ErrorCode.COMMENT_UNAUTHORIZED);
        }
        commentRepository.deleteById(commentId);
    }
}




