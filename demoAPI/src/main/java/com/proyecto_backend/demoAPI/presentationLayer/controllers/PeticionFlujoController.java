package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IPeticionFlujoService;
import com.proyecto_backend.demoAPI.exceptions.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/peticion")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Petición", description = "Operaciones CRUD para peticiones")
@CrossOrigin(origins = "*")
public class PeticionFlujoController {

    private final IPeticionFlujoService peticionService;

    // CREATE
    @Operation(summary = "Crear nueva petición",
            description = "Crea una nueva petición")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Petición creada",
                    content = @Content(schema = @Schema(implementation = PeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Conflicto de datos",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping
    public ResponseEntity<PeticionFlujoResponseDTO> createPeticion(
            @Parameter(description = "Datos de la Petición a crear (id debe ser null)", required = true)
            @Valid @RequestBody PeticionFlujoCreateDTO createDTO) {

        log.info("POST /peticion - Creando petición {}", createDTO.getNombre());
        PeticionFlujoResponseDTO created = peticionService.createPeticion(createDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    //GET BY ID
    @Operation(summary = "Obtener petición por ID",
            description = "Busca una petición por medio del ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Petición encontrada",
                    content = @Content(schema = @Schema(implementation = PeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Petición no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PeticionFlujoResponseDTO> getById(
            @Parameter(description = "ID de la Petición que desea buscar", required = true, example = "1")
            @PathVariable Long id) {

        log.info("GET /peticion/{id} - Buscando estado por el id {}", id);
        return ResponseEntity.ok(peticionService.getPeticionById(id));
    }

    // GET BY NOMBRE
    @Operation(summary = "Obtener petición por nombre",
            description = "Busca una petición por medio del Nombre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Petición encontrada",
                    content = @Content(schema = @Schema(implementation = PeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Nombre inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Petición no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<PeticionFlujoResponseDTO> getByNombre(
            @Parameter(description = "Nombre de Petición a buscar", required = true, example = "cancelación materia")
            @PathVariable String nombre) {

        log.info("GET /peticion/nombre/{nombre} - Buscando estado por el nombre {}", nombre);
        return ResponseEntity.ok(peticionService.getPeticionByNombre(nombre));
    }

    // GET ALL
    @Operation(summary = "Listar todas las peticiones",
            description = "Busca todas las peticiones")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido",
                    content = @Content(schema = @Schema(implementation = PeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping
    public ResponseEntity<List<PeticionFlujoResponseDTO>> getAll() {

        log.info("GET /peticion - Buscando todos los estados");
        return ResponseEntity.ok(peticionService.getAllPeticiones());
    }

    // GET BY REMITENTE
    @Operation(summary = "Listar peticiones por remitente",
            description = "Busca todas las peticiones que tiene el remitente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido",
                    content = @Content(schema = @Schema(implementation = PeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/remitente/{id}")
    public ResponseEntity<List<PeticionFlujoResponseDTO>> getByRemitente(
            @Parameter(description = "ID del remitente para buscar sus peticiones", required = true, example = "1")
            @PathVariable Long id) {

        log.info("GET /peticion/remitente/{id} - Buscando todas las peticiones del remitente de id {}", id);
        return ResponseEntity.ok(peticionService.getAllPeticionesByRemitenteId(id));
    }

    // GET BY DESTINATARIO
    @Operation(summary = "Listar peticiones por destinatario",
            description = "Busca todas las peticiones que tiene el destinatario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido",
                    content = @Content(schema = @Schema(implementation = PeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/destinatario/{id}")
    public ResponseEntity<List<PeticionFlujoResponseDTO>> getByDestinatario(
            @Parameter(description = "ID del destinatario para buscar sus peticiones", required = true, example = "1")
            @PathVariable Long id) {

        log.info("GET /peticion/destinatario/{id} - Buscando todas las peticiones del destinatario de id {}", id);
        return ResponseEntity.ok(peticionService.getAllPeticionesByDestinatarioId(id));
    }

    // UPDATE
    @Operation(summary = "Actualizar petición",
            description = "Actualiza una petición por medio del ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Petición actualizada",
                    content = @Content(schema = @Schema(implementation = PeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Petición no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Conflicto de datos",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<PeticionFlujoResponseDTO> update(
            @Parameter(description = "ID de la Petición que desea actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos de la Petición a actualizar (id y remitente deben ser null)", required = true)
            @Valid @RequestBody PeticionFlujoUpdateDTO updateDto) {

        log.info("PUT /peticion/{id} - Actualizando la petición con id {} ", id);
        return ResponseEntity.ok(peticionService.updatePeticion(id, updateDto));
    }

    // DELETE
    @Operation(summary = "Eliminar petición",
            description = "Elimina una petición por medio del ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Petición eliminada"),
            @ApiResponse(responseCode = "404", description = "Petición no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id de la Petición que desea eliminar", required = true, example = "1")
            @PathVariable Long id) {

        log.info("DELETE /peticion/{id} - Eliminando la petición con id {} ", id);
        peticionService.deletePeticion(id);
        return ResponseEntity.noContent().build();
    }

    // ENVIAR A REVISIÓN
    @Operation(summary = "Enviar petición a revisión",
            description = "Envia una petición con estado CREADO a revisión")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Petición enviada a revisión",
                    content = @Content(schema = @Schema(implementation = PeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Petición no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Cambio de estado inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PatchMapping("/{id}/revision")
    public ResponseEntity<PeticionFlujoResponseDTO> enviarRevision(
            @Parameter(description = "Id del Estado que desea enviar a revisión", required = true, example = "1")
            @PathVariable Long id) {

        log.info("PATCH /peticion/{id}/revision - Cambia a estado EN_REVISION la petición con id {} ", id);
        return ResponseEntity.ok(peticionService.enviarRevision(id));
    }

    //APROBAR
    @Operation(summary = "Aprobar petición para que sea firmada",
            description = "Aprueba una petición con estado EN_REVISION")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Petición aprobada",
                    content = @Content(schema = @Schema(implementation = PeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Petición no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Cambio de estado inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PatchMapping("/{id}/aprobar")
    public ResponseEntity<PeticionFlujoResponseDTO> aprobar(
            @Parameter(description = "Id del Estado que desea Aprobar", required = true, example = "1")
            @PathVariable Long id) {

        log.info("PATCH /peticion/{id}/aprobar - Cambia a estado APROBADO la petición con id {} ", id);
        return ResponseEntity.ok(peticionService.aprobarPeticion(id));
    }

    //RECHAZAR
    @Operation(summary = "Rechazar petición",
            description = "Se rechaza la Petición, debe estar en estado EN_REVISION")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Petición rechazada",
                    content = @Content(schema = @Schema(implementation = PeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Petición no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Cambio de estado inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PatchMapping("/{id}/rechazar")
    public ResponseEntity<PeticionFlujoResponseDTO> rechazar(
            @Parameter(description = "Id la Petición que desea Rechazar", required = true, example = "1")
            @PathVariable Long id) {

        log.info("PATCH /peticion/{id}/rechazar - Cambia a estado RECHAZADO la petición con id {} ", id);
        return ResponseEntity.ok(peticionService.rechazarPeticion(id));
    }

    //FIRMAR
    @Operation(summary = "Firmar petición",
            description = "Se firma la petición, debe estar en estado APROBADO")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Petición firmada",
                    content = @Content(schema = @Schema(implementation = PeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Petición no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Cambio de estado inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PatchMapping("/{id}/firmar")
    public ResponseEntity<PeticionFlujoResponseDTO> firmar(
            @Parameter(description = "Id la Petición que desea Firmar", required = true, example = "1")
            @PathVariable Long id) {

        log.info("PATCH /peticion/{id}/firmar - Cambia a estado FIRMADO la petición con id {} ", id);
        return ResponseEntity.ok(peticionService.firmarPeticion(id));
    }

    //FINALIZAR
    @Operation(summary = "Finalizar petición",
            description = "Se finaliza el proceso la petición se envíá al remitente original, el estado debe ser FIRMADO")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Petición finalizada",
                    content = @Content(schema = @Schema(implementation = PeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Petición no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Cambio de estado inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<PeticionFlujoResponseDTO> finalizar(
            @Parameter(description = "Id la Petición que desea Finalizar", required = true, example = "1")
            @PathVariable Long id) {

        log.info("PATCH /peticion/{id}/finalizar - Cambia a estado FINALIZADO la petición con id {} ", id);
        return ResponseEntity.ok(peticionService.finalizarPeticion(id));
    }
}
