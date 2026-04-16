package com.unisimon.gestor.autenticacion.security;

import com.unisimon.gestor.autenticacion.service.TokenServicio;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro JWT que se ejecuta una sola vez por request.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FiltroJwt extends OncePerRequestFilter {

    private final TokenServicio tokenServicio;
    private final UsuarioDetallesServicio usuarioDetallesServicio;

    private static final String PREFIJO_BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith(PREFIJO_BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(PREFIJO_BEARER.length());

        try {
            String correo = tokenServicio.extraerCorreo(token);

            if (correo != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails =
                    usuarioDetallesServicio.loadUserByUsername(correo);

                if (tokenServicio.esTokenValido(token, userDetails)) {
                    UsernamePasswordAuthenticationToken autenticacion =
                        new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    autenticacion.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(autenticacion);
                    log.debug("Usuario autenticado via JWT: {}", correo);
                }
            }
        } catch (Exception ex) {
            log.warn("No se pudo autenticar el token JWT: {}", ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
