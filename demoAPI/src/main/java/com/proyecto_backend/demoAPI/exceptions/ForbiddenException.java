package com.proyecto_backend.demoAPI.exceptions;

import java.util.Date;

public class ForbiddenException extends RuntimeException {
    private final Date timestamp = new Date();

    public ForbiddenException(String message) {
        super(message);
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
