package com.proyecto_backend.demoAPI.exceptions;

import java.util.Date;

public class ConflictException extends RuntimeException {

    private final Date timestamp = new Date();

    public ConflictException(String message) {
        super(message);
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
