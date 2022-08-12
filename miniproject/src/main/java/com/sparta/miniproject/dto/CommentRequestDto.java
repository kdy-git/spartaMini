package com.sparta.miniproject.dto;

import com.sparta.miniproject.model.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CommentRequestDto {

    private String commentContent;

    public Comment createComment() {
        return Comment.builder()
                .commentContent(commentContent)
                .build();
    }

}
