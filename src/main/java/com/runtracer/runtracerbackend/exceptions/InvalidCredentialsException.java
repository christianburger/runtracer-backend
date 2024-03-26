package com.runtracer.runtracerbackend.exceptions;

public class InvalidCredentialsException extends CustomException {
    public InvalidCredentialsException() {
        super("Invalid credentials");
    }
}