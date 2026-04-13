package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de respuesta para Rol")
public class RolDTO {

    @Schema(description = "Identificador único del rol", example = "5")
    private Long idRol;

    @Schema(description = "Nombre del rol", example = "Administrador")
    private String nombre;

    @Schema(description = "Descripción del rol", example = "Rol con permisos de administración en el sistema")
    private String descripcion;

}