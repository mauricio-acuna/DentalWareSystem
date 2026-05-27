package com.clinica.odonto.model;

import com.clinica.odonto.model.Enums.EstadoPaciente;
import com.clinica.odonto.model.Enums.Genero;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "pacientes", uniqueConstraints = {
        @UniqueConstraint(name = "uk_paciente_identificacion", columnNames = "numeroIdentificacion"),
        @UniqueConstraint(name = "uk_paciente_email", columnNames = "email")
})
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idPaciente;

    @NotBlank
    @Pattern(regexp = "^[A-Z0-9]{8,12}$")
    @Column(nullable = false, length = 12)
    private String numeroIdentificacion;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String nombreCompleto;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String apellidos;

    @NotNull
    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Email
    @NotBlank
    @Column(nullable = false)
    private String email;

    private String telefonoMovil;
    private String calle;
    private String numero;
    private String codigoPostal;
    private String ciudad;
    private String provincia;
    private String pais;

    @Column(nullable = false)
    private boolean consentimientoRgpd;

    @Lob
    private String historialAlergias;

    @Lob
    private String patologiasBase;

    @Lob
    private String medicamentosActuales;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPaciente estado = EstadoPaciente.ACTIVO;

    @Column(nullable = false, updatable = false)
    private Instant fechaAlta;
    private Instant fechaBaja;
    private Instant fechaModificacion;

    @PrePersist
    void prePersist() {
        fechaAlta = Instant.now();
        fechaModificacion = fechaAlta;
    }

    @PreUpdate
    void preUpdate() {
        fechaModificacion = Instant.now();
    }
}
