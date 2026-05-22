package com.proyecto_backend.demoAPI.businessLayer.dtos;

import java.util.List;

public record AuthResponse(

        // Token de acceso
        String accessToken,
        // Tipo de token (por ejemplo, "Bearer")
        String tokenType,
        // Tiempo de expiración del token en segundos
        Long expiresInSeconds,
        Long idUsuario,
        // Nombre del usuario autenticado
        String nombre,
        // Correo electrónico del usuario autenticado
        String correo,
        // Apellido del usuario autenticado
        String apellido,
        // Celular del usuario autenticado
        String celular,
        // Organización del usuario autenticado
        String nombreOrganizacion,
        // Rol del usuario autenticado
        List<String> roles,
        // Estado del usuario autenticado
        boolean estaActivo

) {}