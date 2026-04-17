package com.proyecto_backend.demoAPI.persistenceLayer.repositories;

import com.proyecto_backend.demoAPI.persistenceLayer.entities.DocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoDocumentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IDocumentoRepository extends JpaRepository<DocumentoEntity, Long> {

    //Metodo para buscar por nombre
    Optional<DocumentoEntity> findByNombreIgnoreCase(String nombre);

    //Existe por nombre
    Boolean existsByNombreIgnoreCase(String nombre);

    //Buscar por usuario creador
    List<DocumentoEntity> findByUsuarioCreador_IdUsuario(Long idUsuarioCreador);

    //Buscar por tipo documento
    List<DocumentoEntity> findByTipoDocumento_Id(Long idTipoDocumento);

    //Buscar documento segun fecha de creacion
    List<DocumentoEntity> findByFechaCreacionBetween(LocalDate inicio, LocalDate fin);

}
