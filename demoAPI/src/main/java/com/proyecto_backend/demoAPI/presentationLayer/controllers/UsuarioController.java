package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioUpdateContrasenaDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IUsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Operaciones CRUD para gestión de usuarios")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Crear usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Correo ya registrado")
    })
    public ResponseEntity<UsuarioDTO> guardarUsuario(
            @Parameter(description = "Datos del usuario a crear", required = true) @Valid @RequestBody UsuarioCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardarUsuario(dto));
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Obtiene la lista completa de usuarios registrados")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
    public ResponseEntity<List<UsuarioDTO>> listaUsuarios() {
        return ResponseEntity.ok(usuarioService.listaUsuarios());
    }

    @GetMapping("/{idUsuario}")
    @Operation(summary = "Buscar usuario por ID", description = "Obtiene la información de un usuario específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorId(
            @Parameter(description = "ID del usuario", required = true, example = "1") @PathVariable Long idUsuario) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(idUsuario));
    }

    @GetMapping("/correo/{correoUsuario}")
    @Operation(summary = "Buscar usuario por correo", description = "Obtiene un usuario a partir de su correo electrónico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "400", description = "Correo inválido"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorCorreo(
            @Parameter(description = "Correo del usuario", required = true, example = "usuario@correo.com") @PathVariable String correoUsuario) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorCorreo(correoUsuario));
    }

    @GetMapping("/login/{correoUsuario}/{contrasenaUsuario}")
    @Operation(summary = "Login de usuario", description = "Valida las credenciales de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso"),
            @ApiResponse(responseCode = "400", description = "Correo o contraseña inválidos"),
            @ApiResponse(responseCode = "401", description = "Contraseña incorrecta"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioDTO> loginUsuario(
            @Parameter(description = "Correo del usuario", required = true) @PathVariable String correoUsuario,
            @Parameter(description = "Contraseña del usuario", required = true) @PathVariable String contrasenaUsuario) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorCorreoYContrasena(correoUsuario, contrasenaUsuario));
    }

    @GetMapping("/organizacion/{idOrganizacion}")
    @Operation(summary = "Listar usuarios por organización", description = "Obtiene la lista de usuarios asociados a una organización")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Organización no encontrada")
    })
    public ResponseEntity<List<UsuarioDTO>> listaUsuariosPorOrganizacion(
            @Parameter(description = "ID de la organización", required = true, example = "10") @PathVariable Long idOrganizacion) {
        return ResponseEntity.ok(usuarioService.listaUsuariosPorOrganizacion(idOrganizacion));
    }

    @PutMapping("/{idUsuario}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza la información de un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioDTO> actualizarUsuario(
            @Parameter(description = "Datos actualizados del usuario", required = true) @Valid @RequestBody UsuarioUpdateDTO dto,
            @Parameter(description = "ID del usuario a actualizar", required = true, example = "1") @PathVariable Long idUsuario) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(dto, idUsuario));
    }

    @PutMapping("/contrasena")
    @Operation(summary = "Actualizar contraseña", description = "Actualiza la contraseña de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contraseña actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o contraseñas no coinciden"),
            @ApiResponse(responseCode = "401", description = "Contraseña actual incorrecta"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioDTO> actualizarContrasena(
            @Parameter(description = "Datos para actualizar la contraseña", required = true) @Valid @RequestBody UsuarioUpdateContrasenaDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizarContrasena(dto));
    }

    @DeleteMapping("/{idUsuario}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> eliminarUsuario(
            @Parameter(description = "ID del usuario a eliminar", required = true, example = "1") @PathVariable Long idUsuario) {
        usuarioService.eliminarUsuario(idUsuario);
        return ResponseEntity.noContent().build();
    }

}