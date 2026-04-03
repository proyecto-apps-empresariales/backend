package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dto de respuesta para los Requerimientos de peticiones")
public class RequerimientoPeticionResponseDTO {

    @Schema(description = "Id único del Requerimiento", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nombre del Requerimiento", example = "Firma del destinatario")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 20, message = "Debe tener máximo 20 caracteres")
    private String nombre;

    @Schema(description = "Descripción del Requerimiento", example = "Necesita firma del destinatario")
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 250, message = "Debe tener máximo 250 caracteres")
    private String descripcion;

}
