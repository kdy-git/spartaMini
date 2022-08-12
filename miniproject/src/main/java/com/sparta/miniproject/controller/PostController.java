package com.sparta.miniproject.controller;


import com.sparta.miniproject.dto.PostRequestDto;
import com.sparta.miniproject.dto.PostResponseDto;
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

    // 포스트 리스트 조회
    @GetMapping("/api/posts")
    public List<PostResponseDto> getpostList() {
        return postService.getPostList();
    }

    // 포스트 상세 조회
    @ResponseBody
    @GetMapping("/api/post/{id}")
    public HashMap getPost(@PathVariable Long id) {
        HashMap p = new HashMap();
        p.put("contents", postService.getPost(id));
        return p;
    }

    // 포스트 생성
    @PostMapping("/api/post")
    public ResponseEntity<String> createPosts(PostRequestDto requestDto, MultipartFile imageFile) {

        postService.createPost(requestDto, imageFile);
        return new ResponseEntity<>("컨텐츠 등록에 성공했습니다", HttpStatus.OK);
    }

    // 포스트 수정
    @PutMapping("/api/post/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id, PostRequestDto requestDto, MultipartFile imageFile) {
            try {
                postService.updatePost(id, requestDto, imageFile);
            } catch (NullPointerException | IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
            }
        return new ResponseEntity<>("컨텐츠 수정에 성공했습니다.", HttpStatus.OK);
    }

    // 포스트 삭제
    @DeleteMapping("/api/post/{id}")
    public Long deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }
}