package com.api.projectweb.service;

import org.springframework.http.HttpStatus;


public class MovieServiceException extends Exception {
    private final HttpStatus status;

    public MovieServiceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public MovieServiceException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
