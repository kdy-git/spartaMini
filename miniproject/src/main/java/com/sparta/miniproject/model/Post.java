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

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String contents;

    private String imgUrl;

    @Builder
    public Post (String author, String contents, String imgUrl) {
        this.author = author;
        this.contents = contents;
        this.imgUrl = imgUrl;
    }

    public void update(String contents, String imgUrl) {
        this.contents = contents;
        this.imgUrl = imgUrl;
    }
}
