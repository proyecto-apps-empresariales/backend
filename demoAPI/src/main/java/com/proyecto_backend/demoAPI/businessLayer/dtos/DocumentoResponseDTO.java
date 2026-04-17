package com.proyecto_backend.demoAPI.businessLayer.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoDocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Informacion sobre el documento")
public class DocumentoResponseDTO {

    @Schema(description = "Id único del documento", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull(message = "El id no puede estar vacio")
    private long id;

    @Schema(description = "Nombre del usuario creador del documento", example = "Juan", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank(message = "El nombre del usario creador no puede estar vacio")
    private String usuarioCreador;

    @Schema(description = "Nombre del tipo de documento que usa el documento", example = "Registros de sanciones sanitarias", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank(message = "El nombre del tipo de documento no puede estar vacio")
    private String tipoDocumento;

    @Schema(description = "Nombre del documento", example = "Informe epidemiológicos semanal - 10262026")
    @NotBlank(message = "El nombre del documento no puede estar vacio")
    @Size(max = 50, message = "Debe tener máximo 50 caracteres")
    private String nombre;

    @Schema(description = "Descripcion del documento", example = "Informe epidemiológicos de la semana 2 del 10262026")
    @NotBlank(message = "La descripcion del documento no puede estar vacio")
    @Size(max = 250, message = "Debe tener máximo 250 caracteres")
    private String descripcion;

    @Schema(description = "Fecha de creacion del documento -> yyyy-MM-dd", example = "2026-10-27", accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull(message = "La fecha no puede estar vacía")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaCreacion;

    @Schema(description = "Versiones del documento asociadas", example = "V2, V3")
    @NotEmpty(message = "La lista no puede estar vacía")
    private List<VersionDocumentoResponseDTO> versiones;
}
