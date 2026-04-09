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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO Para crear una nueva version de documento")
public class VersionDocumentoCreateDTO {

    @Schema(description = "Nombre del documento sobre el que se genera una nueva version", example = "Informe epidemiológicos semanal - 10262026", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank(message = "El nombre del documento no puede estar vacio")
    private String documento;

    @Schema(description = "Nombre del usuario actualizador del documento", example = "Juan", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank(message = "El nombre del usuario no puede estar vacio")
    private String usuarioActualizador;

    @Schema(description = "Nombre de la version del documento", example = "Informe epidemiológicos semanal - 10262026 V2")
    @NotBlank(message = "El nombre de la version documento no puede estar vacio")
    @Size(max = 20, message = "Debe tener máximo 20 caracteres")
    private String nombre;

    @Schema(description = "Url de la version del documento en la nube", example = "https://storage.salud.gov.co/documentos/expedientes/12345/versiones/1.0/documento.pdf")
    @NotBlank(message = "La URL de la version documento no puede estar vacia")
    @Size(max = 255, message = "Debe tener máximo 255 caracteres")
    private String archivoUrl;

    @Schema(description = "Descripcion de la version del documento", example = "Se implementaron datos estadisticos segun el informe presentado")
    @NotBlank(message = "La descripcion de la version documento no puede estar vacia")
    @Size(max = 250, message = "Debe tener máximo 250 caracteres")
    private String descripcion;

    @Schema(description = "Fecha de creacion de la version del documento -> yyyy-MM-dd", example = "2026-10-27", accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull(message = "La fecha no puede estar vacía")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaActualizacion;
}
