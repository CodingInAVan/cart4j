package com.cart4j.auth.exception;

public class UserAlreadyExistingException extends Exception {

    private static final long serialVersionUID = 5311668845955592037L;

    public UserAlreadyExistingException(String message) {
        super(message);
    }
}
