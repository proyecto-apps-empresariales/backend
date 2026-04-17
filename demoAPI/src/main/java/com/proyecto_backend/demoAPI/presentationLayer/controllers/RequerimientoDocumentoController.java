package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PlantillaDocumentoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PlantillaDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IPlantillaDocumentoService;
import com.proyecto_backend.demoAPI.businessLayer.services.IRequerimientoDocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requerimientoDocumento")
@RequiredArgsConstructor
public class RequerimientoDocumentoController {

    private final IRequerimientoDocumentoService requerimientoDocumentoService;

    //Endpoint para obtener plantilla documento por id
    @GetMapping("/{idRequerimiento}")
    public ResponseEntity<RequerimientoDocumentoResponseDTO> getPlantillaDocumentoById(@PathVariable Long idRequerimiento){
        return ResponseEntity.ok(requerimientoDocumentoService.getRequerimientoById(idRequerimiento));
    }

    //Endpoint para obtener todas las plantillas documento
    @GetMapping
    public ResponseEntity<List<RequerimientoDocumentoResponseDTO>> getAllPlantillaDocumentos(){
        return ResponseEntity.ok(requerimientoDocumentoService.getAllRequerimientos());
    }
}
