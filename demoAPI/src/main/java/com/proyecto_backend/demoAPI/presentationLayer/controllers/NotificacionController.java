package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto_backend.demoAPI.businessLayer.dtos.NotificacionCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.NotificacionDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.NotificacionUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.INotificacionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {
    
    // Inyectamos el servicio correspondiente:
    private final INotificacionService notificacionService;

    // Endpoint para guardar una notificacion:
    @PostMapping
    public ResponseEntity<NotificacionDTO> guardarNotificacion (@Valid @RequestBody NotificacionCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionService.guardarNotificacion(dto));
    }

    // Endpoint para retornar la lista de notificaciones:
    @GetMapping
    public ResponseEntity<List<NotificacionDTO>> listaNotificaciones() {
        return ResponseEntity.ok(notificacionService.listaNotificaciones());
    }

    // Endpoint para buscar una notificacion por id:
    @GetMapping("/{idNotificacion}")
    public ResponseEntity<NotificacionDTO> buscarNotificacionPorId (@PathVariable Long idNotificacion) {
        return ResponseEntity.ok(notificacionService.buscarPorId(idNotificacion));
    }

    // Endpoint para buscar notificaciones por titulo:
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<NotificacionDTO>> listaPorTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(notificacionService.listaPorTitulo(titulo));
    }

    // Endpoint para buscar notificaciones por usuario:
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<NotificacionDTO>> listaPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(notificacionService.listaPorUsuario(idUsuario));
    }

    // Endpoint para buscar notificaciones por leidas ó no leidas:
    @GetMapping("/leida/{fueLeida}")
    public ResponseEntity<List<NotificacionDTO>> listaPorFueLeida(@PathVariable boolean fueLeida) {
        return ResponseEntity.ok(notificacionService.listaPorFueLeida(fueLeida));
    }

    // Endpoint para buscar notificaciones por usuario y leidas ó no leidas:
    @GetMapping("/usuario/{idUsuario}/leida/{fueLeida}")
    public ResponseEntity<List<NotificacionDTO>> listaPorUsuarioYFueLeida(@PathVariable Long idUsuario, @PathVariable boolean fueLeida) {
        return ResponseEntity.ok(notificacionService.listaPorUsuarioYFueLeida(idUsuario, fueLeida));
    }

    // Endpoint para actualizar una notificacion:
    @PutMapping("/{idNotificacion}")
    public ResponseEntity<NotificacionDTO> actualizarNotificacion (@Valid @RequestBody NotificacionUpdateDTO dto,
            @PathVariable Long idNotificacion) {
        return ResponseEntity.ok(notificacionService.actualizarNotificacion(dto, idNotificacion));
    }

    // Endpoint para eliminar una notificacion:
    @DeleteMapping("/{idNotificacion}")
    public ResponseEntity<Void> eliminarNotificacion (@PathVariable Long idNotificacion) {
        notificacionService.eliminarNotificacion(idNotificacion);
        return ResponseEntity.noContent().build();
    }
    
}
