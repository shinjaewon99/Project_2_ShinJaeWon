package com.likelion.sns.global.exception;

import com.likelion.sns.article.likeArticle.exception.NotMyFeedLikeException;
import com.likelion.sns.global.dto.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            NotInputException.class,
            NotMyFeedLikeException.class,
            NotExistUserException.class
    })
    public ResponseEntity<ExceptionResponse> handleInvalidData(final RuntimeException e) {
        ExceptionResponse errorResponse = new ExceptionResponse(e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleFiledExceptionData(final MethodArgumentNotValidException e) {
        ExceptionResponse errorResponse = new ExceptionResponse("잘못된 데이터 타입 입니다.");
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
