package com.sparta.miniproject.model;


import com.sparta.miniproject.dto.CommentRequestDto;
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

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String commentContent;

    public Comment(Long postId, CommentRequestDto requestDto) {
        this.postId = postId;
        this.author = requestDto.getAuthor();
        this.commentContent = requestDto.getCommentContent();
    }

    public void update(CommentRequestDto requestDto) {
        this.commentContent = requestDto.getCommentContent();
    }
}
