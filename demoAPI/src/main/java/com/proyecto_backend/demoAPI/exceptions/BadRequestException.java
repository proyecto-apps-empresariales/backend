package com.proyecto_backend.demoAPI.exceptions;

import java.util.Date;

public class BadRequestException extends RuntimeException {

    private final Date timestamp = new Date();

    public BadRequestException(String message) {
        super(message);
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
