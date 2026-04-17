package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IEstadoPeticionFlujoService;
import com.proyecto_backend.demoAPI.exceptions.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/estado-peticion")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "EstadoPeticion", description = "Operaciones CRUD para gestión de Estados de las peticiones")
@CrossOrigin(origins = "*")
public class EstadoPeticionFlujoController {
    private final IEstadoPeticionFlujoService estadoService;

    // CREATE
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
            @Parameter(description = "Datos del Estado a crear (id debe ser null)", required = true)
            @Valid @RequestBody EstadoPeticionFlujoCreateUpdateDTO createDTO) {

        log.info("POST /estado-peticion - Creando estado {}", createDTO.getNombre());
        EstadoPeticionFlujoResponseDTO created = estadoService.createEstado(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener Estado por ID",
            description = "Obtiene un Estado de Petición Flujo con el Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado encontrado",
                    content = @Content(schema = @Schema(implementation = EstadoPeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Id inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<EstadoPeticionFlujoResponseDTO> getEstadoById(
            @Parameter(description = "ID del Estado que desea buscar", required = true, example = "1")
            @PathVariable Long id) {

        log.info("GET /estado-peticion/{id} - Buscando estado por el id {}", id);
        return ResponseEntity.ok(estadoService.getEstadoById(id));
    }

    // GET BY NOMBRE
    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Obtener Estado por nombre",
            description = "Obtiene un Estado de Petición Flujo con el Nombre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado encontrado",
                    content = @Content(schema = @Schema(implementation = EstadoPeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Nombre inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<EstadoPeticionFlujoResponseDTO> getEstadoByNombre(
            @Parameter(description = "Nombre del Estado que desea buscar", required = true, example = "ENVIADO")
            @PathVariable String nombre) {

        log.info("GET /estado-peticion/{nombre} - Buscando estado por el nombre{}", nombre);
        return ResponseEntity.ok(estadoService.getEstadoByNombre(nombre));
    }

    // GET ALL
    @GetMapping
    @Operation(summary = "Listar Estados",
            description = "Obtiene todos los Estados de Petición Flujo existentes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de estados",
                    content = @Content(schema = @Schema(implementation = EstadoPeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<EstadoPeticionFlujoResponseDTO>> getAllEstados() {

        log.info("GET /estado-peticion - Buscando todos los estados");
        return ResponseEntity.ok(estadoService.getAllEstados());
    }

    // UPDATE
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Estado",
            description = "Actualiza un Estado de Petición Flujo con el Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado",
                    content = @Content(schema = @Schema(implementation = EstadoPeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Nombre duplicado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<EstadoPeticionFlujoResponseDTO> updateEstado(
            @Parameter(description = "Id del Estado que desea actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos a actualizar, no puede haber null", required = true)
            @Valid @RequestBody EstadoPeticionFlujoCreateUpdateDTO updateDTO) {

        log.info("PUT /estado-peticion/{id} - Actualizando el estado con id {} ", id);
        return ResponseEntity.ok(estadoService.updateEstado(id, updateDTO));
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Estado",
            description = "Elimina un Estado de Petición Flujo con el Id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estado eliminado"),
            @ApiResponse(responseCode = "400", description = "Id inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> deleteEstado(
            @Parameter(description = "Id del Estado que desea eliminar", required = true, example = "1")
            @PathVariable Long id) {

        log.info("DELETE /estado-peticion/{id} - Eliminando el estado con id {} ", id);
        estadoService.deleteEstado(id);
        return ResponseEntity.noContent().build();
    }
}
