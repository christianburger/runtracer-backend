package com.runtracer.runtracerbackend.exceptions;

public class UserAlreadyInDatabase extends CustomException {
    public UserAlreadyInDatabase() {
        super("User already in database");
    }
}