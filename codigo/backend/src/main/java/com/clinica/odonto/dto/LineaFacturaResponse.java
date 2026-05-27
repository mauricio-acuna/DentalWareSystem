package com.clinica.odonto.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record LineaFacturaResponse(
        UUID idLinea,
        String concepto,
        BigDecimal cantidad,
        BigDecimal precioUnitario,
        BigDecimal ivaPercent,
        BigDecimal importeLinea
) {
}
