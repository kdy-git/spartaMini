package com.sparta.miniproject.exception;

import com.sparta.miniproject.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> handleBusinessException(BusinessException exception) {
        return new ResponseEntity<>(ResponseDto.fail(ErrorCode.S3_UPLOAD_FAILED), HttpStatus.valueOf(ErrorCode.S3_UPLOAD_FAILED.getStatus()));
    }
}
