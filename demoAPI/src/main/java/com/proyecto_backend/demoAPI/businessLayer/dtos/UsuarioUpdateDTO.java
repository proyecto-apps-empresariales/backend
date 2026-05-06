package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO para actualizar la información de un Usuario")
public class UsuarioUpdateDTO {

    @Schema(description = "Nombre del usuario", example = "Mateo")
    @Size(min = 3, max = 50, message = "Debe tener entre 3 y 50 caracteres")
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "García")
    @Size(min = 3, max = 50, message = "Debe tener entre 3 y 50 caracteres")
    private String apellido;

    @Schema(description = "Número de celular del usuario", example = "3123456789")
    @Size(min = 3, max = 50, message = "Debe tener entre 3 y 50 caracteres")
    private String celular;

    @Schema(description = "Estado del usuario (activo/inactivo)", example = "true")
    private Boolean estaActivo;

    @Schema(description = "Identificador de la organización a la que pertenece el usuario", example = "1")
    @Positive(message = "El id de organización debe ser mayor a 0")
    private Long idOrganizacion;

    @Schema(description = "Identificador del rol asignado al usuario", example = "2")
    @Positive(message = "El id de rol debe ser mayor a 0")
    private Long idRol;

}