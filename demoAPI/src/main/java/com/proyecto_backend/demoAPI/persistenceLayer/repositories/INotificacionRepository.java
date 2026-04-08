package com.proyecto_backend.demoAPI.persistenceLayer.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto_backend.demoAPI.persistenceLayer.entities.Notificacion;

@Repository
public interface INotificacionRepository extends JpaRepository <Notificacion, Long> {
    
    // Metodo para buscar notificaciones por titulo:
    List<Notificacion> findByTitulo (String titulo);

    // Metodo para buscar notificaciones por Usuario:
    List<Notificacion> findByUsuario_IdUsuario (Long idUsuario);

    // Metodo para buscar notificaciones por leidas ó no leidas:
    List<Notificacion> findByFueLeida (boolean fueLeida);

    // Metodo para buscar notificaciones por Usuario y leidas ó no leidas:
    List<Notificacion> findByUsuario_IdUsuarioAndFueLeida (Long idUsuario, boolean fueLeida);

}
