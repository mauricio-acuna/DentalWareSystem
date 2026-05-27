package com.clinica.odonto.repository;

import com.clinica.odonto.model.Enums.EstadoTratamiento;
import com.clinica.odonto.model.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TratamientoRepository extends JpaRepository<Tratamiento, UUID> {
    long countByFechaInicio(LocalDate fechaInicio);

    List<Tratamiento> findByPacienteIdPaciente(UUID idPaciente);

    long countByEstado(EstadoTratamiento estado);
}
