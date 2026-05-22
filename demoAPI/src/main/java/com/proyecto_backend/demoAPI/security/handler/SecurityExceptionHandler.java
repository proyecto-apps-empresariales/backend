package com.proyecto_backend.demoAPI.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto_backend.demoAPI.security.dto.ApiErrorResponse;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Respuestas JSON 401/403 alineadas con {@link com.rafaelperez.tiendaonline.security.dto.ApiErrorResponse}.
 */
@Component
public class SecurityExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(jakarta.servlet.http.HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        writeError(response, request, HttpStatus.UNAUTHORIZED, "No autenticado o token invalido");
    }

    @Override
    public void handle(jakarta.servlet.http.HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        writeError(response, request, HttpStatus.FORBIDDEN, "No autorizado para este recurso");
    }

    private void writeError(HttpServletResponse response, jakarta.servlet.http.HttpServletRequest request,
                            HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        ApiErrorResponse body = ApiErrorResponse.of(
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );
        objectMapper.writeValue(response.getWriter(), body);
    }
    
}
