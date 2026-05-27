package com.clinica.odonto.dto;

import com.clinica.odonto.model.Enums.ClasificacionTratamiento;
import com.clinica.odonto.model.Enums.EstadoTratamiento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TratamientoResponse(
        UUID idTratamiento,
        UUID idPaciente,
        UUID idCita,
        String codigoTratamiento,
        String nombreTratamiento,
        ClasificacionTratamiento clasificacion,
        String dienteAfectado,
        BigDecimal presupuesto,
        EstadoTratamiento estado,
        LocalDate fechaInicio,
        LocalDate fechaCompletacion,
        String resultadoClinico,
        LocalDate fechaProximoControl,
        List<SesionTratamientoResponse> sesiones
) {
}
