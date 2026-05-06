package com.proyecto_backend.demoAPI.businessLayer.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Informacion sobre las firmas del documento")
public class FirmaPeticionFlujoResponseDTO {

    @Schema(description = "Id único de la firma del documento", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull(message = "El id no puede estar vacio")
    private long id;

    @Schema(description = "Nombre del usuario que firma", example = "Juan", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank(message = "El nombre del usuario que firma no puede estar vacio")
    private String usuarioFirmador;

    @Schema(description = "Nombre de petición la que se firma", example = "Transferencia de depertamento", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank(message = "El nombre de la peticion no puede estar vacio")
    private String peticion;

    @Schema(description = "Fecha en la que se firma -> yyyy-MM-dd", example = "2026-10-27", accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull(message = "La fecha no puede estar vacía")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime fechaFirma;

    @Schema(description = "Observación del documento", example = "Cumple con los requsitos, se firma después de revisión")
    @NotBlank(message = "La observación del documento no puede estar vacio")
    @Size(max = 250, message = "Debe tener máximo 250 caracteres")
    private String observacion;

}
