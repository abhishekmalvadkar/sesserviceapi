package com.amalvadkar.ses.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistException extends SesException{

    public ResourceAlreadyExistException(String message){
        super(message, HttpStatus.CONFLICT.value());
    }

}
