package com.cart4j.cart.exception;

public class NoUserCartException extends Exception {

    private static final long serialVersionUID = -5206714119701819619L;

    public NoUserCartException(String message) {
        super(message);
    }
}
