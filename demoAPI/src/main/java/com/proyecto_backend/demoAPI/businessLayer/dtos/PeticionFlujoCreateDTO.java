package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dto para crear PeticionFlujoEntity")
public class PeticionFlujoCreateDTO {

    //Se debe validar con jwt authentication. No se envia en dto
    @Schema(description = "Id del usuario remitente", example = "5")
    @NotNull(message = "EL id del remitente no puede ser nulo")
    private Long remitente;

    @Schema(description = "Id del usuario destinatario", example = "5")
    @NotNull(message = "EL id del destinatario no puede ser nulo")
    private Long destinatario;

    @Schema(description = "Id del tipo de la petición", example = "Cancelación de materia", accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull(message = "EL tipo de petición no puede ser nulo")
    private Long tipoPeticion;

    @Schema(description = "Fecha de vigencia de la petición -> yyyy-MM-dd", example = "2026-10-27", accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull(message = "La fecha de fin no puede estar vacía")
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
