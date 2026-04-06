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

import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IFirmaUsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/firmaUsuario")
@RequiredArgsConstructor
public class FirmaUsuarioController {

    // Creamos las instancias de los servicios correspondientes:
    private final IFirmaUsuarioService firmaUsuarioService;

    // Endpoint para guardar una firmaUsuario:
    @PostMapping
    public ResponseEntity<FirmaUsuarioDTO> guardarFirmaUsuario (@Valid @RequestBody FirmaUsuarioCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(firmaUsuarioService.guardarFirmaUsuario(dto));
    }

    // Endpoint para retornar la lista de firmas:
    @GetMapping
    public ResponseEntity<List<FirmaUsuarioDTO>> listaFirmas () {
        return ResponseEntity.ok(firmaUsuarioService.listaFirmas());
    }

    // Endpoint para retornar la lista de firmas por usuario:
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<FirmaUsuarioDTO>> listaFirmasPorUsuario (@PathVariable Long idUsuario) {
        return ResponseEntity.ok(firmaUsuarioService.listaFirmasPorUsuario(idUsuario));
    }

    // Endpoint para buscar una firmaUsuario por id:
    @GetMapping("/{idFirma}")
    public ResponseEntity<FirmaUsuarioDTO> firmaUsuarioPorIdFirma (@PathVariable Long idFirma) {
        return ResponseEntity.ok(firmaUsuarioService.buscarFirmaPorIdFirma(idFirma));
    }

    // Endpoint para buscar una firmaUsuario por archivoFirma:
    @GetMapping("/archivoFirma/{archivoFirma}")
    public ResponseEntity<FirmaUsuarioDTO> firmaUsuarioPorArchivoFirma (@PathVariable String archivoFirma) {
        return ResponseEntity.ok(firmaUsuarioService.buscarFirmaPorArchivoFirma(archivoFirma));
    }

    // Endpoint para actualizar una firma:
    @PutMapping("/{idFirma}")
    public ResponseEntity<FirmaUsuarioDTO> actualizarFirma (@Valid @RequestBody FirmaUsuarioUpdateDTO dto,@PathVariable Long idFirma) {
        return ResponseEntity.ok(firmaUsuarioService.actualizarFirmaUsuario(dto, idFirma));
    }

    // Endpoint para eliminar una firma:
    @DeleteMapping("/{idFirma}")
    public ResponseEntity<Void> eliminarFirma (@PathVariable Long idFirma) {
        
        firmaUsuarioService.eliminarFirmaUsuario(idFirma);
        return ResponseEntity.noContent().build();
    }
    
}
