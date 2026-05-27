package com.clinica.odonto.dto;

import com.clinica.odonto.model.Enums.EstadoPaciente;
import com.clinica.odonto.model.Enums.Genero;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record PacienteResponse(
        UUID idPaciente,
        String numeroIdentificacion,
        String nombreCompleto,
        String apellidos,
        LocalDate fechaNacimiento,
        Integer edad,
        Genero genero,
        String email,
        String telefonoMovil,
        DireccionRequest direccion,
        boolean consentimientoRGPD,
        EstadoPaciente estado,
        Instant fechaAlta
) {
}
