package com.clinica.odonto.dto;

import com.clinica.odonto.model.Enums.NivelSeveridadEvento;
import com.clinica.odonto.model.Enums.ResultadoEvento;
import com.clinica.odonto.model.Enums.TipoEventoAuditoria;

import java.time.Instant;
import java.util.UUID;

public record EventoAuditoriaResponse(
        UUID idEvento,
        TipoEventoAuditoria tipoEvento,
        String moduloAfectado,
        String entidadAfectada,
        String idRegistroAfectado,
        String descripcionEvento,
        Instant fechaEvento,
        ResultadoEvento resultadoEvento,
        NivelSeveridadEvento nivelSeveridad
) {
}
