package com.clinica.odonto.repository;

import com.clinica.odonto.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PagoRepository extends JpaRepository<Pago, UUID> {
    long countByFacturaIdFactura(UUID idFactura);

    List<Pago> findByFacturaIdFactura(UUID idFactura);
}
