package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.AuthRequest;
import com.proyecto_backend.demoAPI.businessLayer.dtos.AuthResponse;
import com.proyecto_backend.demoAPI.businessLayer.services.AuthService;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.UsuarioDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;
import com.proyecto_backend.demoAPI.security.jwt.JwtService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioDAO usuarioDAO;

    // Implementación del método de autenticación
    @Override
    @Transactional
    public AuthResponse login(AuthRequest authRequest) {
        
        String loginKey = authRequest.correo().trim().toLowerCase(Locale.ROOT);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginKey, authRequest.contrasena())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Usuario user = usuarioDAO.buscarUsuarioEntidadPorCorreo(userDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("Usuario autenticado no encontrado en persistencia"));

        String token = jwtService.generateToken(userDetails);
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new AuthResponse(
                token,
                "Bearer",
                jwtService.getJwtExpirationSeconds(),
                user.getIdUsuario(),
                user.getNombre(),
                user.getCorreo(),
                user.getApellido(),
                user.getCelular(),
                user.getOrganizacion() != null ? user.getOrganizacion().getNombre() : null,
                roles,  // ← usar la lista de roles que ya tienes arriba
                user.isEstaActivo()
        );

    }
    
}
