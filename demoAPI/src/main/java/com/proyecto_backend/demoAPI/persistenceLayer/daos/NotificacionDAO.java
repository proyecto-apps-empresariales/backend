package com.proyecto_backend.demoAPI.persistenceLayer.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.proyecto_backend.demoAPI.businessLayer.dtos.NotificacionCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.NotificacionDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.NotificacionUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Notificacion;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.NotificacionMapper;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.INotificacionRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificacionDAO {
    
    // Inyectamos el repositorio INotificacionRepository:
    private final INotificacionRepository notificacionRepository;

    // Metodo para guardar una notificacion:
    public NotificacionDTO guardarNotificacion (NotificacionCreateDTO dto, Usuario usuario) {

        Notificacion notificacion = NotificacionMapper.toEntity(dto);
        notificacion.setUsuario(usuario);

        return NotificacionMapper.toDTO(notificacionRepository.save(notificacion));

    }

    // Metodo para retornar todas las notificaciones:
    public List<NotificacionDTO> listaNotificaciones () {

        return NotificacionMapper.toListDTO(notificacionRepository.findAll());

    }

    // Metodo para buscar una notificacion por id:
    public Optional<NotificacionDTO> buscarPorId (Long idNotificacion) {

        return notificacionRepository.findById(idNotificacion).map(NotificacionMapper::toDTO);

    }

    // Metodo para buscar notificaciones por titulo:
    public List<NotificacionDTO> listaPorTitulo (String titulo) {

        return NotificacionMapper.toListDTO(notificacionRepository.findByTitulo(titulo));

    }

    // Metodo para buscar notificaciones por usuario:
    public List<NotificacionDTO> listaPorUsuario (Long idUsuario) {

        return NotificacionMapper.toListDTO(notificacionRepository.findByUsuario_IdUsuario(idUsuario));

    }

    // Metodo para buscar notificaciones por leidas ó no leidas:
    public List<NotificacionDTO> listaPorFueLeida (boolean fueLeida) {

        return NotificacionMapper.toListDTO(notificacionRepository.findByFueLeida(fueLeida));

    }

    // Metodo para buscar notificaciones por usuario y leidas ó no leidas:
    public List<NotificacionDTO> listaPorUsuarioYFueLeida (Long idUsuario, boolean fueLeida) {

        return NotificacionMapper.toListDTO(notificacionRepository.findByUsuario_IdUsuarioAndFueLeida(idUsuario, fueLeida));

    } 

    // Metodo para actualizar una notificacion:
    public Optional<NotificacionDTO> actualizarNotificacion (NotificacionUpdateDTO dto, Long idNotificacion, Usuario usuario) {

        return notificacionRepository.findById(idNotificacion).map(notificacion -> {
            NotificacionMapper.updateEntityFromDTO(dto, notificacion, usuario);
            return NotificacionMapper.toDTO(notificacionRepository.save(notificacion));
        });
        
    }

    // Metodo para eliminar una notificacion:
    public boolean eliminarNotificacion (Long idNotificacion) {
        
        if (notificacionRepository.existsById(idNotificacion)) {
            notificacionRepository.deleteById(idNotificacion);
            return true;
        }
        return false;
        
    }

    

}
