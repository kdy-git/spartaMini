package com.sparta.miniproject.controller;


import com.sparta.miniproject.dto.PostRequestDto;
import com.sparta.miniproject.dto.PostResponseDto;
import com.sparta.miniproject.dto.ResponseDto;
import com.sparta.miniproject.model.Post;
import com.sparta.miniproject.service.CommentService;
import com.sparta.miniproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    // 포스트 리스트 조회
    @GetMapping("/api/posts")
    public ResponseEntity<ResponseDto> getPostList() {
        return new ResponseEntity<>(
                ResponseDto.success(postService.getPostList()), HttpStatus.valueOf(HttpStatus.OK.value()));
    }

    // 포스트 상세 조회
    @ResponseBody
    @GetMapping("/api/post/{postId}")
    public ResponseEntity<ResponseDto> getPost(@PathVariable Long postId) {
        HashMap p = new HashMap();
        p.put("contents", postService.getPost(postId));
        p.put("comment", commentService.getComment(postId));
        return new ResponseEntity<>(
                ResponseDto.success(p), HttpStatus.valueOf(HttpStatus.OK.value()));
    }

    // 포스트 생성
    @PostMapping("/api/post")
    public ResponseEntity<ResponseDto> createPost(PostRequestDto requestDto, MultipartFile imageFile) {
        return new ResponseEntity<>(
                ResponseDto.success(postService.createPost(requestDto, imageFile)), HttpStatus.valueOf(HttpStatus.OK.value()));
    }

    // 포스트 수정
    @PutMapping("/api/post/{postId}")
    public ResponseEntity<ResponseDto> updatePost(@PathVariable Long postId, PostRequestDto requestDto, MultipartFile imageFile) {
        return new ResponseEntity<>(
                ResponseDto.success(postService.updatePost(postId, requestDto, imageFile)), HttpStatus.valueOf(HttpStatus.OK.value()));
    }

    // 포스트 삭제
    @DeleteMapping("/api/post/{postId}")
    public ResponseEntity<ResponseDto> deletePost(@PathVariable Long postId) {
        return new ResponseEntity<>(
                ResponseDto.success(postService.deletePost(postId)), HttpStatus.valueOf(HttpStatus.OK.value()));
    }
}
