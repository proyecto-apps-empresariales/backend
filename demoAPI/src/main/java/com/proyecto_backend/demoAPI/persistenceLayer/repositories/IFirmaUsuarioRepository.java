package com.proyecto_backend.demoAPI.persistenceLayer.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto_backend.demoAPI.persistenceLayer.entities.FirmaUsuario;

@Repository
public interface IFirmaUsuarioRepository extends JpaRepository<FirmaUsuario, Long> {

    // Metodo para buscar una firma por archivoFirma:
    Optional<FirmaUsuario> findByArchivoFirma (String archivoFirma);

    // Metodo para buscar firmas por un usuario:
    List<FirmaUsuario> findByUsuario_IdUsuario (Long idUsuario);
    
}
