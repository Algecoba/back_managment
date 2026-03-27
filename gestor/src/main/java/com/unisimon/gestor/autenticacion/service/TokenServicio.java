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
 * Servicio responsable exclusivamente de la generación y validación de JWT.
 *
 * Encapsula toda interacción con la librería JJWT para que el resto
 * del sistema nunca dependa directamente de ella. Si en el futuro se
 * cambia la librería de JWT, solo este archivo cambia.
 *
 * El token contiene:
 * - subject: el correo del usuario (identificador único)
 * - usuarioId: UUID del usuario en la base de datos
 * - roles: lista de roles del usuario para autorización
 * - iat: fecha de emisión
 * - exp: fecha de expiración
 */
@Slf4j
@Service
public class TokenServicio {

    @Value("${jwt.secret}")
    private String secreto;

    @Value("${jwt.expiracion-ms}")
    private long expiracionMs;

    /**
     * Genera la clave criptográfica a partir del secreto configurado.
     * Se recalcula cada vez para evitar guardar estado mutable en el servicio.
     */
    private SecretKey getClave() {
        return Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Genera un JWT firmado para el usuario autenticado.
     *
     * @param correo    identificador único del usuario (subject del token)
     * @param usuarioId UUID del usuario para incluir en los claims
     * @param roles     roles del usuario separados por coma: "INVESTIGADOR,ADMIN"
     */
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

    /**
     * Extrae el correo (subject) del token.
     * Lanza JwtException si el token es inválido o expiró.
     */
    public String extraerCorreo(String token) {
        return extraerClaims(token).getSubject();
    }

    /**
     * Extrae el UUID del usuario del token.
     */
    public UUID extraerUsuarioId(String token) {
        String id = extraerClaims(token).get("usuarioId", String.class);
        return UUID.fromString(id);
    }

    /**
     * Extrae los roles del token como string.
     */
    public String extraerRoles(String token) {
        return extraerClaims(token).get("roles", String.class);
    }

    /**
     * Valida que el token sea auténtico, no haya expirado y pertenezca
     * al usuario indicado por UserDetails.
     */
    public boolean esTokenValido(String token, UserDetails userDetails) {
        try {
            String correo = extraerCorreo(token);
            return correo.equals(userDetails.getUsername()) && !estaExpirado(token);
        } catch (JwtException | IllegalArgumentException ex) {
            // Token malformado, firma inválida o expirado — no es válido
            log.warn("Token JWT inválido: {}", ex.getMessage());
            return false;
        }
    }

    // ── Métodos privados ─────────────────────────────────────────

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