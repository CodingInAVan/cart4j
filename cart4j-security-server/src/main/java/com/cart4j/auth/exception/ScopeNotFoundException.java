package com.cart4j.auth.exception;

public class ScopeNotFoundException extends Exception {

    private static final long serialVersionUID = -8644619983463020276L;

    public ScopeNotFoundException(String message) {
        super(message);
    }
}
