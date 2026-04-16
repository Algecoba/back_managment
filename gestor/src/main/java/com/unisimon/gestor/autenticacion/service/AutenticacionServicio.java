package com.unisimon.gestor.autenticacion.service;

import com.unisimon.gestor.autenticacion.dto.RespuestaTokenDto;
import com.unisimon.gestor.autenticacion.dto.SolicitudLoginDto;
import com.unisimon.gestor.autenticacion.exception.ExcepcionAutenticacion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Servicio de autenticacion.
 * La validacion de credenciales vive en UsuarioDetallesServicio.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AutenticacionServicio {

    private final AuthenticationManager authenticationManager;
    private final TokenServicio tokenServicio;

    @Value("${jwt.expiracion-ms}")
    private long expiracionMs;

    public RespuestaTokenDto login(SolicitudLoginDto solicitud) {
        try {
            Authentication autenticacion = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    solicitud.getCorreo(),
                    solicitud.getContrasena()
                )
            );

            UserDetails userDetails = (UserDetails) autenticacion.getPrincipal();

            String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

            UUID usuarioId = UUID.fromString("00000000-0000-0000-0000-000000000099");

            String token = tokenServicio.generarToken(
                userDetails.getUsername(), usuarioId, roles);

            log.info("Login exitoso para: {}", userDetails.getUsername());

            return RespuestaTokenDto.builder()
                .token(token)
                .tipo("Bearer")
                .usuarioId(usuarioId)
                .correo(userDetails.getUsername())
                .roles(roles)
                .expiracionMs(expiracionMs)
                .build();

        } catch (BadCredentialsException ex) {
            log.warn("Intento de login fallido para: {}", solicitud.getCorreo());
            throw new ExcepcionAutenticacion();
        }
    }
}
