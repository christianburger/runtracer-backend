package com.runtracer.runtracerbackend.exceptions;

public class UserIdNotNullException extends CustomException {
    public UserIdNotNullException() {
        super("User ID should be null");
    }
}