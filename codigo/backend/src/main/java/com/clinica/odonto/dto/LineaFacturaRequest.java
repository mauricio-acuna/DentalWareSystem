package com.clinica.odonto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record LineaFacturaRequest(
        @NotBlank String concepto,
        @NotNull @Positive BigDecimal cantidad,
        @NotNull @PositiveOrZero BigDecimal precioUnitario,
        @NotNull @PositiveOrZero BigDecimal ivaPercent
) {
}
