package com.unisimon.gestor.autenticacion.security;

import com.unisimon.gestor.usuarios.domain.Usuario;
import com.unisimon.gestor.usuarios.repository.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación de UserDetailsService que consulta la BD real.
 *
 * El sistema productivo usará Microsoft OAuth2 — los usuarios no
 * tienen contraseña almacenada. Para desarrollo, el admin de prueba
 * tiene una contraseña hardcodeada que permite probar el flujo JWT
 * completo sin depender de Microsoft Identity.
 *
 * TODO: cuando Microsoft OAuth2 esté activo, eliminar el bloque
 * de desarrollo y este servicio solo validará tokens de Microsoft.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioDetallesServicio implements UserDetailsService {

        private final UsuarioRepositorio usuarioRepositorio;
        private final PasswordEncoder passwordEncoder;

        @Override
        @Transactional(readOnly = true)
        public UserDetails loadUserByUsername(String correo)
                        throws UsernameNotFoundException {

                log.debug("Cargando usuario por correo: {}", correo);

                // ── Bloque de desarrollo ─────────────────────────────────
                // Permite hacer login con usuario+contraseña mientras no
                // existe integración con Microsoft. El usuario debe existir
                // en BD para que /me retorne datos reales.
                // TODO: eliminar cuando Microsoft OAuth2 esté implementado.
                if ("admin@unisimon.edu.co".equals(correo)) {
                        return usuarioRepositorio.findByCorreoConRoles(correo)
                                        .map(u -> construirUserDetails(u,
                                                        passwordEncoder.encode("Admin2025!")))
                                        .orElseGet(() -> User.builder()
                                                        .username(correo)
                                                        .password(passwordEncoder.encode("Admin2025!"))
                                                        .authorities(List.of(
                                                                        new SimpleGrantedAuthority("ADMINISTRADOR")))
                                                        .build());
                }

                if ("investigador@unisimon.edu.co".equals(correo)) {
                        return User.builder()
                                        .username(correo)
                                        .password(passwordEncoder.encode("Inv2025!"))
                                        .authorities(List.of(
                                                        new SimpleGrantedAuthority("INVESTIGADOR")))
                                        .build();
                }
                // ── Fin bloque de desarrollo ─────────────────────────────

                // Flujo real: cualquier otro usuario debe existir en BD
                Usuario usuario = usuarioRepositorio
                                .findByCorreoConRoles(correo)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "No existe usuario activo con correo: " + correo));

                // Sin contraseña real — autenticación es por JWT, no por password
                return construirUserDetails(usuario, "{noop}sin-password");
        }

        /**
         * Construye el UserDetails de Spring Security desde nuestra entidad.
         * Extrae los roles de la tabla usuario_rol para construir authorities.
         */
        private UserDetails construirUserDetails(Usuario usuario, String password) {
                List<SimpleGrantedAuthority> authorities = usuario.getUsuarioRoles()
                                .stream()
                                .map(ur -> new SimpleGrantedAuthority(ur.getRol().getCodigo()))
                                .toList();

                return User.builder()
                                .username(usuario.getCorreo())
                                .password(password)
                                .authorities(authorities)
                                .accountLocked(!usuario.isActivo())
                                .disabled(!usuario.isActivo())
                                .build();
        }
}