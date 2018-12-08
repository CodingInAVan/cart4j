package com.cart4j.auth.exception;

public class RoleAlreadyExistingException extends Exception {

    private static final long serialVersionUID = 2558842758232954320L;
    public RoleAlreadyExistingException(String message) {
        super(message);
    }
}
