package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto_backend.demoAPI.businessLayer.dtos.AuthRequest;
import com.proyecto_backend.demoAPI.businessLayer.dtos.AuthResponse;
import com.proyecto_backend.demoAPI.businessLayer.services.AuthService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login (@Valid @RequestBody AuthRequest authRequest) {
        // Punto de entrada para Angular/web: retorna access token JWT y roles.
        return ResponseEntity.ok(authService.login(authRequest));

    }
    
}
