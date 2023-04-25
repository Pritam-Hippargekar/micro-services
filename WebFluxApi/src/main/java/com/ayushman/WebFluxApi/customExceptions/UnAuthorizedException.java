package com.ayushman.WebFluxApi.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnAuthorizedException extends ResponseStatusException {
//    private String message;
    public UnAuthorizedException(HttpStatus status, String message) {
        super(status, message);
//        this.message = message;
    }

    public UnAuthorizedException(HttpStatus status, String message, Throwable throwable) {
        super(status, message, throwable);
    }

}