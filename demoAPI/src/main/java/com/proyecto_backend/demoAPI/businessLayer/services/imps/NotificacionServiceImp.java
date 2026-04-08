package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto_backend.demoAPI.businessLayer.dtos.NotificacionCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.NotificacionDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.NotificacionUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.INotificacionService;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.NotificacionDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.UsuarioDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificacionServiceImp implements INotificacionService {

    // Inyectamos el NotificacionDAO y el UsuarioDAO::
    private final NotificacionDAO notificacionDAO;
    private final UsuarioDAO usuarioDAO;

    // Metodo para guardar una notificacion:
    @Override
    public NotificacionDTO guardarNotificacion(NotificacionCreateDTO dto) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no validos");
        }

        Usuario usuario = buscarUsuarioId(dto.getIdUsuario());

        return notificacionDAO.guardarNotificacion(dto, usuario);

    }

    // Metodo para retornar todas las notificaciones:
    @Override
    public List<NotificacionDTO> listaNotificaciones() {

        return notificacionDAO.listaNotificaciones();

    }

    // Metodo para buscar una notificacion por id:
    @Override
    public NotificacionDTO buscarPorId(Long idNotificacion) {

        if (idNotificacion == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        return notificacionDAO.buscarPorId(idNotificacion)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificacion no encontrada"));

    }

    // Metodo para buscar notificaciones por titulo:
    @Override
    public List<NotificacionDTO> listaPorTitulo(String titulo) {

        if (titulo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Titulo no valido");
        }

        return notificacionDAO.listaPorTitulo(titulo);

    }

    // Metodo para buscar notificaciones por usuario:
    @Override
    public List<NotificacionDTO> listaPorUsuario(Long idUsuario) {

        if (idUsuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID de usuario no valido");
        }

        return notificacionDAO.listaPorUsuario(idUsuario);

    }

    // Metodo para buscar notificaciones por leidas ó no leidas:
    @Override
    public List<NotificacionDTO> listaPorFueLeida(boolean fueLeida) {

        return notificacionDAO.listaPorFueLeida(fueLeida);

    }

    // Metodo para buscar notificaciones por usuario y leidas ó no leidas:
    @Override
    public List<NotificacionDTO> listaPorUsuarioYFueLeida(Long idUsuario, boolean fueLeida) {

        if (idUsuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID de usuario no valido");
        }

        buscarUsuarioId(idUsuario);

        return notificacionDAO.listaPorUsuarioYFueLeida(idUsuario, fueLeida);

    }

    // Metodo para actualizar una notificacion:
    @Override
    public NotificacionDTO actualizarNotificacion(NotificacionUpdateDTO dto, Long idNotificacion) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no validos");
        }

        if (idNotificacion == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        Usuario usuario = buscarUsuarioId(dto.getIdUsuario() == null ? null : dto.getIdUsuario());

        return notificacionDAO.actualizarNotificacion(dto, idNotificacion, usuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificacion no encontrada"));

    }

    // Metodo para eliminar una notificacion:
    @Override
    public void eliminarNotificacion(Long idNotificacion) {

        if (idNotificacion == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        boolean eliminado = notificacionDAO.eliminarNotificacion(idNotificacion);

        if (eliminado == false) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificacion no encontrada");
        }

    }

    // Metodo privado para buscar un usuario por id:
    private Usuario buscarUsuarioId(Long idUsuario) {

        if (idUsuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        return usuarioDAO.buscarUsuarioEntidadPorId(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

    }

}
