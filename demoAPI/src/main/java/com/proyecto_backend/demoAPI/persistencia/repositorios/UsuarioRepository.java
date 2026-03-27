package com.proyecto_backend.demoAPI.persistencia.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto_backend.demoAPI.persistencia.entidades.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Metodo para buscar usuarios por una organizacion:
    public List<Usuario> findByOrganizacion_IdOrganizacion (Long idOrganizacion);

    // Metodo para buscar un usuario por correo:
    public Optional<Usuario> findByCorreo (String correo);
    
}
