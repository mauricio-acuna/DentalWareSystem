package com.clinica.odonto.dto;

import com.clinica.odonto.model.Enums.Genero;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.List;

public record PacienteRequest(
        @NotBlank @Pattern(regexp = "^[A-Z0-9]{8,12}$") String numeroIdentificacion,
        @NotBlank String nombreCompleto,
        @NotBlank String apellidos,
        @NotNull LocalDate fechaNacimiento,
        Genero genero,
        @NotBlank @Email String email,
        String telefonoMovil,
        DireccionRequest direccion,
        boolean consentimientoRGPD,
        List<String> historialAlergias,
        List<String> patologiasBase,
        List<String> medicamentosActuales
) {
}
