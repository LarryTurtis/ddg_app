package com.ddg.restservice;

public class DDGException extends RuntimeException {

    public DDGException(String errorMessage) {
        super(errorMessage);
    }
}
