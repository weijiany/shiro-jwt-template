package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class UnAuthenticationException extends DomainException {

    public UnAuthenticationException(String message) {
        super(message);
    }

    @Override
    public ErrorDetail toDetail() {
        return new ErrorDetail("unAuthenticate", getMessage());
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
