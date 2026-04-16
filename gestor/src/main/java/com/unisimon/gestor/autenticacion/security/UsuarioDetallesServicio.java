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
 * Implementacion de UserDetailsService que consulta la BD real.
 * TODO: eliminar bloque de desarrollo cuando Microsoft OAuth2 este activo.
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

        if ("admin@unisimon.edu.co".equals(correo)) {
            return usuarioRepositorio.findByCorreoConRoles(correo)
                .map(u -> construirUserDetails(u, passwordEncoder.encode("Admin2025!")))
                .orElseGet(() -> User.builder()
                    .username(correo)
                    .password(passwordEncoder.encode("Admin2025!"))
                    .authorities(List.of(new SimpleGrantedAuthority("ADMINISTRADOR")))
                    .build());
        }

        if ("investigador@unisimon.edu.co".equals(correo)) {
            return User.builder()
                .username(correo)
                .password(passwordEncoder.encode("Inv2025!"))
                .authorities(List.of(new SimpleGrantedAuthority("INVESTIGADOR")))
                .build();
        }

        Usuario usuario = usuarioRepositorio
            .findByCorreoConRoles(correo)
            .orElseThrow(() -> new UsernameNotFoundException(
                "No existe usuario activo con correo: " + correo));

        return construirUserDetails(usuario, "{noop}sin-password");
    }

    private UserDetails construirUserDetails(Usuario usuario, String password) {
        List<SimpleGrantedAuthority> authorities = usuario.getUsuarioRoles()
            .stream()
            .map(ur -> new SimpleGrantedAuthority(ur.getRol().getCodigo()))
            .toList();

        return User.builder()
            .username(usuario.getCorreo())
            .password(password)
            .authorities(authorities)
            .accountLocked(!usuario.isEsActivo())
            .disabled(!usuario.isEsActivo())
            .build();
    }
}
