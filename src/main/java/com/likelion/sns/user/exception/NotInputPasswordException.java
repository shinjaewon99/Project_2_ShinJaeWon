package com.likelion.sns.user.exception;

public class NotInputPasswordException extends RuntimeException {
    public NotInputPasswordException(final String message) {
        super(message);
    }
}