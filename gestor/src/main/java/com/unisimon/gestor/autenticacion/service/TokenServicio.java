package com.unisimon.gestor.autenticacion.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

/**
 * Servicio responsable de la generacion y validacion de JWT.
 */
@Slf4j
@Service
public class TokenServicio {

    @Value("${jwt.secret}")
    private String secreto;

    @Value("${jwt.expiracion-ms}")
    private long expiracionMs;

    private SecretKey getClave() {
        return Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8));
    }

    public String generarToken(String correo, UUID usuarioId, String roles) {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + expiracionMs);

        return Jwts.builder()
            .subject(correo)
            .claim("usuarioId", usuarioId.toString())
            .claim("roles", roles)
            .issuedAt(ahora)
            .expiration(expiracion)
            .signWith(getClave())
            .compact();
    }

    public String extraerCorreo(String token) {
        return extraerClaims(token).getSubject();
    }

    public UUID extraerUsuarioId(String token) {
        String id = extraerClaims(token).get("usuarioId", String.class);
        return UUID.fromString(id);
    }

    public String extraerRoles(String token) {
        return extraerClaims(token).get("roles", String.class);
    }

    public boolean esTokenValido(String token, UserDetails userDetails) {
        try {
            String correo = extraerCorreo(token);
            return correo.equals(userDetails.getUsername()) && !estaExpirado(token);
        } catch (JwtException | IllegalArgumentException ex) {
            log.warn("Token JWT invalido: {}", ex.getMessage());
            return false;
        }
    }

    private Claims extraerClaims(String token) {
        return Jwts.parser()
            .verifyWith(getClave())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    private boolean estaExpirado(String token) {
        return extraerClaims(token).getExpiration().before(new Date());
    }
}
