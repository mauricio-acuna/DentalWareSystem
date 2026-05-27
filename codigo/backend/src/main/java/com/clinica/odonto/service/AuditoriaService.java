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
