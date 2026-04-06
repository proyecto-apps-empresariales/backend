package com.proyecto_backend.demoAPI.businessLayer.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioUpdateContrasenaDTO {
    
    // Atributos:
    @NotNull
    @Positive
    private Long idUsuario;
    @NotBlank
    @Size(min = 3, max = 250)
    private String contrasenaActual;
    @NotBlank
    @Size(min = 3, max = 250)
    private String contrasenaNueva;
    @NotBlank
    @Size(min = 3, max = 250)
    private String contrasenaConfirmacion;
}
