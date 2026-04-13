package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO para actualizar la contraseña de un Usuario")
public class UsuarioUpdateContrasenaDTO {

    @Schema(description = "Identificador único del usuario", example = "101")
    @NotNull(message = "El idUsuario no puede ser nulo")
    @Positive(message = "El idUsuario debe ser mayor a 0")
    private Long idUsuario;

    @Schema(description = "Contraseña actual del usuario", example = "MiC0ntr@s3n@Antigua")
    @NotBlank(message = "La contraseña actual no puede estar vacía")
    @Size(min = 3, max = 250, message = "Debe tener entre 3 y 250 caracteres")
    private String contrasenaActual;

    @Schema(description = "Nueva contraseña del usuario", example = "MiNuev@C0ntr@s3n@")
    @NotBlank(message = "La nueva contraseña no puede estar vacía")
    @Size(min = 3, max = 250, message = "Debe tener entre 3 y 250 caracteres")
    private String contrasenaNueva;

    @Schema(description = "Confirmación de la nueva contraseña", example = "MiNuev@C0ntr@s3n@")
    @NotBlank(message = "La confirmación de la contraseña no puede estar vacía")
    @Size(min = 3, max = 250, message = "Debe tener entre 3 y 250 caracteres")
    private String contrasenaConfirmacion;

}