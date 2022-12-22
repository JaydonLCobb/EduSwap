package com.example.demo;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DataDoesNotExistException.class)
    public void handler(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }
}
