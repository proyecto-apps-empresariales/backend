package com.proyecto_backend.demoAPI.persistenceLayer.mappers;

import java.util.List;

import com.proyecto_backend.demoAPI.businessLayer.dtos.NotificacionCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.NotificacionDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.NotificacionUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Notificacion;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;

public class NotificacionMapper {
    
    // Metodo para convertir una clase NotificacionCreateDTO -> Notificacion:
    public static Notificacion toEntity (NotificacionCreateDTO dto) {

        Notificacion notificacion = new Notificacion();

        notificacion.setTitulo(dto.getTitulo());
        notificacion.setMensaje(dto.getMensaje());

        return notificacion;

    }

    // Metodo para convertir una clase Notificacion -> NotificacionDTO:
    public static NotificacionDTO toDTO (Notificacion notificacion) {

        if (notificacion == null) {
            return null;
        }

        Long idUsuario = notificacion.getUsuario() == null ? null : notificacion.getUsuario().getIdUsuario();
        String nombreUsuario = notificacion.getUsuario() == null ? null : notificacion.getUsuario().getNombre();
        String correoUsuario = notificacion.getUsuario() == null ? null : notificacion.getUsuario().getCorreo();

        NotificacionDTO dto = new NotificacionDTO();
        
        dto.setIdNotificacion(notificacion.getIdNotificacion());
        dto.setTitulo(notificacion.getTitulo());
        dto.setMensaje(notificacion.getMensaje());
        dto.setFecha(notificacion.getFecha());
        dto.setFueLeida(notificacion.isFueLeida());
        dto.setIdUsuario(idUsuario);
        dto.setNombreUsuario(nombreUsuario);
        dto.setCorreoUsuario(correoUsuario);

        return dto;

    }

    // Metodo para actualizar una Notificacion con la clase NotificacionUpdateDTO:
    public static void updateEntityFromDTO (NotificacionUpdateDTO dto, Notificacion notificacion, Usuario usuario) {

        if (dto.getTitulo() != null) {
            notificacion.setTitulo(dto.getTitulo());
        }
        if (dto.getMensaje() != null) {
            notificacion.setMensaje(dto.getMensaje());
        }
        if (dto.getFueLeida() != null) {
            notificacion.setFueLeida(dto.getFueLeida());
        }
        if (usuario != null) {
            notificacion.setUsuario(usuario);
        }

    }

    // Metodo para convertir una lista de Notificacion -> NotificacionDTO:
    public static List<NotificacionDTO> toListDTO (List<Notificacion> notificaciones) {

        return notificaciones.stream().map(NotificacionMapper::toDTO).toList();

    }



}
