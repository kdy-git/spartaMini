package com.sparta.miniproject.controller;

import com.sparta.miniproject.dto.CommentRequestDto;
import com.sparta.miniproject.model.Comment;
import com.sparta.miniproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/api/post/{postId}/comment")
    public Comment createComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(postId, requestDto);

    }

    // 댓글 수정
    @PutMapping("/api/post/{postId}/{commentId}")
    public Comment updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(postId, commentId, requestDto);
    }

    // 댓글 삭제
    @DeleteMapping("/api/post/{postId}/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("댓글이 정상적으로 삭제되었습니다", HttpStatus.OK);
    }
}
