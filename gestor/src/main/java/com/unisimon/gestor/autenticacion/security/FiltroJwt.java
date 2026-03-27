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
 * Filtro JWT que se ejecuta una sola vez por request (OncePerRequestFilter).
 *
 * Flujo de validación:
 * 1. Busca el header "Authorization: Bearer <token>"
 * 2. Si no existe o no empieza con "Bearer ", deja pasar sin autenticar
 * 3. Extrae el correo del token
 * 4. Verifica que el token sea válido y no haya expirado
 * 5. Registra la autenticación en el SecurityContext de Spring
 *
 * Si el token es inválido simplemente no se autentica el request,
 * y Spring Security lo rechazará con 401 en los endpoints protegidos.
 * No lanzamos excepciones aquí para no interrumpir el FilterChain.
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

        // Si no hay header Authorization o no es Bearer, continuar sin autenticar
        if (authHeader == null || !authHeader.startsWith(PREFIJO_BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer el token removiendo el prefijo "Bearer "
        String token = authHeader.substring(PREFIJO_BEARER.length());

        try {
            String correo = tokenServicio.extraerCorreo(token);

            // Solo autenticar si hay correo y el contexto no tiene autenticación previa
            if (correo != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = usuarioDetallesServicio.loadUserByUsername(correo);

                if (tokenServicio.esTokenValido(token, userDetails)) {
                    // Registrar la autenticación en el contexto de Spring Security
                    UsernamePasswordAuthenticationToken autenticacion = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null, // credentials null: ya validamos con JWT
                            userDetails.getAuthorities());

                    autenticacion.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(autenticacion);
                    log.debug("Usuario autenticado via JWT: {}", correo);
                }
            }
        } catch (Exception ex) {
            // Token malformado o expirado — log y continuar sin autenticar
            log.warn("No se pudo autenticar el token JWT: {}", ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}