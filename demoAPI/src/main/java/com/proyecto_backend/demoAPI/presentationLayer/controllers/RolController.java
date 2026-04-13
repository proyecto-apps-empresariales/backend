package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto_backend.demoAPI.businessLayer.dtos.RolCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RolDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RolUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IRolService;

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
@RequestMapping("/roles")
@RequiredArgsConstructor
@Tag(name = "Roles", description = "Operaciones CRUD para gestión de roles")
public class RolController {

    private final IRolService rolService;

    @PostMapping
    @Operation(summary = "Crear rol", description = "Registra un nuevo rol en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rol creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RolDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Nombre ya registrado")
    })
    public ResponseEntity<RolDTO> guardarRol(
            @Parameter(description = "Datos del rol a crear", required = true) @Valid @RequestBody RolCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rolService.guardarRol(dto));
    }

    @GetMapping
    @Operation(summary = "Listar roles", description = "Obtiene la lista completa de roles registrados")
    @ApiResponse(responseCode = "200", description = "Lista de roles obtenida exitosamente")
    public ResponseEntity<List<RolDTO>> listaRoles() {
        return ResponseEntity.ok(rolService.listaRoles());
    }

    @GetMapping("/{idRol}")
    @Operation(summary = "Buscar rol por ID", description = "Obtiene la información de un rol específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol encontrado"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    public ResponseEntity<RolDTO> buscarRolPorId(
            @Parameter(description = "ID del rol", required = true, example = "1") @PathVariable Long idRol) {
        return ResponseEntity.ok(rolService.buscarRolPorId(idRol));
    }

    @GetMapping("/nombre/{nombreRol}")
    @Operation(summary = "Buscar rol por nombre", description = "Obtiene un rol a partir de su nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol encontrado"),
            @ApiResponse(responseCode = "400", description = "Nombre inválido"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    public ResponseEntity<RolDTO> buscarRolPorNombre(
            @Parameter(description = "Nombre del rol", required = true, example = "ADMIN") @PathVariable String nombreRol) {
        return ResponseEntity.ok(rolService.buscarRolPorNombre(nombreRol));
    }

    @PutMapping("/{idRol}")
    @Operation(summary = "Actualizar rol", description = "Actualiza la información de un rol existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o ID inválido"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @ApiResponse(responseCode = "409", description = "Nombre ya registrado")
    })
    public ResponseEntity<RolDTO> actualizarRol(
            @Parameter(description = "Datos actualizados del rol", required = true) @Valid @RequestBody RolUpdateDTO dto,
            @Parameter(description = "ID del rol a actualizar", required = true, example = "1") @PathVariable Long idRol) {
        return ResponseEntity.ok(rolService.actualizarRol(dto, idRol));
    }

    @DeleteMapping("/{idRol}")
    @Operation(summary = "Eliminar rol", description = "Elimina un rol del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rol eliminado exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    public ResponseEntity<Void> eliminarRol(
            @Parameter(description = "ID del rol a eliminar", required = true, example = "1") @PathVariable Long idRol) {
        rolService.eliminarRol(idRol);
        return ResponseEntity.noContent().build();
    }

}