package com.clinica.odonto.repository;

import com.clinica.odonto.model.Enums.EstadoPaciente;
import com.clinica.odonto.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PacienteRepository extends JpaRepository<Paciente, UUID> {
    boolean existsByNumeroIdentificacion(String numeroIdentificacion);

    boolean existsByEmail(String email);

    Optional<Paciente> findByEmail(String email);

    List<Paciente> findByEstado(EstadoPaciente estado);
}
