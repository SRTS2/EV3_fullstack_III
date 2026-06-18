package com.rednorte.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(Base64.getEncoder().encodeToString(secret.getBytes()));
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String rut, String role, Long usuarioId, Long pacienteId) {
        return Jwts.builder()
                .issuer("rednorte-client")
                .subject(rut)
                .claim("role", role)
                .claim("usuarioId", usuarioId)
                .claim("pacienteId", pacienteId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(secretKey)
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractRut(String token) {
        return validateToken(token).getSubject();
    }

    public String extractRole(String token) {
        return validateToken(token).get("role", String.class);
    }

    public Long extractUsuarioId(String token) {
        return validateToken(token).get("usuarioId", Long.class);
    }

    public Long extractPacienteId(String token) {
        return validateToken(token).get("pacienteId", Long.class);
    }
}
