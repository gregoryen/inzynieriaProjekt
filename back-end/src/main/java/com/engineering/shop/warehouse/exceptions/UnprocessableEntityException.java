package com.engineering.shop.warehouse.exceptions;

public class UnprocessableEntityException extends RuntimeException {
    public UnprocessableEntityException() {
        super();
    }

    public UnprocessableEntityException(String message) {
        super(message);
    }

    public UnprocessableEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}