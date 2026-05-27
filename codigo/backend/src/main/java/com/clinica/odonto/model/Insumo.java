package com.clinica.odonto.model;

import com.clinica.odonto.model.Enums.CategoriaInsumo;
import com.clinica.odonto.model.Enums.EstadoInsumo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "insumos", uniqueConstraints = @UniqueConstraint(name = "uk_insumo_codigo", columnNames = "codigoInsumo"))
public class Insumo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idInsumo;

    @NotBlank
    @Column(nullable = false)
    private String codigoInsumo;

    @NotBlank
    @Column(nullable = false)
    private String nombreProducto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaInsumo categoria;

    private String fabricante;
    private String codigoLote;
    private LocalDate fechaCaducidad;

    @NotNull
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precioCompra;

    @Column(precision = 12, scale = 2)
    private BigDecimal precioVenta;

    private int stockActual;
    private int stockMinimo;
    private int stockOptimo;
    private String ubicacionAlmacen;
    private boolean certificacionCe;
    private boolean biocompatibilidad;
    private boolean requiereEsterilizacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoInsumo estado = EstadoInsumo.DISPONIBLE;
}
