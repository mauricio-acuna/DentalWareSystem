package com.clinica.odonto.dto;

public record ReporteActividadResponse(
        long citasTotales,
        long citasCompletadas,
        long citasCanceladas,
        long citasNoPresentado,
        long tratamientosActivos,
        long tratamientosCompletados
) {
}
