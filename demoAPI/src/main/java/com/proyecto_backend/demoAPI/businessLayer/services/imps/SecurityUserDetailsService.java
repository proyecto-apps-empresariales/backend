package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.proyecto_backend.demoAPI.persistenceLayer.daos.UsuarioDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService{

    private final UsuarioDAO usuarioDAO;

    /**
     * {@code loginOrLegacyUsername}: correo (comparación sin distinguir mayúsculas) o, si no hay coincidencia,
     * nombre de usuario exacto (compatibilidad con cuentas antiguas / scripts).
     */
    @Override
    public UserDetails loadUserByUsername(String loginOrLegacyUsername) throws UsernameNotFoundException {
        if (loginOrLegacyUsername == null || loginOrLegacyUsername.isBlank()) {
            throw new UsernameNotFoundException("Credenciales no válidas");
        }
        String trimmed = loginOrLegacyUsername.trim();

        Usuario user = usuarioDAO.buscarUsuarioPorCorreoIgnoreCase(trimmed)
                .or(() -> usuarioDAO.buscarUsuarioEntidadPorNombre(trimmed))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRol().getNombre());

        return User.builder()
                .username(user.getNombre())
                .password(user.getContrasenaHash())
                .authorities(authority)
                .disabled(!user.isEstaActivo())
                .build();
    }
    
}
