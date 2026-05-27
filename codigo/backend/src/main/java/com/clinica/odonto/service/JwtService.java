package com.clinica.odonto.service;

import com.clinica.odonto.model.Enums.RolUsuario;
import com.clinica.odonto.model.UsuarioSistema;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JwtService {
    private final SecretKey signingKey;
    private final long expirationMinutes;

    public JwtService(
            @Value("${app.security.jwt-secret:dev-secret-change-me-dev-secret-change-me}") String secret,
            @Value("${app.security.jwt-expiration-minutes:60}") long expirationMinutes) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMinutes = expirationMinutes;
    }

    public String emitir(UsuarioSistema usuario) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(expirationMinutes * 60);
        List<String> roles = usuario.getRoles().stream().map(RolUsuario::name).toList();
        return Jwts.builder()
                .subject(usuario.getEmail())
                .id(UUID.randomUUID().toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .claim("uid", usuario.getIdUsuario().toString())
                .claim("roles", roles)
                .signWith(signingKey)
                .compact();
    }

    public Claims validar(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Instant calcularExpiracion() {
        return Instant.now().plusSeconds(expirationMinutes * 60);
    }
}
