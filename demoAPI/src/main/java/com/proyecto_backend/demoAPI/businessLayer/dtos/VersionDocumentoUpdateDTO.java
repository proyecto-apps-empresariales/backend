package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO Para actualizar una version de documento")
public class VersionDocumentoUpdateDTO {

    @Schema(description = "Nombre de la version del documento", example = "Informe epidemiológicos semanal - 10262026 V2")
    @NotBlank(message = "El nombre de la version documento no puede estar vacio")
    @Size(max = 20, message = "Debe tener máximo 20 caracteres")
    private String nombre;

    @Schema(description = "Descripcion de la version del documento", example = "Se implementaron datos estadisticos segun el informe presentado")
    @NotBlank(message = "La descripcion de la version documento no puede estar vacia")
    @Size(max = 250, message = "Debe tener máximo 250 caracteres")
    private String descripcion;
}
