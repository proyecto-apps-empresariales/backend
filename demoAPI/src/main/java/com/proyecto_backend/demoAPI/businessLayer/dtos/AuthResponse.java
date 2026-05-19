package com.proyecto_backend.demoAPI.businessLayer.dtos;

import java.util.List;

public record AuthResponse(

    // Token de acceso
    String accessToken,
    // Tipo de token (por ejemplo, "Bearer")
    String tokenType,
    // Tiempo de expiración del token en segundos
    Long expiresInSeconds,
    // Información adicional del usuario autenticado
    String nombre,
    // Correo electrónico del usuario autenticado
    String correo,
    // Rol del usuario autenticado
    List<String> roles

) {
    
}
