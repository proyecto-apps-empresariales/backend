package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información sobre Estado del Flujo")
public class EstadoPeticionFlujoResponseDTO {

    @Schema(description = "Id único del Estado", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull(message = "EL id no puede ser nulo")
    private Long id;

    @Schema(description = "Nombre del Estado", example = "Enviado")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 20, message = "Debe tener máximo 20 caracteres")
    private String nombre;

    @Schema(description = "Descripción del Estado", example = "El estado envíado se usa cúando una petición se envía, pero no se ha revisado aún")
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 250, message = "Debe tener máximo 250 caracteres")
    private String descripcion;
}
