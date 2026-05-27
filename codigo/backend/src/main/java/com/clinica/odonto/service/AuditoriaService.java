package com.clinica.odonto.service;

import com.clinica.odonto.dto.EventoAuditoriaResponse;
import com.clinica.odonto.model.Enums.NivelSeveridadEvento;
import com.clinica.odonto.model.Enums.ResultadoEvento;
import com.clinica.odonto.model.Enums.TipoEventoAuditoria;
import com.clinica.odonto.model.EventoAuditoria;
import com.clinica.odonto.repository.EventoAuditoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AuditoriaService {
    private final EventoAuditoriaRepository repository;

    public AuditoriaService(EventoAuditoriaRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrar(TipoEventoAuditoria tipo, String modulo, String entidad, String idRegistro, String descripcion) {
        EventoAuditoria evento = new EventoAuditoria();
        evento.setTipoEvento(tipo);
        evento.setModuloAfectado(modulo);
        evento.setEntidadAfectada(entidad);
        evento.setIdRegistroAfectado(idRegistro);
        evento.setDescripcionEvento(descripcion);
        evento.setResultadoEvento(ResultadoEvento.EXITO);
        evento.setNivelSeveridad(NivelSeveridadEvento.INFO);
        repository.save(evento);
    }

    public void registrarCreacion(String entidad, UUID idRegistro, String descripcion) {
        registrar(TipoEventoAuditoria.CREACION, "seguridad", entidad, idRegistro.toString(), descripcion);
    }

    public void registrarAcceso(String entidad, UUID idRegistro, String descripcion) {
        registrar(TipoEventoAuditoria.ACCESO, "seguridad", entidad, idRegistro.toString(), descripcion);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarAccesoDenegado(String entidad, UUID idRegistro, String descripcion) {
        EventoAuditoria evento = new EventoAuditoria();
        evento.setTipoEvento(TipoEventoAuditoria.ACCESO_DENEGADO);
        evento.setModuloAfectado("seguridad");
        evento.setEntidadAfectada(entidad);
        evento.setIdRegistroAfectado(idRegistro.toString());
        evento.setDescripcionEvento(descripcion);
        evento.setResultadoEvento(ResultadoEvento.ERROR);
        evento.setNivelSeveridad(NivelSeveridadEvento.ADVERTENCIA);
        repository.save(evento);
    }

    @Transactional(readOnly = true)
    public List<EventoAuditoriaResponse> recientes() {
        return repository.findTop50ByOrderByFechaEventoDesc().stream()
                .map(this::toResponse)
                .toList();
    }

    private EventoAuditoriaResponse toResponse(EventoAuditoria evento) {
        return new EventoAuditoriaResponse(
                evento.getIdEvento(),
                evento.getTipoEvento(),
                evento.getModuloAfectado(),
                evento.getEntidadAfectada(),
                evento.getIdRegistroAfectado(),
                evento.getDescripcionEvento(),
                evento.getFechaEvento(),
                evento.getResultadoEvento(),
                evento.getNivelSeveridad());
    }
}
