package com.proyecto_backend.demoAPI.security.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.proyecto_backend.demoAPI.businessLayer.services.imps.SecurityUserDetailsService;
import com.proyecto_backend.demoAPI.security.filter.JwtAuthenticationFilter;
import com.proyecto_backend.demoAPI.security.handler.SecurityExceptionHandler;

import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final SecurityUserDetailsService securityUserDetailsService;
    private final SecurityExceptionHandler securityExceptionHandler;

    @Value("${security.cors.allowed-origins}")
    private String allowedOrigins;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(securityExceptionHandler)
                        .accessDeniedHandler(securityExceptionHandler)
                )
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**",
                                "/swagger-ui/**",
                                "/api-docs/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        .requestMatchers(HttpMethod.GET, "/documentos/**", "/estado-peticion/**", "/firma-peticion/**", "/firmaUsuario/**", "/historial/**", "/notificaciones/**", "/organizaciones/**", "/permisos/**", "/peticion/**", "/plantillaDocumento/**", "/requerimientoDocumento/**", "/requerimiento-peticion/**", "/roles/**", "/tipoDocumento/**", "/tipo-peticion/**", "/usuarios/**", "/versiones/**")
                        .hasAnyRole("ADMIN", "EDITOR", "VIEWER")
                        .requestMatchers(HttpMethod.POST, "/documentos/**", "/estado-peticion/**", "/firma-peticion/**", "/firmaUsuario/**", "/historial/**", "/notificaciones/**", "/organizaciones/**", "/permisos/**", "/peticion/**", "/plantillaDocumento/**", "/requerimientoDocumento/**", "/requerimiento-peticion/**", "/roles/**", "/tipoDocumento/**", "/tipo-peticion/**", "/usuarios/**", "/versiones/**")
                        .hasAnyRole("ADMIN", "EDITOR")
                        .requestMatchers(HttpMethod.PUT, "/documentos/**", "/estado-peticion/**", "/firma-peticion/**", "/firmaUsuario/**", "/historial/**", "/notificaciones/**", "/organizaciones/**", "/permisos/**", "/peticion/**", "/plantillaDocumento/**", "/requerimientoDocumento/**", "/requerimiento-peticion/**", "/roles/**", "/tipoDocumento/**", "/tipo-peticion/**", "/usuarios/**", "/versiones/**")
                        .hasAnyRole("ADMIN", "EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/documentos/**", "/estado-peticion/**", "/firma-peticion/**", "/firmaUsuario/**", "/historial/**", "/notificaciones/**", "/organizaciones/**", "/permisos/**", "/peticion/**", "/plantillaDocumento/**", "/requerimientoDocumento/**", "/requerimiento-peticion/**", "/roles /**", "/tipoDocumento /**", "/tipo-peticion /**", "/usuarios /**", "/versiones /**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH,
                                "/documentos/**", "/estado-peticion/**", "/firma-peticion/**",
                                "/firmaUsuario/**", "/historial/**", "/notificaciones/**",
                                "/organizaciones/**", "/permisos/**", "/peticion/**",
                                "/plantillaDocumento/**", "/requerimientoDocumento/**",
                                "/requerimiento-peticion/**", "/roles/**", "/tipoDocumento/**",
                                "/tipo-peticion/**", "/usuarios/**", "/versiones/**")
                        .hasAnyRole("ADMIN", "EDITOR")
                        .anyRequest().denyAll()

                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(securityUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(allowedOrigins.split(",")));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
}
