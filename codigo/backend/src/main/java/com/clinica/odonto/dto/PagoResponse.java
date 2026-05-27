package com.clinica.odonto.dto;

import com.clinica.odonto.model.Enums.EstadoFactura;

import java.math.BigDecimal;
import java.util.UUID;

public record PagoResponse(
        UUID idPago,
        String numeroRecibo,
        BigDecimal importePago,
        EstadoFactura estadoFactura
) {
}
