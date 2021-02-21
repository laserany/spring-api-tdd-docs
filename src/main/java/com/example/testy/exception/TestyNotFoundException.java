package com.example.testy.exception;

public class TestyNotFoundException extends  Exception{
    public TestyNotFoundException(Long errorMessage) {
        super(String.valueOf(errorMessage));
    }
}
