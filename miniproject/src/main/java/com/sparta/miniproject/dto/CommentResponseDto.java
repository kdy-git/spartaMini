package com.sparta.miniproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.miniproject.model.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long commentId;

    private String author;

    private String commentContent;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime modifiedAt;

    @Builder
    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.author = comment.getAuthor();
        this.commentContent = comment.getCommentContent();
        this.modifiedAt = comment.getModifiedAt();
    }

}
