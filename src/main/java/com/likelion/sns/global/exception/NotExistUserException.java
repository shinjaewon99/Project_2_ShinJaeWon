package com.likelion.sns.global.exception;

public class NotExistUserException extends RuntimeException {

    public NotExistUserException(String message) {
        super(message);
    }
}
