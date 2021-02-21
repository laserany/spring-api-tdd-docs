package com.example.testy.exception.handler;

import com.example.testy.exception.TestyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TestyExceptionHandler {

    @ExceptionHandler(value = TestyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String testyNotFound(TestyNotFoundException e) {
        return "Testy with id %s was not found".formatted(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String testyMethodArgumentInvalid(MethodArgumentNotValidException e) {
        return e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    }
}
