package com.sparta.miniproject.dto;

import com.sparta.miniproject.model.Post;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostRequestDto {

    private String username;
    private String contents;
    private String imgUrl;

    public Post createPost() {
        return Post.builder()
                .username(username)
                .contents(contents)
                .imgUrl(imgUrl)
                .build();
    }
}
