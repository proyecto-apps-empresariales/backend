package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de respuesta para Organización")
public class OrganizacionDTO {

    @Schema(description = "Identificador único de la organización", example = "10")
    private Long idOrganizacion;

    @Schema(description = "Nombre de la organización", example = "Universidad del Quindío")
    private String nombre;

    @Schema(description = "Descripción de la organización", example = "Institución de educación superior ubicada en Armenia, Quindío")
    private String descripcion;

    @Schema(description = "Fecha de creación de la organización", example = "2026-04-13")
    private LocalDate fechaCreacion;

}