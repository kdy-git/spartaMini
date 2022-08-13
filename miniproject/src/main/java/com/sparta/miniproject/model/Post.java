package com.sparta.miniproject.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Post extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long postId;

    private String username;

    @Column(nullable = false)
    private String contents;

    private String imgUrl;

    @Builder
    public Post (String username, String contents, String imgUrl) {
        this.username = username;
        this.contents = contents;
        this.imgUrl = imgUrl;
    }

    public void update(String contents, String imgUrl) {
        this.contents = contents;
        this.imgUrl = imgUrl;
    }
}
