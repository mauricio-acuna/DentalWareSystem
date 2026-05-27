package com.clinica.odonto.dto;

import com.clinica.odonto.model.Enums.RolUsuario;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record AuthResponse(
        String accessToken,
        String tokenType,
        Instant expiresAt,
        UUID idUsuario,
        String nombreCompleto,
        String email,
        Set<RolUsuario> roles,
        boolean requiereCambioPassword
) {
}
