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
 *
 * AuditorAware extrae el correo del usuario autenticado desde
 * el SecurityContext para poblarlo en usuario_creacion y
 * usuario_actualizacion de cada entidad auditable.
 *
 * Cumple estandar institucional: usuario_creacion varchar(100).
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

            // Si no hay autenticacion activa usa "sistema" como fallback
            if (auth == null || !auth.isAuthenticated() ||
                    auth.getName().equals("anonymousUser")) {
                return Optional.of(SISTEMA);
            }

            // El nombre en Spring Security es el correo institucional
            return Optional.of(auth.getName());
        };
    }
}