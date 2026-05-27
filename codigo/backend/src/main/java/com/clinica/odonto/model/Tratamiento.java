package com.clinica.odonto.model;

import com.clinica.odonto.model.Enums.ClasificacionTratamiento;
import com.clinica.odonto.model.Enums.EstadoTratamiento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tratamientos", uniqueConstraints = @UniqueConstraint(name = "uk_tratamiento_codigo", columnNames = "codigoTratamiento"))
public class Tratamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idTratamiento;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cita")
    private Cita cita;

    @Column(nullable = false)
    private String codigoTratamiento;

    @NotBlank
    @Column(nullable = false)
    private String nombreTratamiento;

    @Lob
    private String descripcionDetallada;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClasificacionTratamiento clasificacion;

    private String dienteAfectado;
    private String superficieDiente;

    @Lob
    private String diagnosticoAsociado;
    @Lob
    private String planTratamiento;

    @Column(precision = 12, scale = 2)
    private BigDecimal presupuestoInicial;
    @Column(precision = 12, scale = 2)
    private BigDecimal costeReal;
    @Column(precision = 12, scale = 2)
    private BigDecimal coberturaSeguro;
    @Column(precision = 12, scale = 2)
    private BigDecimal copagoPaciente;

    private Integer duracionEstimadaMinutos;
    private Integer duracionRealMinutos;
    private LocalDate fechaInicio;
    private LocalDate fechaCompletacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTratamiento estado = EstadoTratamiento.PENDIENTE;

    private UUID empleadoResponsable;

    @Lob
    private String complicaciones;
    @Lob
    private String resultadoClinico;
    @Lob
    private String observaciones;
    private LocalDate fechaProximoControl;

    @OneToMany(mappedBy = "tratamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SesionTratamiento> sesiones = new ArrayList<>();

    private Instant fechaCreacion;
    private Instant fechaModificacion;

    @PrePersist
    void prePersist() {
        fechaCreacion = Instant.now();
        fechaModificacion = fechaCreacion;
        if (fechaInicio == null) {
            fechaInicio = LocalDate.now();
        }
    }

    @PreUpdate
    void preUpdate() {
        fechaModificacion = Instant.now();
    }
}
