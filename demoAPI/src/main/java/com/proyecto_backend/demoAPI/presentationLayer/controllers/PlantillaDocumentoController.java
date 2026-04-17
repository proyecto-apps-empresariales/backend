package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PlantillaDocumentoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoDocumentoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PlantillaDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IPlantillaDocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plantillaDocumento")
@RequiredArgsConstructor
public class PlantillaDocumentoController {

    private final IPlantillaDocumentoService plantillaDocumentoService;

    //Endpoint para crear plantilla documento
    @PostMapping("/crear")
    public ResponseEntity<PlantillaDocumentoResponseDTO> createPlantillaDocumento(@RequestBody PlantillaDocumentoCreateUpdateDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(plantillaDocumentoService.createPlantilla(dto));
    }

    //Endpoint para obtener plantilla documento por id
    @GetMapping("/{idPlantillaDocumento}")
    public ResponseEntity<PlantillaDocumentoResponseDTO> getPlantillaDocumentoById(@PathVariable Long idPlantillaDocumento){
        return ResponseEntity.ok(plantillaDocumentoService.getPlantillaById(idPlantillaDocumento));
    }

    //Endpoint para obtener todas las plantillas documento
    @GetMapping
    public ResponseEntity<List<PlantillaDocumentoResponseDTO>> getAllPlantillaDocumentos(){
        return ResponseEntity.ok(plantillaDocumentoService.getAllPlantillas());
    }

    //Endpoint para editar plantilla documento
    @PatchMapping("/update/{idPlantillaDocumento}")
    public ResponseEntity<PlantillaDocumentoResponseDTO> updateDocumento(@PathVariable Long idPlantillaDocumento,@RequestBody PlantillaDocumentoCreateUpdateDTO dto){
        return ResponseEntity.ok(plantillaDocumentoService.updatePlantilla(idPlantillaDocumento, dto));
    }

}
