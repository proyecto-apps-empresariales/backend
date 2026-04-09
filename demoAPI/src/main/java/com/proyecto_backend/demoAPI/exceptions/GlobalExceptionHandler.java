package com.proyecto_backend.demoAPI.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ===============================
    // VALIDACIONES @Valid (400)
    // ===============================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        // Recorre todos los errores de los campos del DTO
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Error de validación");
        response.put("timestamp", new Date());
        response.put("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // ===============================
    // ERROR 404 - RECURSO NO ENCONTRADO
    // ===============================
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(
            ResourceNotFoundException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", ex.getMessage());
        response.put("timestamp", ex.getTimestamp());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // ===============================
    // ERROR 400 - BAD REQUEST NEGOCIO
    // ===============================
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(
            BadRequestException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", ex.getMessage());
        response.put("timestamp", ex.getTimestamp());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // ===============================
    // ERROR 409 - CONFLICTO
    // ===============================
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(ConflictException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("error", ex.getMessage());
        response.put("timestamp", ex.getTimestamp());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // ===============================
    // ERROR 401 - NO AUTENTICADO -> FALTA INICIAR SESIÓN
    // ===============================
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorized(UnauthorizedException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("error", ex.getMessage());
        response.put("timestamp", ex.getTimestamp());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // ===============================
    // ERROR 403 - NO AUTORIZADO -> FALTAN PERMISOS
    // ===============================
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, Object>> handleForbidden(ForbiddenException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("error", ex.getMessage());
        response.put("timestamp", ex.getTimestamp());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    // ===============================
    // ERROR 500 - CUALQUIER ERROR NO CONTROLADO
    // ===============================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Error interno del servidor");
        response.put("timestamp", new Date());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


}
