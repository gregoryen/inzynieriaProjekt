package com.engineering.shop.users.exceptions;

public abstract class UserException extends RuntimeException {
    public UserException() {
    }

    public UserException(String message) {
        super(message);
    }
}
