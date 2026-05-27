package com.clinica.odonto.dto;

import com.clinica.odonto.model.Enums.TipoFactura;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.UUID;

public record FacturaRequest(
        @NotNull UUID idPaciente,
        TipoFactura tipoFactura,
        @NotEmpty List<@Valid LineaFacturaRequest> lineas,
        @Positive Integer diasCredito
) {
}
