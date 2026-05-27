package com.clinica.odonto.repository;

import com.clinica.odonto.model.SesionTratamiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SesionTratamientoRepository extends JpaRepository<SesionTratamiento, UUID> {
    List<SesionTratamiento> findByTratamientoIdTratamientoOrderByNumeroSesion(UUID idTratamiento);
}
