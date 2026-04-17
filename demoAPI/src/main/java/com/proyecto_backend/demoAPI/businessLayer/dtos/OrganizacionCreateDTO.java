package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para la creación de una Organización")
public class OrganizacionCreateDTO {

    @Schema(description = "Nombre de la organización", example = "Universidad del Quindío")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 50, message = "Debe tener entre 3 y 50 caracteres")
    private String nombre;

    @Schema(description = "Descripción de la organización", example = "Institución de educación superior ubicada en Armenia, Quindío")
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 5, max = 250, message = "Debe tener entre 5 y 250 caracteres")
    private String descripcion;

}