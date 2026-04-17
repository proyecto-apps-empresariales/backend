package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto_backend.demoAPI.businessLayer.dtos.NotificacionCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.NotificacionDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.NotificacionUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.INotificacionService;

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
@RequestMapping("/notificaciones")
@RequiredArgsConstructor
@Tag(name = "Notificaciones", description = "Operaciones CRUD para gestión de notificaciones")
public class NotificacionController {

    private final INotificacionService notificacionService;

    @PostMapping
    @Operation(summary = "Crear notificación", description = "Registra una nueva notificación en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificación creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotificacionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<NotificacionDTO> guardarNotificacion(
            @Parameter(description = "Datos de la notificación a crear", required = true) @Valid @RequestBody NotificacionCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionService.guardarNotificacion(dto));
    }

    @GetMapping
    @Operation(summary = "Listar notificaciones", description = "Obtiene la lista completa de notificaciones registradas")
    @ApiResponse(responseCode = "200", description = "Lista de notificaciones obtenida exitosamente")
    public ResponseEntity<List<NotificacionDTO>> listaNotificaciones() {
        return ResponseEntity.ok(notificacionService.listaNotificaciones());
    }

    @GetMapping("/{idNotificacion}")
    @Operation(summary = "Buscar notificación por ID", description = "Obtiene la información de una notificación específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificación encontrada"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    public ResponseEntity<NotificacionDTO> buscarNotificacionPorId(
            @Parameter(description = "ID de la notificación", required = true, example = "1") @PathVariable Long idNotificacion) {
        return ResponseEntity.ok(notificacionService.buscarPorId(idNotificacion));
    }

    @GetMapping("/titulo/{titulo}")
    @Operation(summary = "Buscar notificaciones por título", description = "Obtiene una lista de notificaciones filtradas por título")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Título inválido")
    })
    public ResponseEntity<List<NotificacionDTO>> listaPorTitulo(
            @Parameter(description = "Título de la notificación", required = true, example = "Aviso de pago") @PathVariable String titulo) {
        return ResponseEntity.ok(notificacionService.listaPorTitulo(titulo));
    }

    @GetMapping("/usuario/{idUsuario}")
    @Operation(summary = "Buscar notificaciones por usuario", description = "Obtiene una lista de notificaciones asociadas a un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID de usuario inválido")
    })
    public ResponseEntity<List<NotificacionDTO>> listaPorUsuario(
            @Parameter(description = "ID del usuario", required = true, example = "5") @PathVariable Long idUsuario) {
        return ResponseEntity.ok(notificacionService.listaPorUsuario(idUsuario));
    }

    @GetMapping("/leida/{fueLeida}")
    @Operation(summary = "Buscar notificaciones por estado de lectura", description = "Obtiene una lista de notificaciones filtradas por si fueron leídas o no")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    public ResponseEntity<List<NotificacionDTO>> listaPorFueLeida(
            @Parameter(description = "Estado de lectura", required = true, example = "true") @PathVariable boolean fueLeida) {
        return ResponseEntity.ok(notificacionService.listaPorFueLeida(fueLeida));
    }

    @GetMapping("/usuario/{idUsuario}/leida/{fueLeida}")
    @Operation(summary = "Buscar notificaciones por usuario y estado de lectura", description = "Obtiene una lista de notificaciones de un usuario filtradas por estado de lectura")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID de usuario inválido"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<List<NotificacionDTO>> listaPorUsuarioYFueLeida(
            @Parameter(description = "ID del usuario", required = true, example = "5") @PathVariable Long idUsuario,
            @Parameter(description = "Estado de lectura", required = true, example = "false") @PathVariable boolean fueLeida) {
        return ResponseEntity.ok(notificacionService.listaPorUsuarioYFueLeida(idUsuario, fueLeida));
    }

    @PutMapping("/{idNotificacion}")
    @Operation(summary = "Actualizar notificación", description = "Actualiza la información de una notificación existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificación actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o ID inválido"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<NotificacionDTO> actualizarNotificacion(
            @Parameter(description = "Datos actualizados de la notificación", required = true) @Valid @RequestBody NotificacionUpdateDTO dto,
            @Parameter(description = "ID de la notificación a actualizar", required = true, example = "1") @PathVariable Long idNotificacion) {
        return ResponseEntity.ok(notificacionService.actualizarNotificacion(dto, idNotificacion));
    }

    @DeleteMapping("/{idNotificacion}")
    @Operation(summary = "Eliminar notificación", description = "Elimina una notificación del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Notificación eliminada exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    public ResponseEntity<Void> eliminarNotificacion(
            @Parameter(description = "ID de la notificación a eliminar", required = true, example = "1") @PathVariable Long idNotificacion) {
        notificacionService.eliminarNotificacion(idNotificacion);
        return ResponseEntity.noContent().build();
    }

}