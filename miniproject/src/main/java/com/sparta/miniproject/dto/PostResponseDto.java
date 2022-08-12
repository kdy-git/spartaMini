package com.sparta.miniproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.miniproject.model.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private Long id;
    private String username;
    private String contents;
    private String imageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime modifiedAt;

    @Builder
    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.username = post.getUsername();
        this.contents = post.getContents();
        this.imageUrl = post.getImgUrl();
        this.modifiedAt = post.getModifiedAt();
    }
}
