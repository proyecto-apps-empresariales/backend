package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "DTO de respuesta para Permiso")
public class PermisoDTO {

    @Schema(description = "Identificador único del permiso", example = "12")
    private Long idPermiso;

    @Schema(description = "Nombre del permiso", example = "GESTION USUARIOS")
    private String nombre;

    @Schema(description = "Descripción del permiso", example = "Permite gestionar usuarios dentro del sistema")
    private String descripcion;

}