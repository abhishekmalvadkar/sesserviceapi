package com.amalvadkar.ses.common.exceptions;

import lombok.Getter;

@Getter
public class SesException extends RuntimeException {

    private final Integer code;

    public SesException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
