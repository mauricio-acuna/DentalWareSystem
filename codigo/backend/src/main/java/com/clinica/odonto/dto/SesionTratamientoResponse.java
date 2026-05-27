package com.clinica.odonto.dto;

import java.time.LocalDate;
import java.util.UUID;

public record SesionTratamientoResponse(
        UUID idSesion,
        int numeroSesion,
        LocalDate fechaRealizacion,
        Integer tiempoReal,
        String procedimientos,
        String complicaciones,
        LocalDate proximaSesion,
        UUID empleadoResponsable
) {
}
