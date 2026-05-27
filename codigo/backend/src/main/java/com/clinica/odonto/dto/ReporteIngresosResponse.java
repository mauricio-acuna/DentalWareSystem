package com.clinica.odonto.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReporteIngresosResponse(
        LocalDate fechaInicio,
        LocalDate fechaFin,
        BigDecimal facturado,
        BigDecimal pagado,
        BigDecimal pendiente
) {
}
