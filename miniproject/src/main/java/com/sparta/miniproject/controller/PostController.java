package com.sparta.miniproject.controller;


import com.sparta.miniproject.S3.S3Service;
import com.sparta.miniproject.dto.PostRequestDto;
import com.sparta.miniproject.dto.PostResponseDto;
import com.sparta.miniproject.model.Post;
import com.sparta.miniproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final S3Service s3Service;

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
        String imagePath;
        if (!Objects.isNull(imageFile)) {
            try {
                imagePath = s3Service.uploadImage(imageFile);
                requestDto.setImgUrl(imagePath); // 받은 스트링값을 Url필드로 주입
            } catch (NullPointerException e) {
                return new ResponseEntity<>("파일 변환에 실패했습니다", HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
            }
        }
        postService.createPost(requestDto);
        return new ResponseEntity<>("컨텐츠 등록에 성공했습니다", HttpStatus.OK);
    }

//    @PutMapping("/api/post/{postId}")
//    public Long updatePost(@PathVariable Long id)
//
//    @DeleteMapping("/api/post/{postId}")



}
