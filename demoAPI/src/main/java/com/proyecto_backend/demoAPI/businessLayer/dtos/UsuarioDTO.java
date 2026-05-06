package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de respuesta para Usuario")
public class UsuarioDTO {

    @Schema(description = "Identificador único del usuario", example = "101")
    private Long idUsuario;

    @Schema(description = "Nombre del usuario", example = "Mateo")
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "García")
    private String apellido;

    @Schema(description = "Correo electrónico del usuario", example = "mateo.garcia@email.com")
    private String correo;

    @Schema(description = "Número de celular del usuario", example = "3123456789")
    private String celular;

    @Schema(description = "Fecha de creación del usuario", example = "2026-04-13")
    private LocalDate fechaCreacion;

    @Schema(description = "Estado del usuario (activo/inactivo)", example = "true")
    private boolean estaActivo;

    @Schema(description = "Identificador de la organización a la que pertenece el usuario", example = "1")
    private Long idOrganizacion;

    @Schema(description = "Nombre de la organización a la que pertenece el usuario", example = "Universidad del Quindío")
    private String nombreOrganizacion;

    @Schema(description = "Identificador del rol asignado al usuario", example = "2")
    private Long idRol;

    @Schema(description = "Nombre del rol asignado al usuario", example = "Administrador")
    private String nombreRol;

}