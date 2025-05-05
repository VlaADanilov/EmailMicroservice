package com.technokratos.emailmicroservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ioe) {
        return new ResponseEntity<>(ioe.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
