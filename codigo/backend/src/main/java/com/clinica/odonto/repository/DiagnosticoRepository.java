package com.clinica.odonto.repository;

import com.clinica.odonto.model.Diagnostico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DiagnosticoRepository extends JpaRepository<Diagnostico, UUID> {
    List<Diagnostico> findByPacienteIdPaciente(UUID idPaciente);
}
