package com.proyecto_backend.demoAPI.businessLayer.services;

import com.proyecto_backend.demoAPI.businessLayer.dtos.AuthRequest;
import com.proyecto_backend.demoAPI.businessLayer.dtos.AuthResponse;

public interface AuthService {
    
    // Método para autenticar a un usuario y generar un token JWT
    AuthResponse login (AuthRequest authRequest);
}
