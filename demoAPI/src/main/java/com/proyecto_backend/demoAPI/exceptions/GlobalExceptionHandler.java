package com.proyecto_backend.demoAPI.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.proyecto_backend.demoAPI.security.dto.ApiErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // =====================================================
    // 🔴 VALIDACIONES DTO (@Valid) -> 400
    // =====================================================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Error de validación",
                new Date(),
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    // =====================================================
    // 🔴 404 - RESOURCE NOT FOUND
    // =====================================================
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex) {

        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                ex.getTimestamp(),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    // =====================================================
    // 🔴 400 - BAD REQUEST NEGOCIO
    // =====================================================
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex) {

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                ex.getTimestamp(),
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    // =====================================================
    // 🔴 409 - CONFLICT (Duplicados, reglas de negocio)
    // =====================================================
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleConflict(ConflictException ex) {

        ApiError apiError = new ApiError(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                ex.getTimestamp(),
                null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    // =====================================================
    // 🔴 401 - NO AUTENTICADO
    // =====================================================
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> handleUnauthorized(UnauthorizedException ex) {

        ApiError apiError = new ApiError(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                ex.getTimestamp(),
                null
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    // =====================================================
    // 🔴 403 - NO AUTORIZADO
    // =====================================================
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiError> handleForbidden(ForbiddenException ex) {

        ApiError apiError = new ApiError(
                HttpStatus.FORBIDDEN.value(),
                ex.getMessage(),
                ex.getTimestamp(),
                null
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
    }

    // =====================================================
    // 🔴 500 - ERROR NO CONTROLADO
    // =====================================================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception ex) {

        log.error("Error interno no controlado", ex);

        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor",
                new Date(),
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    // =====================================================
    // 🔴 ERRORES DE AUTENTICACIÓN (Spring Security)
    // =====================================================
    // Maneja errores de credenciales inválidas en el proceso de login.
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCredentials(
            BadCredentialsException ex,
            HttpServletRequest request) {
        ApiErrorResponse body = ApiErrorResponse.of(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                "Credenciales invalidas",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    
}