package com.api.githubapi.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler{
    @ExceptionHandler(CustomHttpException.class)
    public ResponseEntity<Object> customExceptionHandler(CustomHttpException exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getHttpStatus(), exception.getMessage()),
                new HttpHeaders(),
                exception.getHttpStatus());
    }
}