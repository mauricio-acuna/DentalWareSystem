package com.clinica.odonto.dto;

import com.clinica.odonto.model.Enums.EstadoUsuario;
import com.clinica.odonto.model.Enums.RolUsuario;

import java.util.Set;
import java.util.UUID;

public record UsuarioSistemaResponse(
        UUID idUsuario,
        String nombreCompleto,
        String email,
        EstadoUsuario estado,
        Set<RolUsuario> roles,
        boolean requiereCambioPassword
) {
}
