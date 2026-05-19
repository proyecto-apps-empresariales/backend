package com.proyecto_backend.demoAPI.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

// Servicio para manejar la generación y validación de tokens JWT
@Service
public class JwtService {
    
    // Clave secreta y tiempo de expiración para los tokens JWT, inyectados desde application.properties
    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.expiration-seconds}")
    private long jwtExpirationSeconds;

    // Método para generar un token JWT a partir de los detalles del usuario
    public String generateToken(UserDetails userDetails) {
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(jwtExpirationSeconds);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(Map.of("roles", roles))
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(getSigningKey())
                .compact();
    }

    // Método para extraer el nombre de usuario del token JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Método para validar un token JWT comparando el nombre de usuario y verificando que no haya expirado
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Método para obtener el tiempo de expiración de los tokens JWT
    public long getJwtExpirationSeconds() {
        return jwtExpirationSeconds;
    }

    // Método para extraer los roles del token JWT
    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        Object roles = claims.get("roles");
        if (roles instanceof Collection<?> collection) {
            return collection.stream().map(String::valueOf).toList();
        }
        return List.of();
    }

    // Método privado para verificar si un token JWT ha expirado
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    // Método privado para extraer cualquier reclamo del token JWT utilizando una función de resolución de reclamos
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Método privado para extraer todos los reclamos del token JWT y verificar su firma
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Método privado para obtener la clave de firma a partir de la clave secreta codificada en Base64
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
