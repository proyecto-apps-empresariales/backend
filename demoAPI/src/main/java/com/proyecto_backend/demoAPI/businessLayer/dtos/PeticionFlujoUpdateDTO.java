package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Informacion para actualizar una Petición/Flujo")
public class PeticionFlujoUpdateDTO {

    @Schema(description = "Id del usuario destinatario", example = "5")
    private Long destinatario;

    @Schema(description = "Id del tipo de la petición", example = "Cancelación de materia", accessMode = Schema.AccessMode.READ_ONLY)
    private Long tipoPeticion;

    @Schema(description = "Id de la petición", example = "Enviado", accessMode = Schema.AccessMode.READ_ONLY)
    private Long estado;

    @Schema(description = "Fecha de vigencia de la petición -> yyyy-MM-dd", example = "2026-10-27", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank(message = "La fecha de fin no puede estar vacía")
    private LocalDate fechaFin;

    @Schema(description = "Descripción de la petición", example = "A través de esta petición, se desean cancelar 3 materias del semestre")
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 250, message = "Debe tener máximo 250 caracteres")
    private String descripcion;

    @Schema(description = "Nombre de la petición", example = "Cancelación materias Bryan Vanegas, nocturna")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 250, message = "Debe tener máximo 50 caracteres")
    private String nombre;

}
