package com.cleanarchitecture.domain.exception;

public class AppException extends RuntimeException {
    public AppException(String message) {
        super(message);
    }
}
