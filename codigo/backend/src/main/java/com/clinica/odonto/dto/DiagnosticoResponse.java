package com.clinica.odonto.dto;

import com.clinica.odonto.model.Enums.SeveridadDiagnostico;

import java.time.LocalDate;
import java.util.UUID;

public record DiagnosticoResponse(
        UUID idDiagnostico,
        UUID idPaciente,
        UUID idCita,
        String codigoCIE10,
        String descripcionDiagnostico,
        String dienteAfectado,
        LocalDate fechaDiagnostico,
        SeveridadDiagnostico severidad,
        boolean confirmadoLaboratorio,
        String planTratamientoRecomendado,
        boolean urgenciaTratamiento
) {
}
