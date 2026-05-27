package com.clinica.odonto.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CompletarTratamientoRequest(
        @NotBlank String resultadoClinico,
        String observaciones,
        LocalDate proximoControl
) {
}
