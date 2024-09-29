package com.amalvadkar.ses.enquiry.exceptions;

import com.amalvadkar.ses.common.exceptions.SesException;
import org.springframework.http.HttpStatus;

public class EnquiryNotFoundException extends SesException {
    public EnquiryNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND.value());
    }
}
