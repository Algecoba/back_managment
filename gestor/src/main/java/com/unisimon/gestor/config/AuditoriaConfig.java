package com.unisimon.gestor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

/**
 * Configuración de auditoría automática de JPA.
 *
 * Habilita @CreatedBy, @LastModifiedBy, @CreatedDate y
 * 
 * @LastModifiedDate en las entidades que extiendan una clase
 *                   base de auditoría.
 *
 *                   AuditorAware le dice a Spring quién es el usuario actual.
 *                   Por ahora retorna un UUID de sistema como placeholder.
 *
 *                   CUANDO SE ACTIVE JWT: este bean se actualizará para extraer
 *                   el usuario_id del SecurityContext en lugar del UUID fijo.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditoriaConfig {

    // UUID ficticio que representa al sistema mientras no hay autenticación real.
    // Se reemplaza por el UUID del usuario autenticado cuando tengamos JWT.
    private static final UUID SISTEMA_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Bean
    public AuditorAware<UUID> auditorAware() {
        // TODO: reemplazar por extracción del SecurityContext cuando JWT esté activo
        return () -> Optional.of(SISTEMA_ID);
    }
}