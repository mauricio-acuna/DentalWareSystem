package com.clinica.odonto.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record SesionTratamientoRequest(
        @Positive int numeroSesion,
        @NotNull LocalDate fechaRealizacion,
        @PositiveOrZero Integer tiempoReal,
        List<String> procedimientos,
        String complicaciones,
        LocalDate proximaSesion,
        UUID empleadoResponsable
) {
}
