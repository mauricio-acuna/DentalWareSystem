package com.clinica.odonto.model;

import com.clinica.odonto.model.Enums.NivelSeveridadEvento;
import com.clinica.odonto.model.Enums.ResultadoEvento;
import com.clinica.odonto.model.Enums.TipoEventoAuditoria;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "eventos_auditoria")
public class EventoAuditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idEvento;

    private UUID idUsuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEventoAuditoria tipoEvento;

    @Column(nullable = false)
    private String moduloAfectado;

    @Column(nullable = false)
    private String entidadAfectada;

    private String idRegistroAfectado;

    @Lob
    private String descripcionEvento;

    private String ipOrigen;
    private String navegadorUserAgent;

    @Column(nullable = false)
    private Instant fechaEvento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultadoEvento resultadoEvento = ResultadoEvento.EXITO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelSeveridadEvento nivelSeveridad = NivelSeveridadEvento.INFO;

    @PrePersist
    void prePersist() {
        if (fechaEvento == null) {
            fechaEvento = Instant.now();
        }
    }
}
