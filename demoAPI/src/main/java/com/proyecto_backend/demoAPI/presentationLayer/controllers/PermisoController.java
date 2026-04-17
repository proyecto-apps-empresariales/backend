package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IPermisoService;

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
@RequestMapping("/permisos")
@RequiredArgsConstructor
@Tag(name = "Permisos", description = "Operaciones CRUD para gestión de permisos")
public class PermisoController {

    private final IPermisoService permisoService;

    @PostMapping
    @Operation(summary = "Crear permiso", description = "Registra un nuevo permiso en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Permiso creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PermisoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Nombre ya registrado")
    })
    public ResponseEntity<PermisoDTO> guardarPermiso(
            @Parameter(description = "Datos del permiso a crear", required = true) @Valid @RequestBody PermisoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(permisoService.guardarPermiso(dto));
    }

    @GetMapping
    @Operation(summary = "Listar permisos", description = "Obtiene la lista completa de permisos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de permisos obtenida exitosamente")
    public ResponseEntity<List<PermisoDTO>> listaPermisos() {
        return ResponseEntity.ok(permisoService.listaPermisos());
    }

    @GetMapping("/{idPermiso}")
    @Operation(summary = "Buscar permiso por ID", description = "Obtiene la información de un permiso específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permiso encontrado"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
    })
    public ResponseEntity<PermisoDTO> buscarPermisoPorId(
            @Parameter(description = "ID del permiso", required = true, example = "1") @PathVariable Long idPermiso) {
        return ResponseEntity.ok(permisoService.buscarPorId(idPermiso));
    }

    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Buscar permiso por nombre", description = "Obtiene un permiso a partir de su nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permiso encontrado"),
            @ApiResponse(responseCode = "400", description = "Nombre inválido"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
    })
    public ResponseEntity<PermisoDTO> buscarPermisoPorNombre(
            @Parameter(description = "Nombre del permiso", required = true, example = "READ_USERS") @PathVariable String nombre) {
        return ResponseEntity.ok(permisoService.buscarPorNombre(nombre));
    }

    @PutMapping("/{idPermiso}")
    @Operation(summary = "Actualizar permiso", description = "Actualiza la información de un permiso existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permiso actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o ID inválido"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
    })
    public ResponseEntity<PermisoDTO> actualizarPermiso(
            @Parameter(description = "Datos actualizados del permiso", required = true) @Valid @RequestBody PermisoUpdateDTO dto,
            @Parameter(description = "ID del permiso a actualizar", required = true, example = "1") @PathVariable Long idPermiso) {
        return ResponseEntity.ok(permisoService.actualizarPermiso(dto, idPermiso));
    }

    @DeleteMapping("/{idPermiso}")
    @Operation(summary = "Eliminar permiso", description = "Elimina un permiso del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Permiso eliminado exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
    })
    public ResponseEntity<Void> eliminarPermiso(
            @Parameter(description = "ID del permiso a eliminar", required = true, example = "1") @PathVariable Long idPermiso) {
        permisoService.eliminarPermiso(idPermiso);
        return ResponseEntity.noContent().build();
    }

}