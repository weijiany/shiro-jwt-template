package com.example.demo.controller;

import com.example.demo.exception.DomainException;
import com.example.demo.exception.ErrorDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    @ResponseBody
    public ErrorDetail domainException(HttpServletResponse resp, DomainException e) {
        resp.setStatus(e.getHttpStatus().value());
        return e.toDetail();
    }
}
