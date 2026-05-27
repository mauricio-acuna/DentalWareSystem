package com.clinica.odonto.model;

import com.clinica.odonto.model.Enums.FormaPago;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "pagos", uniqueConstraints = @UniqueConstraint(name = "uk_pago_recibo", columnNames = "numeroRecibo"))
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idPago;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_factura", nullable = false)
    private Factura factura;

    @Column(nullable = false)
    private String numeroRecibo;

    @Column(nullable = false)
    private LocalDate fechaPago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormaPago formaPago;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal importePagado;

    private String numeroReferenciaTransaccion;
}
