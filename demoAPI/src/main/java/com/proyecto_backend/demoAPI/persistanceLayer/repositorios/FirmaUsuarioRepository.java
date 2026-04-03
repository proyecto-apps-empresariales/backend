package com.proyecto_backend.demoAPI.persistanceLayer.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto_backend.demoAPI.persistanceLayer.entidades.FirmaUsuario;

@Repository
public interface FirmaUsuarioRepository extends JpaRepository<FirmaUsuario, Long> {

    // Metodo para buscar una firma por archivoFirma:
    public Optional<FirmaUsuario> findByArchivoFirma (String archivoFirma);

    // Metodo para buscar firmas por un usuario:
    public List<FirmaUsuario> findByUsuario_IdUsuario (Long idUsuario);
    
}
