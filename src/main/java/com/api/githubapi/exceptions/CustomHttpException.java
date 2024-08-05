package com.api.githubapi.exceptions;

public class CustomHttpException extends RuntimeException {
    private final int status;
    public CustomHttpException(String message, int status){
        super(message);
        this.status = status;
    }
    public int getHttpStatus(){
        return status;
    }
}