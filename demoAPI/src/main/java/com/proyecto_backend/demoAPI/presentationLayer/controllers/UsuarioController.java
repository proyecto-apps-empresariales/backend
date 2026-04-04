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

import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    // Creamos las instancias de los servicios correspondientes:
    private final UsuarioService usuarioService;

    // Endpoint para guardar un usuario:
    @PostMapping
    public ResponseEntity<UsuarioDTO> guardarUsuario (@Valid @RequestBody UsuarioCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardarUsuario(dto));
    }

    // Endpoint para retornar la lista de usuarios:
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listaUsuarios() {
        return ResponseEntity.ok(usuarioService.listaUsuarios());
    }

    // Endpoint para buscar un usuario por id:
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorId (@PathVariable Long idUsuario) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(idUsuario));
    }

    // Endpoint para buscar un usuario por correo:
    @GetMapping("/correo/{correoUsuario}")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorCorreo (@PathVariable String correoUsuario) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorCorreo(correoUsuario));
    }

    // Endpoint para retornar lista de usuarios por organizacion:
    @GetMapping("/organizacion/{idOrganizacion}")
    public ResponseEntity<List<UsuarioDTO>> listaUsuariosPorOrganizacion (@PathVariable Long idOrganizacion) {
        return ResponseEntity.ok(usuarioService.listaUsuariosPorOrganizacion(idOrganizacion));
    }

    // Endpoint para actualizar un usuario:
    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario (@Valid @RequestBody UsuarioUpdateDTO dto,
            @PathVariable Long idUsuario) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(dto, idUsuario));
    }

    // Endpoint para eliminar un usuario:
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> eliminarUsuario (@PathVariable Long idUsuario) {
        usuarioService.eliminarUsuario(idUsuario);
        return ResponseEntity.noContent().build();
    }

}
