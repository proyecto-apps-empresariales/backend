package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaPeticionFlujoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IFirmaPeticionFlujoService;
import com.proyecto_backend.demoAPI.exceptions.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/firma-peticion")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Firmas en la Petición", description = "Operaciones CRUD para las firmas de peticiones")
@CrossOrigin(origins = "*")
public class FirmaPeticionFlujoController {

    private final IFirmaPeticionFlujoService firmaService;

    // CREATE
    @Operation(summary = "Crear firma de petición",
            description = "Registra la firma de un usuario sobre una petición")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Firma creada correctamente",
                    content = @Content(schema = @Schema(implementation = FirmaPeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "El usuario ya firmó esta petición",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping
    public ResponseEntity<FirmaPeticionFlujoResponseDTO> createFirma(
            @Parameter(description = "Datos la firma que aparecerá en la petición (id debe ser null)", required = true)
            @Valid @RequestBody FirmaPeticionFlujoCreateDTO createDTO) {

        log.info("POST /firma-peticion - Creando firma para petición {}", createDTO.getPeticion());
        FirmaPeticionFlujoResponseDTO created = firmaService.createFirmaPeticion(createDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET BY ID
    @Operation(summary = "Obtener firma por ID",
            description = "Busca la firma de un usuario sobre una petición")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Firma encontrada",
                    content = @Content(schema = @Schema(implementation = FirmaPeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Firma no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<FirmaPeticionFlujoResponseDTO> getById(
            @Parameter(description = "ID de la firma", required = true, example = "1")
            @PathVariable Long id) {

        log.info("GET /firma-peticion/{} - Buscando firma por ID", id);
        return ResponseEntity.ok(firmaService.getFirmaPeticionById(id));
    }

    // GET ALL
    @Operation(summary = "Obtener todas las firmas",
            description = "Busca todas las firmas usadas en peticiones")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de firmas obtenido",
                    content = @Content(schema = @Schema(implementation = FirmaPeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping
    public ResponseEntity<List<FirmaPeticionFlujoResponseDTO>> getAll() {

        log.info("GET /firma-peticion - Obteniendo todas las firmas");
        return ResponseEntity.ok(firmaService.getAllFirmaPeticion());
    }

    // GET BY USUARIO
    @Operation(summary = "Obtener firmas por usuario",
            description = "Lista todas las firmas realizadas por un usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Firmas encontradas",
                    content = @Content(schema = @Schema(implementation = FirmaPeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<FirmaPeticionFlujoResponseDTO>> getByUsuarioId(
            @Parameter(description = "ID del usuario", required = true, example = "1")
            @PathVariable Long id) {

        log.info("GET /firma-peticion/usuario/{} - Firmas por usuario", id);
        return ResponseEntity.ok(firmaService.getAllFirmaPeticionByUsuarioId(id));
    }

    // GET BY PETICION
    @Operation(summary = "Obtener firmas por petición",
            description = "Lista todas las firmas asociadas a una petición")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Firmas encontradas",
                    content = @Content(schema = @Schema(implementation = FirmaPeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/peticion/{id}")
    public ResponseEntity<List<FirmaPeticionFlujoResponseDTO>> getByPeticionId(
            @Parameter(description = "ID de la petición", required = true, example = "1")
            @PathVariable Long id) {

        log.info("GET /firma-peticion/peticion/{} - Firmas por petición", id);
        return ResponseEntity.ok(firmaService.getAllByPeticionId(id));
    }

    // DELETE
    @Operation(summary = "Eliminar firma",
            description = "Elimina de una petición la Firma por el id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Firma eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Firma no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFirma(
            @Parameter(description = "ID de la firma", required = true, example = "1")
            @PathVariable Long id) {

        log.info("DELETE /firma-peticion/{} - Eliminando firma", id);
        firmaService.deleteFirmaPeticion(id);
        return ResponseEntity.noContent().build();
    }
}
