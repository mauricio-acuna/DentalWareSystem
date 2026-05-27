package com.clinica.odonto.model;

import com.clinica.odonto.model.Enums.SeveridadDiagnostico;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "diagnosticos")
public class Diagnostico {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idDiagnostico;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cita")
    private Cita cita;

    @Column(nullable = false)
    private String codigoCie10;

    @Lob
    @Column(nullable = false)
    private String descripcionDiagnostico;

    private String dienteAfectado;

    @Column(nullable = false)
    private LocalDate fechaDiagnostico;

    private UUID empleadoDiagnosticador;

    @Enumerated(EnumType.STRING)
    private SeveridadDiagnostico severidad;

    private boolean confirmadoLaboratorio;

    @Lob
    private String hallazgosRadiologicos;
    @Lob
    private String planTratamientoRecomendado;
    private boolean urgenciaTratamiento;
    private LocalDate fechaResolucion;
    private Instant fechaCreacion;

    @PrePersist
    void prePersist() {
        fechaCreacion = Instant.now();
        if (fechaDiagnostico == null) {
            fechaDiagnostico = LocalDate.now();
        }
    }
}
