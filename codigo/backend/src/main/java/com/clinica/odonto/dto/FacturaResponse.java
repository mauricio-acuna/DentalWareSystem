package com.clinica.odonto.dto;

import com.clinica.odonto.model.Enums.EstadoFactura;
import com.clinica.odonto.model.Enums.TipoFactura;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record FacturaResponse(
        UUID idFactura,
        String numeroFactura,
        UUID idPaciente,
        LocalDate fechaFactura,
        LocalDate fechaVencimiento,
        TipoFactura tipoFactura,
        EstadoFactura estado,
        List<LineaFacturaResponse> lineas,
        BigDecimal baseImponible,
        BigDecimal iva,
        BigDecimal importeTotal
) {
}
