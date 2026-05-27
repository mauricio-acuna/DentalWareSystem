package com.clinica.odonto.dto;

import com.clinica.odonto.model.Enums.EstadoCita;
import com.clinica.odonto.model.Enums.Prioridad;
import com.clinica.odonto.model.Enums.TipoCita;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record CitaResponse(
        UUID idCita,
        UUID idPaciente,
        UUID idDentista,
        LocalDate fechaCita,
        LocalTime horaInicio,
        LocalTime horaFin,
        TipoCita tipoCita,
        EstadoCita estado,
        String motivoConsulta,
        Prioridad prioridad
) {
}
