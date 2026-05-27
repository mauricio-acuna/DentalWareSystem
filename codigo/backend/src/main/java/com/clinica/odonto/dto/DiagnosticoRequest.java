package com.clinica.odonto.dto;

import com.clinica.odonto.model.Enums.SeveridadDiagnostico;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.UUID;

public record DiagnosticoRequest(
        UUID idCita,
        @NotBlank String codigoCIE10,
        @NotBlank String descripcionDiagnostico,
        String dienteAfectado,
        LocalDate fechaDiagnostico,
        UUID empleadoDiagnosticador,
        SeveridadDiagnostico severidad,
        boolean confirmadoLaboratorio,
        String hallazgosRadiologicos,
        String planTratamientoRecomendado,
        boolean urgenciaTratamiento
) {
}
