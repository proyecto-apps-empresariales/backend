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
@Schema(description = "DTO para actualizar documentos")
public class DocumentoUpdateDTO {

    @Schema(description = "Nombre del documento", example = "Informe epidemiológicos semanal - 10262026")
    @NotBlank(message = "El nombre del documento no puede estar vacio")
    @Size(max = 50, message = "Debe tener máximo 50 caracteres")
    private String nombre;

    @Schema(description = "Descripcion del documento", example = "Informe epidemiológicos de la semana 2 del 10262026")
    @NotBlank(message = "La descripcion del documento no puede estar vacio")
    @Size(max = 250, message = "Debe tener máximo 250 caracteres")
    private String descripcion;
}
