package com.cart4j.cart.exception;

public class InvalidCartException extends Exception {

    private static final long serialVersionUID = 6282503581915755651L;

    public InvalidCartException(String message) {
        super(message);
    }
}
