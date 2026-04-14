package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IEstadoPeticionFlujoService;
import com.proyecto_backend.demoAPI.exceptions.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estadopeticion")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "EstadoPeticion", description = "Operaciones CRUD para gestión de Estados de las peticiones")
@CrossOrigin(origins = "*")
public class EstadoPeticionFlujoController {
    private final IEstadoPeticionFlujoService estadoService;

    // ===============================
    // CREATE
    // ===============================
    @PostMapping
    @Operation(summary = "Crear Estado",
            description = "Crea un nuevo Estado de Petición Flujo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Estado creado",
                    content = @Content(schema = @Schema(implementation = EstadoPeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Estado duplicado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<EstadoPeticionFlujoResponseDTO> createEstado(
            @Valid @RequestBody EstadoPeticionFlujoCreateUpdateDTO createDTO) {

        log.info("POST /estados - Creando estado {}", createDTO.getNombre());
        EstadoPeticionFlujoResponseDTO created = estadoService.createEstado(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ===============================
    // GET BY ID
    // ===============================
    @GetMapping("/{id}")
    @Operation(summary = "Obtener Estado por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado encontrado",
                    content = @Content(schema = @Schema(implementation = EstadoPeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Id inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<EstadoPeticionFlujoResponseDTO> getEstadoById(@PathVariable Long id) {
        return ResponseEntity.ok(estadoService.getEstadoById(id));
    }

    // ===============================
    // GET BY NOMBRE
    // ===============================
    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Obtener Estado por nombre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado encontrado",
                    content = @Content(schema = @Schema(implementation = EstadoPeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Nombre inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<EstadoPeticionFlujoResponseDTO> getEstadoByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(estadoService.getEstadoByNombre(nombre));
    }

    // ===============================
    // GET ALL
    // ===============================
    @GetMapping
    @Operation(summary = "Listar Estados")
    @ApiResponse(responseCode = "200", description = "Lista de estados",
            content = @Content(schema = @Schema(implementation = EstadoPeticionFlujoResponseDTO.class)))
    public ResponseEntity<List<EstadoPeticionFlujoResponseDTO>> getAllEstados() {
        return ResponseEntity.ok(estadoService.getAllEstados());
    }

    // ===============================
    // UPDATE
    // ===============================
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Estado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado",
                    content = @Content(schema = @Schema(implementation = EstadoPeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Nombre duplicado",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<EstadoPeticionFlujoResponseDTO> updateEstado(
            @PathVariable Long id,
            @Valid @RequestBody EstadoPeticionFlujoCreateUpdateDTO updateDTO) {

        return ResponseEntity.ok(estadoService.updateEstado(id, updateDTO));
    }

    // ===============================
    // DELETE
    // ===============================
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Estado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estado eliminado"),
            @ApiResponse(responseCode = "400", description = "Id inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> deleteEstado(@PathVariable Long id) {
        estadoService.deleteEstado(id);
        return ResponseEntity.noContent().build();
    }
}
