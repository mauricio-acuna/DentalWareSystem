package com.clinica.odonto.model;

import com.clinica.odonto.model.Enums.EstadoFactura;
import com.clinica.odonto.model.Enums.FormaPago;
import com.clinica.odonto.model.Enums.TipoFactura;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "facturas", uniqueConstraints = @UniqueConstraint(name = "uk_factura_numero", columnNames = "numeroFactura"))
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idFactura;

    @Column(nullable = false)
    private String numeroFactura;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @Column(nullable = false)
    private LocalDate fechaFactura;

    @Column(nullable = false)
    private LocalDate fechaVencimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoFactura tipoFactura = TipoFactura.ORDINARIA;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoFactura estado = EstadoFactura.EMITIDA;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineaFactura> lineas = new ArrayList<>();

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal baseImponible = BigDecimal.ZERO;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal iva = BigDecimal.ZERO;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal importeTotal = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private FormaPago formaPago;
}
