package com.sparta.miniproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.miniproject.model.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private Long postId;
    private String contents;
    private String imgUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime modifiedAt;

    private int countComment;

    @Builder
    public PostResponseDto(Post post, int countComment) {
        this.postId = post.getPostId();
        this.contents = post.getContents();
        this.imgUrl = post.getImgUrl();
        this.modifiedAt = post.getModifiedAt();
        this.countComment = countComment;
    }
}
