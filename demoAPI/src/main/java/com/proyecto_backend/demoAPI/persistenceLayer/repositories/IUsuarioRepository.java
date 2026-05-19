package com.proyecto_backend.demoAPI.persistenceLayer.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    // Metodo para buscar usuarios por una organizacion:
    List<Usuario> findByOrganizacion_IdOrganizacion (Long idOrganizacion);

    // Metodo para buscar un usuario por correo:
    Optional<Usuario> findByCorreo (String correo);

    // Metodo para buscar un usuario por correo, ignorando mayusculas y minusculas:
    Optional<Usuario> findByCorreoIgnoreCase (String correo);

    // Metodo para buscar un usuario por nombre:
    Optional<Usuario> findByNombre (String nombre);
    
}
