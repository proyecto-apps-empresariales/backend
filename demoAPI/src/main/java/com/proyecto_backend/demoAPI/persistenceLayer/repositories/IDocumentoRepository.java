package com.proyecto_backend.demoAPI.persistenceLayer.repositories;

import com.proyecto_backend.demoAPI.persistenceLayer.entities.DocumentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDocumentoRepository extends JpaRepository<DocumentoEntity, Long> {
}
