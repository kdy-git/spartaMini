package com.sparta.miniproject.model;

import lombok.Getter;

@Getter
// api의 query param을 구분하기 위한 enum
public enum TargetType {
    POST("POST"),
    COMMENT("COMMENT");

    TargetType(String value) {
        this.value = value;
    }

    private final String value;
}