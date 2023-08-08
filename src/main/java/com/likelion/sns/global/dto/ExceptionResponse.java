package com.likelion.sns.global.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse {
    private String exceptionMessage;

    public ExceptionResponse(final String message) {
        this.exceptionMessage = message;
    }
}
