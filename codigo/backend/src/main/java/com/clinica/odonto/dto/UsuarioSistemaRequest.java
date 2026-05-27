package com.clinica.odonto.dto;

import com.clinica.odonto.model.Enums.RolUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UsuarioSistemaRequest(
        @NotBlank @Size(max = 120) String nombreCompleto,
        @NotBlank @Email @Size(max = 120) String email,
        @NotBlank @Size(min = 10, max = 80) String passwordTemporal,
        @NotEmpty Set<RolUsuario> roles
) {
}
