package com.clinica.odonto.dto;

import com.clinica.odonto.model.Enums.FormaPago;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagoRequest(
        @NotNull FormaPago formaPago,
        @NotNull @Positive BigDecimal importePago,
        String numeroTransaccion,
        LocalDate fecha
) {
}
