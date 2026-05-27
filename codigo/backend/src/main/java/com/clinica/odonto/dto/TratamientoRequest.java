package com.clinica.odonto.dto;

import com.clinica.odonto.model.Enums.ClasificacionTratamiento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public record TratamientoRequest(
        UUID idCita,
        @NotBlank String nombreTratamiento,
        String descripcionDetallada,
        @NotNull ClasificacionTratamiento clasificacion,
        @Pattern(regexp = "^(1[1-8]|2[1-8]|3[1-8]|4[1-8])$", message = "Debe usar numeracion FDI valida") String dienteAfectado,
        String superficieDiente,
        String diagnosticoAsociado,
        String planTratamiento,
        @PositiveOrZero BigDecimal presupuesto,
        @PositiveOrZero BigDecimal coberturaSeguro,
        @PositiveOrZero BigDecimal copagoPaciente,
        Integer duracionEstimadaMinutos,
        UUID empleadoResponsable
) {
}
