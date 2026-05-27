package com.clinica.odonto.repository;

import com.clinica.odonto.model.EventoAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventoAuditoriaRepository extends JpaRepository<EventoAuditoria, UUID> {
    List<EventoAuditoria> findTop50ByOrderByFechaEventoDesc();
}
