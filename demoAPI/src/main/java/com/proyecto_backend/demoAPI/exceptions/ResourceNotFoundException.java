package com.proyecto_backend.demoAPI.exceptions;

import java.util.Date;

public class ResourceNotFoundException extends RuntimeException {

    private final Date timestamp = new Date();

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
