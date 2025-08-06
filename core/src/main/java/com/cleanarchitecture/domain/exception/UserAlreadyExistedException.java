/*
 *  UserNotFoundException
 *  @author: minhhieuano
 *  @created 8/7/2025 2:39 AM
 * */


package com.cleanarchitecture.domain.exception;

public class UserAlreadyExistedException extends AppException {

    public UserAlreadyExistedException(String message) {
        super(message);
    }

    public UserAlreadyExistedException() {
        super("User already existed!");
    }
}
