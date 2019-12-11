package com.engineering.shop.users.exceptions;

public class BadUserCredentialsException extends UserException {
    public BadUserCredentialsException() {
    }

    public BadUserCredentialsException(String message) {
        super(message);
    }
}
