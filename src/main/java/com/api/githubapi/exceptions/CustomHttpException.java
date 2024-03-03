package com.api.githubapi.exceptions;

public class CustomHttpException extends RuntimeException {
    private final int httpStatus;
    public CustomHttpException(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}