package com.amalvadkar.ses.account.exceptions;

import com.amalvadkar.ses.common.exceptions.SesException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ConfirmPasswordUnMatchedException extends SesException {
    public ConfirmPasswordUnMatchedException(String message) {
        super(message, HttpStatus.BAD_REQUEST.value());
    }
}
