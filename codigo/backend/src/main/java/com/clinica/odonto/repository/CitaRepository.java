package com.clinica.odonto.repository;

import com.clinica.odonto.model.Cita;
import com.clinica.odonto.model.Enums.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface CitaRepository extends JpaRepository<Cita, UUID> {
    boolean existsByIdDentistaAndFechaCitaAndHoraInicioLessThanAndHoraFinGreaterThanAndEstadoNot(
            UUID idDentista,
            LocalDate fechaCita,
            LocalTime horaFin,
            LocalTime horaInicio,
            EstadoCita estado);

    List<Cita> findByPacienteIdPaciente(UUID idPaciente);
}
