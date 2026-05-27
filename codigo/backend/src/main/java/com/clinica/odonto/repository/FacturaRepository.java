package com.clinica.odonto.repository;

import com.clinica.odonto.model.Enums.EstadoFactura;
import com.clinica.odonto.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface FacturaRepository extends JpaRepository<Factura, UUID> {
    long countByFechaFactura(LocalDate fechaFactura);

    List<Factura> findByEstado(EstadoFactura estado);

    List<Factura> findByFechaFacturaBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
