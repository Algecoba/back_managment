package com.unisimon.gestor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Configuracion de auditoria automatica de JPA.
 * Extrae el correo del usuario autenticado para poblar
 * usuario_creacion y usuario_actualizacion.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditoriaConfig {

    private static final String SISTEMA = "sistema";

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

            if (auth == null || !auth.isAuthenticated() ||
                auth.getName().equals("anonymousUser")) {
                return Optional.of(SISTEMA);
            }

            return Optional.of(auth.getName());
        };
    }
}
