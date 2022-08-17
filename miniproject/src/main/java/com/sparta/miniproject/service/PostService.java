package com.sparta.miniproject.service;

import com.sparta.miniproject.S3.S3Service;
import com.sparta.miniproject.dto.PostRequestDto;
import com.sparta.miniproject.dto.PostResponseDto;
import com.sparta.miniproject.exception.BusinessException;
import com.sparta.miniproject.exception.ErrorCode;
import com.sparta.miniproject.model.Post;
import com.sparta.miniproject.model.User;
import com.sparta.miniproject.repository.CommentRepository;
import com.sparta.miniproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
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
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final S3Service s3Service;

    // 포스트 리스트 조회
    public List<PostResponseDto> getPostList() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> postList = new ArrayList<>();
        for (Post post : posts) {
            int countComment = commentRepository.countByPostId(post.getPostId());
            PostResponseDto postResponseDto = PostResponseDto.builder()
                    .post(post)
                    .countComment(countComment)
                    .build();
            postList.add(postResponseDto);
        }
        return postList;
    }

    // 포스트 상세 조회
    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("포스트가 존재하지 않습니다."));
        int countComment = commentRepository.countByPostId(post.getPostId());
        return PostResponseDto.builder()
                .post(post)
                .countComment(countComment)
                .build();
    }

    private String getUser() {
        return userService.getMyInfo().getUsername();
    }

    // 포스트 생성
    @Transactional
    public Post createPost(PostRequestDto requestDto, MultipartFile imageFile) {
        requestDto.setAuthor(getUser());
        String imagePath;
        if (!Objects.isNull(imageFile)) {
            try {
                imagePath = s3Service.uploadImage(imageFile);
                requestDto.setImgUrl(imagePath);
            } catch (NullPointerException e) {
                throw new NullPointerException("파일 변환에 실패했습니다");
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        Post post = requestDto.createPost();
        return postRepository.save(post);
    }

    //  포스트 수정
    @Transactional
    public Post updatePost(Long postId, PostRequestDto requestDto, MultipartFile imageFile) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
        if(!getUser().equals(post.getAuthor())) {
            throw new BusinessException(ErrorCode.POST_UNAUTHORIZED);
        }
        if(requestDto.getContents() == null || requestDto.getContents().equals("")){
            throw new BusinessException(ErrorCode.LENGTH_REQUIRED);
        }
        String imagePath;
        if (!Objects.isNull(imageFile)) {
            imagePath = s3Service.uploadImage(imageFile);
            requestDto.setImgUrl(imagePath);
        }
        post.update(requestDto.getContents(), requestDto.getImgUrl());
        return post;
    }

    // 포스트 삭제
    public Long deletePost(Long postId) throws IllegalArgumentException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
        if(!getUser().equals(post.getAuthor())) {
            throw new BusinessException(ErrorCode.POST_UNAUTHORIZED);
        }
        postRepository.deleteById(postId);
        return postId;
    }
}
