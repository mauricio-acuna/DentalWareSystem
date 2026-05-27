package com.clinica.odonto.repository;

import com.clinica.odonto.model.Enums.CategoriaInsumo;
import com.clinica.odonto.model.Enums.EstadoInsumo;
import com.clinica.odonto.model.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InsumoRepository extends JpaRepository<Insumo, UUID> {
    boolean existsByCodigoInsumo(String codigoInsumo);

    List<Insumo> findByCategoriaAndEstado(CategoriaInsumo categoria, EstadoInsumo estado);

    List<Insumo> findByStockActualLessThanEqual(int stockMinimo);
}
