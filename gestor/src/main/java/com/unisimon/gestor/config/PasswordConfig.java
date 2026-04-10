package com.unisimon.gestor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración del encoder de contraseñas.
 *
 * Separado de SeguridadConfig intencionalmente para evitar
 * dependencia circular entre SeguridadConfig → FiltroJwt →
 * UsuarioDetallesServicio → PasswordEncoder → SeguridadConfig.
 *
 * Al vivir en su propia clase, PasswordEncoder puede ser
 * inyectado por cualquier bean sin crear ciclos.
 */
@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}