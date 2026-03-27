package com.unisimon.gestor.autenticacion.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de UserDetailsService requerida por Spring Security.
 *
 * Spring Security llama a loadUserByUsername() durante la autenticación
 * para obtener los datos del usuario y verificar sus credenciales.
 *
 * ESTADO ACTUAL: usuario hardcodeado en memoria para desarrollo.
 * Permite probar el flujo JWT completo sin necesidad de base de datos.
 *
 * CUANDO EXISTA EL MÓDULO usuarios: reemplazar el usuario hardcodeado
 * por una consulta real a UsuarioRepositorio. El resto del flujo
 * (FiltroJwt, TokenServicio) no cambia en absoluto.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioDetallesServicio implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    /**
     * Carga el usuario por correo electrónico.
     * Spring Security usa el correo como "username" en este sistema.
     *
     * @param correo correo institucional del usuario
     * @throws UsernameNotFoundException si no existe el usuario
     */
    @Override
    public UserDetails loadUserByUsername(String correo)
            throws UsernameNotFoundException {

        log.debug("Cargando usuario por correo: {}", correo);

        // ── Usuario de prueba hardcodeado ────────────────────────
        // TODO: reemplazar por UsuarioRepositorio.findByCorreo(correo)
        // cuando el módulo usuarios esté implementado
        if ("admin@unisimon.edu.co".equals(correo)) {
            return User.builder()
                    .username(correo)
                    // La contraseña debe estar encodeada con BCrypt
                    .password(passwordEncoder.encode("Admin2025!"))
                    .authorities(List.of(
                            new SimpleGrantedAuthority("ROLE_ADMINISTRADOR")))
                    .build();
        }

        if ("investigador@unisimon.edu.co".equals(correo)) {
            return User.builder()
                    .username(correo)
                    .password(passwordEncoder.encode("Inv2025!"))
                    .authorities(List.of(
                            new SimpleGrantedAuthority("ROLE_INVESTIGADOR")))
                    .build();
        }

        // Si no existe el usuario, Spring Security lanza 401 automáticamente
        throw new UsernameNotFoundException(
                "No existe usuario con correo: " + correo);
    }
}