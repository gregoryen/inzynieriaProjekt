package com.engineering.shop.advImageUpload;

public class AdvStorageException extends RuntimeException {
    public AdvStorageException(String message) {
        super(message);
    }

    public AdvStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
