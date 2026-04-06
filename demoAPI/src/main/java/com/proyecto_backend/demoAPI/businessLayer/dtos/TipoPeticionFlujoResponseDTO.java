package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dto response para el tipo de petición")
public class TipoPeticionFlujoResponseDTO {

    @Schema(description = "Id único del Tipo de Petición", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nombre del Tipo de Petición", example = "Cancelación de Materia")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 20, message = "Debe tener máximo 20 caracteres")
    private String nombre;

    @Schema(description = "Descripción del Tipo de Petición", example = "Con esta petición se solicita la cancelación de materias")
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 250, message = "Debe tener máximo 250 caracteres")
    private String descripcion;

    @Schema(description = "Url del archivo pdf con instrucciones", example = "/instrucciones/cancelacion_materias.pdf")
    @Size(max = 250, message = "Debe tener máximo 250 caracteres")
    private String instruccionesPdf;

    @Schema(description = "Lista de requermientos según Tipo de Petición", example = "Nombre Destinatario, Fecha creación, Firma del revisor")
    @NotEmpty(message = "La lista no puede estar vacía")
    private List<String> requerimientos;
}
