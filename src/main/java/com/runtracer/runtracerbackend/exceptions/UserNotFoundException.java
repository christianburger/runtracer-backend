package com.runtracer.runtracerbackend.exceptions;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException() {
        super("User not found");
    }
}