package com.proyecto_backend.demoAPI.security.dto;

import java.time.Instant;

// DTO para respuestas de error en la API. Usado por GlobalExceptionHandler.
public record ApiErrorResponse(
    String timestamp,
    int status,
    String error,
    String message,
    String path
) {

    // Método de fábrica para crear ApiErrorResponse de forma segura (evita null en path).
    public static ApiErrorResponse of(int status, String error, String message, String path) {
        String safePath = path != null ? path : "";
        return new ApiErrorResponse(Instant.now().toString(), status, error, message, safePath);
    }

}