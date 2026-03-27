package com.unisimon.gestor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración central de Spring Security.
 *
 * ESTADO ACTUAL: seguridad desactivada intencionalmente durante
 * el desarrollo inicial. Todos los endpoints son públicos.
 *
 * CUANDO SE ACTIVE JWT: esta clase recibirá el FiltroJwt como
 * parámetro y lo registrará antes del filtro de autenticación
 * estándar de Spring. Los endpoints protegidos se configuran aquí.
 *
 * NO eliminar esta clase aunque la seguridad esté desactivada:
 * Spring Security requiere al menos un SecurityFilterChain definido
 * para no aplicar su configuración por defecto (que bloquea todo).
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Permite usar @PreAuthorize en controladores y servicios
public class SeguridadConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Desactivar CSRF: no aplica en APIs REST stateless con JWT
                .csrf(AbstractHttpConfigurer::disable)

                // Sin sesiones HTTP: cada request se autentica con su propio JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // TODO: cuando se active JWT, aquí se restringe por roles y rutas.
                // Por ahora todos los endpoints son públicos para desarrollo.
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }

    /**
     * Encoder de contraseñas con BCrypt.
     * Se registra como Bean para poder inyectarlo en AutenticacionServicio
     * cuando implementemos el login. BCrypt es el estándar de la industria:
     * aplica salt automático y es resistente a ataques de fuerza bruta.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}