package com.example.demo.controller;

import com.example.demo.exception.DomainException;
import com.example.demo.exception.ErrorDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = DomainException.class)
    public ErrorDetail domainException(HttpServletResponse resp, DomainException e) {
        resp.setStatus(e.getHttpStatus().value());
        return e.toDetail();
    }
}
