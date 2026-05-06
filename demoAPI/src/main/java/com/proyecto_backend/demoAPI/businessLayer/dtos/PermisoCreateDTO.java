package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Schema(description = "DTO para la creación de un Permiso")
public class PermisoCreateDTO {

    @Schema(description = "Nombre del permiso", example = "GESTION USUARIOS")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 20, message = "Debe tener entre 3 y 20 caracteres")
    private String nombre;

    @Schema(description = "Descripción del permiso", example = "Permite gestionar usuarios dentro del sistema")
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 3, max = 250, message = "Debe tener entre 3 y 250 caracteres")
    private String descripcion;

}