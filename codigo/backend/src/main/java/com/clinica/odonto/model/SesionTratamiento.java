package com.clinica.odonto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "sesiones_tratamiento")
public class SesionTratamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idSesion;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tratamiento", nullable = false)
    private Tratamiento tratamiento;

    @Column(nullable = false)
    private int numeroSesion;

    @Column(nullable = false)
    private LocalDate fechaRealizacion;

    private Integer tiempoReal;

    @Lob
    private String procedimientos;
    @Lob
    private String complicaciones;

    private LocalDate proximaSesion;
    private UUID empleadoResponsable;
}
