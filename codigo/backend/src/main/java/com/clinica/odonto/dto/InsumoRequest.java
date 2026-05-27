package com.clinica.odonto.dto;

import com.clinica.odonto.model.Enums.CategoriaInsumo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InsumoRequest(
        @NotBlank String codigoInsumo,
        @NotBlank String nombreProducto,
        @NotNull CategoriaInsumo categoria,
        String fabricante,
        String codigoLote,
        LocalDate fechaCaducidad,
        @NotNull @PositiveOrZero BigDecimal precioCompra,
        @PositiveOrZero BigDecimal precioVenta,
        int stockActual,
        int stockMinimo,
        int stockOptimo,
        String ubicacionAlmacen,
        boolean certificacionCE,
        boolean biocompatibilidad,
        boolean requiereEsterilizacion
) {
}
