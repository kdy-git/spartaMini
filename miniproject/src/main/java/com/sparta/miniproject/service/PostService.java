package com.sparta.miniproject.service;

import com.sparta.miniproject.S3.S3Service;
import com.sparta.miniproject.dto.PostRequestDto;
import com.sparta.miniproject.dto.PostResponseDto;
import com.sparta.miniproject.model.Post;
import com.sparta.miniproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final S3Service s3Service;

    // 포스트 리스트 조회
    public List<PostResponseDto> getPostList() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> postList = new ArrayList<>();
        for (Post post : posts) {
            PostResponseDto postResponseDto = PostResponseDto.builder()
                    .post(post)
                    .build();
            postList.add(postResponseDto);
        }
        return postList;
    }

    // 포스트 상세 조회
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("포스트가 존재하지 않습니다."));
        return PostResponseDto.builder()
                .post(post)
                .build();
    }

    // 포스트 생성
    @Transactional
    public Post createPost(PostRequestDto requestDto) {

        Post post = requestDto.createPost();
        return postRepository.save(post);
    }

    //  포스트 수정
    @Transactional
    public void updatePost(Long id, PostRequestDto requestDto, MultipartFile file) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("포스트가 존재하지 않습니다."));
        if(requestDto.getContents() == null || requestDto.getContents().equals("")){
            throw new NullPointerException("제목과 내용을 채워주세요.");
        }
        String imagePath;
        if (!Objects.isNull(file)) {
            imagePath = s3Service.uploadImage(file);
            requestDto.setImgUrl(imagePath);
        }
        post.update(requestDto.getContents(), requestDto.getImgUrl());
    }


    // 포스트 삭제
    public Long deletePost(Long id) {
        postRepository.deleteById(id);
        return id;
    }




}
