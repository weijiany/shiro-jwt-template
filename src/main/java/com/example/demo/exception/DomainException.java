package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public abstract class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }

    public abstract ErrorDetail toDetail();

    public abstract HttpStatus getHttpStatus();
}
