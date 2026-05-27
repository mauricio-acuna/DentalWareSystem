package com.clinica.odonto.service;

import com.clinica.odonto.dto.CitaRequest;
import com.clinica.odonto.dto.CitaResponse;
import com.clinica.odonto.exception.BusinessRuleException;
import com.clinica.odonto.exception.NotFoundException;
import com.clinica.odonto.model.Cita;
import com.clinica.odonto.model.Enums.EstadoCita;
import com.clinica.odonto.repository.CitaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CitaService {
    private final CitaRepository repository;
    private final PacienteService pacienteService;

    public CitaService(CitaRepository repository, PacienteService pacienteService) {
        this.repository = repository;
        this.pacienteService = pacienteService;
    }

    @Transactional
    public CitaResponse crear(CitaRequest request) {
        validarHorario(request, null);
        Cita cita = new Cita();
        cita.setPaciente(pacienteService.find(request.idPaciente()));
        applyRequest(cita, request);
        return toResponse(repository.save(cita));
    }

    @Transactional(readOnly = true)
    public CitaResponse obtener(UUID id) {
        return toResponse(find(id));
    }

    @Transactional(readOnly = true)
    public List<CitaResponse> listarPorPaciente(UUID idPaciente) {
        return repository.findByPacienteIdPaciente(idPaciente).stream().map(this::toResponse).toList();
    }

    @Transactional
    public CitaResponse confirmar(UUID id) {
        Cita cita = find(id);
        if (cita.getEstado() == EstadoCita.CANCELADA) {
            throw new BusinessRuleException("No se puede confirmar una cita cancelada");
        }
        cita.setEstado(EstadoCita.CONFIRMADA);
        cita.setFechaConfirmacionPaciente(Instant.now());
        return toResponse(repository.save(cita));
    }

    @Transactional
    public CitaResponse reprogramar(UUID id, CitaRequest request) {
        Cita cita = find(id);
        validarHorario(request, id);
        applyRequest(cita, request);
        return toResponse(repository.save(cita));
    }

    private Cita find(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Cita no encontrada"));
    }

    private void validarHorario(CitaRequest request, UUID citaActual) {
        if (request.fechaCita().isBefore(LocalDate.now())) {
            throw new BusinessRuleException("La cita no puede programarse en el pasado");
        }
        if (!request.horaFin().isAfter(request.horaInicio())) {
            throw new BusinessRuleException("La hora de fin debe ser posterior a la hora de inicio");
        }
        long minutos = Duration.between(request.horaInicio(), request.horaFin()).toMinutes();
        if (minutos < 15 || minutos > 240) {
            throw new BusinessRuleException("La duracion debe estar entre 15 y 240 minutos");
        }
        if (request.idDentista() != null) {
            boolean existeSolape = repository.existsByIdDentistaAndFechaCitaAndHoraInicioLessThanAndHoraFinGreaterThanAndEstadoNot(
                    request.idDentista(), request.fechaCita(), request.horaFin(), request.horaInicio(), EstadoCita.CANCELADA);
            if (existeSolape && citaActual == null) {
                throw new BusinessRuleException("El dentista ya tiene una cita en ese horario");
            }
        }
    }

    private void applyRequest(Cita cita, CitaRequest request) {
        cita.setIdDentista(request.idDentista());
        cita.setIdHigienista(request.idHigienista());
        cita.setFechaCita(request.fechaCita());
        cita.setHoraInicio(request.horaInicio());
        cita.setHoraFin(request.horaFin());
        cita.setDuracionEstimada((int) Duration.between(request.horaInicio(), request.horaFin()).toMinutes());
        cita.setTipoCita(request.tipoCita());
        cita.setMotivoConsulta(request.motivoConsulta());
        cita.setPrioridad(request.prioridad());
    }

    private CitaResponse toResponse(Cita cita) {
        return new CitaResponse(
                cita.getIdCita(),
                cita.getPaciente().getIdPaciente(),
                cita.getIdDentista(),
                cita.getFechaCita(),
                cita.getHoraInicio(),
                cita.getHoraFin(),
                cita.getTipoCita(),
                cita.getEstado(),
                cita.getMotivoConsulta(),
                cita.getPrioridad());
    }
}
