package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "DTO para actualizar la información de un Permiso")
public class PermisoUpdateDTO {

    @Schema(description = "Nombre del permiso", example = "GESTION USUARIOS")
    @Size(min = 3, max = 20, message = "Debe tener entre 3 y 20 caracteres")
    private String nombre;

    @Schema(description = "Descripción del permiso", example = "Permite gestionar usuarios dentro del sistema")
    @Size(min = 3, max = 250, message = "Debe tener entre 3 y 250 caracteres")
    private String descripcion;

}