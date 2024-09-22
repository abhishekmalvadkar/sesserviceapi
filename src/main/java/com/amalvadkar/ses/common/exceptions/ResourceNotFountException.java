package com.amalvadkar.ses.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFountException extends SesException{
    public ResourceNotFountException(String message) {
        super(message, HttpStatus.NOT_FOUND.value());
    }
}
