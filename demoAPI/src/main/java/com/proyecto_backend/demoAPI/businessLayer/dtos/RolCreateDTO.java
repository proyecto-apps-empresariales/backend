package com.proyecto_backend.demoAPI.businessLayer.dtos;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para la creación de un Rol")
public class RolCreateDTO {

    @Schema(description = "Nombre del rol", example = "Administrador")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 20, message = "Debe tener entre 3 y 20 caracteres")
    private String nombre;

    @Schema(description = "Descripción del rol", example = "Rol con permisos de administración en el sistema")
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 3, max = 250, message = "Debe tener entre 3 y 250 caracteres")
    private String descripcion;

    @Schema(description = "IDs de los permisos asociados al rol", example = "[1, 2, 3]")
    @NotEmpty(message = "Debe proporcionar al menos un permiso")
    private Set<Long> permisosIds; 

}