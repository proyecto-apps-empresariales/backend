package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para actualizar la información de un Rol")
public class RolUpdateDTO {

    @Schema(description = "Nombre del rol", example = "Editor")
    @Size(min = 3, max = 20, message = "Debe tener entre 3 y 20 caracteres")
    private String nombre;

    @Schema(description = "Descripción del rol", example = "Rol con permisos para editar contenido")
    @Size(min = 3, max = 250, message = "Debe tener entre 3 y 250 caracteres")
    private String descripcion;

}