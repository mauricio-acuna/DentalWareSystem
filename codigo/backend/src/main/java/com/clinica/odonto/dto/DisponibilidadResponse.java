package com.clinica.odonto.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record DisponibilidadResponse(List<DiaDisponibilidad> disponibilidad) {
    public record DiaDisponibilidad(LocalDate fecha, List<HoraDisponible> horas) {
    }

    public record HoraDisponible(LocalTime hora, boolean disponible) {
    }
}
