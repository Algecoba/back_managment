package com.unisimon.gestor.config;

import com.unisimon.gestor.autenticacion.security.FiltroJwt;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.unisimon.gestor.autenticacion.security.UsuarioDetallesServicio;

/**
 * Configuración central de Spring Security con JWT activo.
 *
 * La cadena de filtros registra FiltroJwt antes del filtro estándar
 * de autenticación de Spring, de modo que cada request es validado
 * por el token antes de llegar a los controladores.
 *
 * Rutas públicas: solo /api/v1/auth/** (login)
 * Rutas protegidas: todo lo demás requiere JWT válido
 *
 * NOTA: @PreAuthorize en controladores y servicios permite control
 * fino de acceso por rol, además de la autenticación general aquí.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SeguridadConfig {

    private final FiltroJwt filtroJwt;
    private final UsuarioDetallesServicio usuarioDetallesServicio;

    @Bean
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
            AuthenticationProvider authProvider) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/actuator/health").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(filtroJwt,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Proveedor de autenticación que conecta Spring Security
     * con nuestro UsuarioDetallesServicio y el PasswordEncoder.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioDetallesServicio);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * AuthenticationManager expuesto como Bean para que
     * AutenticacionServicio pueda disparar la autenticación
     * programáticamente durante el login.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}