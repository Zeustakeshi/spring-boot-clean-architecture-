/*
 *  UserNotFoundException
 *  @author: minhhieuano
 *  @created 8/7/2025 2:39 AM
 * */


package com.cleanarchitecture.domain.exception;

public class UserNotFoundException extends AppException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        super("User not found.");
    }
}
