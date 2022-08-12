package com.sparta.miniproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.miniproject.model.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private Long id;
    private String contents;
    private String imgUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime modifiedAt;

    @Builder
    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.contents = post.getContents();
        this.imgUrl = post.getImgUrl();
        this.modifiedAt = post.getModifiedAt();
    }
}
