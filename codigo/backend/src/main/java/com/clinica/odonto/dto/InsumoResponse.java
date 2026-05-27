package com.clinica.odonto.dto;

import com.clinica.odonto.model.Enums.CategoriaInsumo;
import com.clinica.odonto.model.Enums.EstadoInsumo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record InsumoResponse(
        UUID idInsumo,
        String codigoInsumo,
        String nombreProducto,
        CategoriaInsumo categoria,
        BigDecimal precioCompra,
        BigDecimal precioVenta,
        int stockActual,
        int stockMinimo,
        String ubicacionAlmacen,
        LocalDate fechaCaducidad,
        EstadoInsumo estado,
        boolean bajoStock
) {
}
