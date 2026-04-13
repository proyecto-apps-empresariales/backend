package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor 
@NoArgsConstructor
@Schema(description = "DTO para la creación de un Usuario")
public class UsuarioCreateDTO {

    @Schema(description = "Nombre del usuario", example = "Mateo")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 50, message = "Debe tener entre 3 y 50 caracteres")
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "García")
    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 3, max = 50, message = "Debe tener entre 3 y 50 caracteres")
    private String apellido;

    @Schema(description = "Correo electrónico del usuario", example = "mateo.garcia@email.com")
    @Email(message = "Debe ser un correo válido")
    @NotBlank(message = "El correo no puede estar vacío")
    @Size(min = 3, max = 50, message = "Debe tener entre 3 y 50 caracteres")
    private String correo;

    @Schema(description = "Contraseña del usuario", example = "MiC0ntr@s3n@Segura")
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 3, max = 250, message = "Debe tener entre 3 y 250 caracteres")
    private String contrasena;

    @Schema(description = "Número de celular del usuario", example = "3123456789")
    @NotBlank(message = "El celular no puede estar vacío")
    @Size(min = 3, max = 10, message = "Debe tener entre 3 y 10 caracteres")
    private String celular;

    @Schema(description = "Identificador de la organización a la que pertenece el usuario", example = "1")
    @NotNull(message = "El id de organización no puede ser nulo")
    @Positive(message = "El id de organización debe ser mayor a 0")
    private Long idOrganizacion;

    @Schema(description = "Identificador del rol asignado al usuario", example = "2")
    @NotNull(message = "El id de rol no puede ser nulo")
    @Positive(message = "El id de rol debe ser mayor a 0")
    private Long idRol;

}