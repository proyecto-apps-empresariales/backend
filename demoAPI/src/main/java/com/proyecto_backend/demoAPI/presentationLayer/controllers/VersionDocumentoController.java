package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.DocumentoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.VersionDocumentoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.VersionDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.VersionDocumentoUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IVersionDocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/versiones")
@RequiredArgsConstructor
public class VersionDocumentoController {

    private final IVersionDocumentoService versionDocumentoService;

    //Crear version de documento
    @PostMapping("/crear")
    public ResponseEntity<VersionDocumentoResponseDTO> createVersion(@RequestBody VersionDocumentoCreateDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(versionDocumentoService.createVersion(dto));
    }

    //Obtener todas las versiones de un documento
    @GetMapping("/doc/{idDocumento}")
    public ResponseEntity<List<VersionDocumentoResponseDTO>> getAllVersiones(@PathVariable Long idDocumento){
        return ResponseEntity.ok(versionDocumentoService.getVersionesByDocumento(idDocumento));
    }

    //Obtener version por id
    @GetMapping("/{idVersion}")
    public ResponseEntity<VersionDocumentoResponseDTO> getVersionById(@PathVariable Long idVersion){
        return ResponseEntity.ok(versionDocumentoService.getVersionById(idVersion));
    }

    //Editar version
    @PatchMapping("/editar/{idVersion}")
    public ResponseEntity<VersionDocumentoResponseDTO> updateVersion(@PathVariable Long idVersion, @RequestBody VersionDocumentoUpdateDTO dto){
        return ResponseEntity.ok(versionDocumentoService.updateVersion(idVersion,dto));
    }

    //Endpoint para borrar version
    @DeleteMapping("/borrar/{idVersion}")
    public void deleteVersion(@PathVariable Long idVersion){
        versionDocumentoService.deleteVersion(idVersion);
    }
}
