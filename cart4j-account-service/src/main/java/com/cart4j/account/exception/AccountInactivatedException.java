package com.cart4j.account.exception;

public class AccountInactivatedException extends Exception {

    private static final long serialVersionUID = -7357279313047733631L;

    public AccountInactivatedException(String message) {
        super(message);
    }
}
