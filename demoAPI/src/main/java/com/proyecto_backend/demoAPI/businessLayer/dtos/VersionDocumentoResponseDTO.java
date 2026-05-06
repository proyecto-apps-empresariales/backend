package com.proyecto_backend.demoAPI.businessLayer.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.DocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Informacion de version de documento")
public class VersionDocumentoResponseDTO {

    @Schema(description = "Id único de la version de documento", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull(message = "El id no puede estar vacio")
    private Long id;

    @Schema(description = "Nombre del documento sobre el que se genera una nueva version", example = "Informe epidemiológicos semanal - 10262026", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank(message = "El nombre del documento no puede estar vacio")
    private String documento;

    @Schema(description = "Correo del usuario actualizador del documento", example = "Juan", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank(message = "El correo del usuario no puede estar vacio")
    private String usuarioActualizador;

    @Schema(description = "Nombre de la version del documento", example = "Informe- 10262026 V2")
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
