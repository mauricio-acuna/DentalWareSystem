package com.clinica.odonto.dto;

import com.clinica.odonto.model.Enums.Prioridad;
import com.clinica.odonto.model.Enums.TipoCita;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record CitaRequest(
        @NotNull UUID idPaciente,
        UUID idDentista,
        UUID idHigienista,
        @NotNull LocalDate fechaCita,
        @NotNull LocalTime horaInicio,
        @NotNull LocalTime horaFin,
        @NotNull TipoCita tipoCita,
        String motivoConsulta,
        Prioridad prioridad
) {
}
