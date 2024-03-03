package com.api.githubapi.exceptions;

import feign.FeignException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GeneralExceptionHandler{
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> customExceptionHandler(FeignException exception, WebRequest webRequest){
        return new ResponseEntity<>(new ErrorResponse(exception.status(), exception.getMessage()),
                new HttpHeaders(),
                exception.status());
    }
}
