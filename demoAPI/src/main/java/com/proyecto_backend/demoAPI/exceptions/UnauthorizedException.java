package com.proyecto_backend.demoAPI.exceptions;

import java.util.Date;

public class UnauthorizedException extends RuntimeException {
    private final Date timestamp = new Date();

    public UnauthorizedException(String message) {
        super(message);
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
