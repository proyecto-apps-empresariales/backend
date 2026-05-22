package com.proyecto_backend.demoAPI.businessLayer.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// DTO para la autenticación de usuarios, utilizado para recibir las credenciales de inicio de sesión. Contiene el correo electrónico y 
// la contraseña del usuario, ambos campos son obligatorios y deben cumplir con las validaciones correspondientes.
public record AuthRequest(

    @NotBlank(message = "El correo no puede estar vacío") 
    @Email(message = "El correo debe ser un correo electrónico válido") 
    String correo,
    @NotBlank(message = "La contraseña no puede estar vacía") 
    String contrasena

) {
}
