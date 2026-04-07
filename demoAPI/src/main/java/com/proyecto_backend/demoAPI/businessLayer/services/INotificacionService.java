package com.proyecto_backend.demoAPI.businessLayer.services;

import java.util.List;
import com.proyecto_backend.demoAPI.businessLayer.dtos.NotificacionCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.NotificacionDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.NotificacionUpdateDTO;

public interface INotificacionService {
    
    // Metodo para guardar una notificacion:
    NotificacionDTO guardarNotificacion (NotificacionCreateDTO dto);

    // Metodo para retornar todas las notificaciones:
    List<NotificacionDTO> listaNotificaciones ();

    // Metodo para buscar una notificacion por id:
    NotificacionDTO buscarPorId (Long idNotificacion);

    // Metodo para buscar notificaciones por titulo:
    List<NotificacionDTO> listaPorTitulo (String titulo);

    // Metodo para buscar notificaciones por usuario:
    List<NotificacionDTO> listaPorUsuario (Long idUsuario);

    // Metodo para buscar notificaciones por leidas ó no leidas:
    List<NotificacionDTO> listaPorFueLeida (boolean fueLeida);

    // Metodo para buscar notificaciones por usuario y leidas ó no leidas:
    List<NotificacionDTO> listaPorUsuarioYFueLeida (Long idUsuario, boolean fueLeida);

    // Metodo para actualizar una notificacion:
    NotificacionDTO actualizarNotificacion (NotificacionUpdateDTO dto, Long idNotificacion);

    // Metodo para eliminar una notificacion:
    void eliminarNotificacion (Long idNotificacion);

}
