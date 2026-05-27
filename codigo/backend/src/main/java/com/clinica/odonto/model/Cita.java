package com.clinica.odonto.model;

import com.clinica.odonto.model.Enums.EstadoCita;
import com.clinica.odonto.model.Enums.Prioridad;
import com.clinica.odonto.model.Enums.TipoCita;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idCita;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    private UUID idDentista;
    private UUID idHigienista;

    @NotNull
    @Column(nullable = false)
    private LocalDate fechaCita;

    @NotNull
    @Column(nullable = false)
    private LocalTime horaInicio;

    @NotNull
    @Column(nullable = false)
    private LocalTime horaFin;

    private Integer duracionEstimada;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCita tipoCita;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCita estado = EstadoCita.PROGRAMADA;

    @Lob
    private String motivoConsulta;
    @Lob
    private String resultadoCita;
    private LocalDate proximoSeguimiento;

    @Enumerated(EnumType.STRING)
    private Prioridad prioridad = Prioridad.MEDIA;

    private Instant fechaConfirmacionPaciente;
    private Instant fechaCreacion;
    private Instant fechaModificacion;

    @PrePersist
    void prePersist() {
        fechaCreacion = Instant.now();
        fechaModificacion = fechaCreacion;
    }

    @PreUpdate
    void preUpdate() {
        fechaModificacion = Instant.now();
    }
}
