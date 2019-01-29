package com.cart4j.cart.exception;

public class NoCartItemException extends Exception {
    private static final long serialVersionUID = 4283066540969287144L;

    public NoCartItemException(String message) {
        super(message);
    }
}
