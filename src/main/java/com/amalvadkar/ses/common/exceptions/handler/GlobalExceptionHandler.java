package com.amalvadkar.ses.common.exceptions.handler;

import com.amalvadkar.ses.common.exceptions.SesException;
import com.amalvadkar.ses.common.models.response.CustomResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String SOMETHING_WENT_WRONG_ERROR_MESSAGE = "Something went wrong, please contact to admin..";
    private static final String LINE_BREAK = "</br>";

    @ExceptionHandler(SesException.class)
    public CustomResponse handleSesException(SesException ex) {
        logException(ex);
        return CustomResponse.fail(ex.getMessage(), ex.getCode());
    }

    @ExceptionHandler(Throwable.class)
    public CustomResponse handleException(Throwable ex) {
        logException(ex);
        return CustomResponse.fail(SOMETHING_WENT_WRONG_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CustomResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        logException(ex);
        String errors = ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(LINE_BREAK));
        return CustomResponse.fail(errors, HttpStatus.BAD_REQUEST);
    }

    private void logException(Throwable ex) {
        log.error("Exception occurred -> ", ex);
    }


}
