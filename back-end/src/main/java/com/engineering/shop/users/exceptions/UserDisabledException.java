package com.engineering.shop.users.exceptions;

public class UserDisabledException extends UserException {
    public UserDisabledException() {
    }

    public UserDisabledException(String message) {
        super(message);
    }
}
