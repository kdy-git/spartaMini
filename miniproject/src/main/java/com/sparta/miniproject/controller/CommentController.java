package com.sparta.miniproject.controller;

import com.sparta.miniproject.dto.CommentRequestDto;
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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/post/{postId}/comment")
    public ResponseEntity<String> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto) {
        commentService.createComment(postId, requestDto);
        return new ResponseEntity<>("댓글이 등록되었습니다", HttpStatus.OK);
    }

    // 댓글 수정
    @PutMapping("/api/post/{postId}/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Long commentId, CommentRequestDto requestDto) {
        commentService.updateComment(commentId, requestDto);
        return new ResponseEntity<>("댓글이 수정되었습니다", HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/api/post/{postId}/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("댓글이 정상적으로 삭제되었습니다", HttpStatus.OK);
    }
}
