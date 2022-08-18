package com.sparta.miniproject.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    // Common
    SUCCESS(200, "C000", "Success"),
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

    // Member
    EMAIL_DUPLICATION(400, "M001", "Email is Duplication"),
    LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid"),

    // Post
    POST_NOT_FOUND(400, "P001", "Post is not Found"),
    LENGTH_REQUIRED(411, "P002", "Length Required"),
    POST_UNAUTHORIZED(401, "P003", "Unauthorized Access"),

    // Comment
    COMMENT_NOT_FOUND(400, "Co001", "Comment is not Found"),
    COMMENT_UNAUTHORIZED(401, "Co002", "Unauthorized Access"),

    // S3
    S3_UPLOAD_FAILED(400, "S001", "S3 Bucket Object Upload Fail"),
    INVALID_IMAGE_FILE_EXTENSION(400, "S002", "Need bmp,jpg,jpeg,png Type Extension");
    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
