package com.sparta.miniproject.controller;

import com.sparta.miniproject.dto.CommentRequestDto;
import com.sparta.miniproject.dto.ResponseDto;
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
    public ResponseEntity<ResponseDto> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto) {
        return new ResponseEntity<>(
                ResponseDto.success(commentService.createComment(postId, requestDto)), HttpStatus.valueOf(HttpStatus.OK.value()));
    }

    // 댓글 수정
    @PutMapping("/api/post/{postId}/{commentId}")
    public ResponseEntity<ResponseDto> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
        return new ResponseEntity<>(
                ResponseDto.success(commentService.updateComment(postId, commentId, requestDto)), HttpStatus.valueOf(HttpStatus.OK.value()));
    }

    // 댓글 삭제
    @DeleteMapping("/api/post/{postId}/{commentId}")
    public ResponseEntity<ResponseDto> deleteComment(@PathVariable Long commentId) {
        return new ResponseEntity<>(
                ResponseDto.success(commentService.deleteComment(commentId)), HttpStatus.valueOf(HttpStatus.OK.value()));
    }
}
