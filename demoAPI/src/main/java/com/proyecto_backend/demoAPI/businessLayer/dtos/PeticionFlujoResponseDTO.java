package com.proyecto_backend.demoAPI.businessLayer.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Informacion sobre la Petición/Flujo Response")
public class PeticionFlujoResponseDTO {

    @Schema(description = "Id único de la Petición", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull
    private Long id;

    @Schema(description = "Nombre del usuario remitente", example = "Ana", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank
    private String remitente;

    @Schema(description = "Nombre del usuario destinatario", example = "Sara", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank
    private String destinatario;

//    @Schema(description = "Id del documento para el proceso", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
//    private Long id_documento;

    @Schema(description = "Nombre del tipo de la petición", example = "Cancelación de materia", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank
    private String tipoPeticion;

    @Schema(description = "Estado de la patición", example = "Enviado", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank
    private String estado;

    @Schema(description = "Fecha en la que se inicia la petición -> yyyy-MM-dd HH:mm:ss", example = "2026-10-27T10:15:30", accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull(message = "La fecha de inicio no puede estar vacía")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaInicio;

    @Schema(description = "Fecha de vigencia de la petición -> yyyy-MM-dd", example = "2026-10-27", accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull(message = "La fecha de fin no puede estar vacía")
    @JsonFormat(pattern = "yyyy-MM-dd")
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
