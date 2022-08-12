package com.sparta.miniproject.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    @JsonBackReference
    private Post post;


    private String username;

    @Column(nullable = false)
    private String commentContent;

    @Builder
    public Comment(Post post, String username, String commentContent) {
        this.post = post;
        this.username = username;
        this.commentContent = commentContent;
    }

    public void update(String commentContent) {
        this.commentContent = commentContent;
    }
}
