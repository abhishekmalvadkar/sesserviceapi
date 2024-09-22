package com.amalvadkar.ses.account.exceptions;

import com.amalvadkar.ses.common.exceptions.SesException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends SesException {
    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED.value());
    }
}
