package com.unisimon.gestor.autenticacion.service;

import com.unisimon.gestor.autenticacion.dto.RespuestaTokenDto;
import com.unisimon.gestor.autenticacion.dto.SolicitudLoginDto;
import com.unisimon.gestor.autenticacion.exception.ExcepcionAutenticacion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Servicio de autenticación.
 *
 * Orquesta el flujo de login:
 * 1. Delega la verificación de credenciales a Spring Security
 * 2. Extrae los datos del usuario autenticado
 * 3. Genera el JWT via TokenServicio
 * 4. Retorna el token y los datos básicos del usuario
 *
 * No contiene lógica de validación de credenciales — eso lo hace
 * Spring Security internamente a través del AuthenticationManager.
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
            // Spring Security verifica correo + contraseña contra UserDetailsService
            Authentication autenticacion = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            solicitud.getCorreo(),
                            solicitud.getContrasena()));

            UserDetails userDetails = (UserDetails) autenticacion.getPrincipal();

            // Concatenar todos los roles como string para incluir en el token
            String roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

            // TODO: cuando exista UsuarioRepositorio, obtener el UUID real del usuario.
            // Por ahora usamos un UUID fijo para el usuario de prueba.
            UUID usuarioId = UUID.fromString("00000000-0000-0000-0000-000000000099");

            String token = tokenServicio.generarToken(
                    userDetails.getUsername(),
                    usuarioId,
                    roles);

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
            // Traducimos la excepción de Spring a nuestra excepción de negocio
            log.warn("Intento de login fallido para: {}", solicitud.getCorreo());
            throw new ExcepcionAutenticacion();
        }
    }
}