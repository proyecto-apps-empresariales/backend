package com.proyecto_backend.demoAPI.businessLayer.dtos;

import com.proyecto_backend.demoAPI.persistanceLayer.entidades.PeticionFlujoEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Informacion/respuesta sobre el historial de la petición/flujo")
public class HistorialPeticionFlujoResponseDTO {

    @Schema(description = "ID del registro del historial", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank(message = "El id no puede estar vacío")
    private Long id;

    @Schema(description = "ID de la petición a la que pertenece el registro/historial", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank(message = "El id de la petición no puede estar vacío")
    private Long peticion;

    @Schema(description = "Nombre del usuario que hizo la modificacion -> Generó el registro", example = "Juan", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank(message = "El nombre del usuario editor no puede estar vacío")
    private String usuarioEditor;

    @Schema(description = "Fecha de la modificación -> yyyy-MM-dd", example = "2026-10-27", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank(message = "La fecha no puede estar vacía")
    private LocalDate fecha;

    @Schema(description = "Información sobre los cambios/edición realizada", example = "Documento revisado")
    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

}
