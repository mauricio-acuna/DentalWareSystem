package com.clinica.odonto.service;

import com.clinica.odonto.dto.DiagnosticoRequest;
import com.clinica.odonto.dto.DiagnosticoResponse;
import com.clinica.odonto.exception.BusinessRuleException;
import com.clinica.odonto.exception.NotFoundException;
import com.clinica.odonto.model.Cita;
import com.clinica.odonto.model.Diagnostico;
import com.clinica.odonto.model.Enums.TipoEventoAuditoria;
import com.clinica.odonto.model.Paciente;
import com.clinica.odonto.repository.CitaRepository;
import com.clinica.odonto.repository.DiagnosticoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DiagnosticoService {
    private final DiagnosticoRepository repository;
    private final CitaRepository citaRepository;
    private final PacienteService pacienteService;
    private final AuditoriaService auditoriaService;

    public DiagnosticoService(
            DiagnosticoRepository repository,
            CitaRepository citaRepository,
            PacienteService pacienteService,
            AuditoriaService auditoriaService) {
        this.repository = repository;
        this.citaRepository = citaRepository;
        this.pacienteService = pacienteService;
        this.auditoriaService = auditoriaService;
    }

    @Transactional
    public DiagnosticoResponse crear(UUID idPaciente, DiagnosticoRequest request) {
        Paciente paciente = pacienteService.find(idPaciente);
        Cita cita = request.idCita() == null ? null : citaRepository.findById(request.idCita())
                .orElseThrow(() -> new NotFoundException("Cita no encontrada"));
        if (cita != null && !cita.getPaciente().getIdPaciente().equals(idPaciente)) {
            throw new BusinessRuleException("La cita no pertenece al paciente indicado");
        }
        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setPaciente(paciente);
        diagnostico.setCita(cita);
        diagnostico.setCodigoCie10(request.codigoCIE10());
        diagnostico.setDescripcionDiagnostico(request.descripcionDiagnostico());
        diagnostico.setDienteAfectado(request.dienteAfectado());
        diagnostico.setFechaDiagnostico(request.fechaDiagnostico());
        diagnostico.setEmpleadoDiagnosticador(request.empleadoDiagnosticador());
        diagnostico.setSeveridad(request.severidad());
        diagnostico.setConfirmadoLaboratorio(request.confirmadoLaboratorio());
        diagnostico.setHallazgosRadiologicos(request.hallazgosRadiologicos());
        diagnostico.setPlanTratamientoRecomendado(request.planTratamientoRecomendado());
        diagnostico.setUrgenciaTratamiento(request.urgenciaTratamiento());
        diagnostico = repository.save(diagnostico);
        auditoriaService.registrar(TipoEventoAuditoria.CREACION, "DIAGNOSTICOS", "Diagnostico",
                diagnostico.getIdDiagnostico().toString(), "Diagnostico creado");
        return toResponse(diagnostico);
    }

    @Transactional(readOnly = true)
    public List<DiagnosticoResponse> porPaciente(UUID idPaciente) {
        return repository.findByPacienteIdPaciente(idPaciente).stream().map(this::toResponse).toList();
    }

    private DiagnosticoResponse toResponse(Diagnostico diagnostico) {
        return new DiagnosticoResponse(
                diagnostico.getIdDiagnostico(),
                diagnostico.getPaciente().getIdPaciente(),
                diagnostico.getCita() == null ? null : diagnostico.getCita().getIdCita(),
                diagnostico.getCodigoCie10(),
                diagnostico.getDescripcionDiagnostico(),
                diagnostico.getDienteAfectado(),
                diagnostico.getFechaDiagnostico(),
                diagnostico.getSeveridad(),
                diagnostico.isConfirmadoLaboratorio(),
                diagnostico.getPlanTratamientoRecomendado(),
                diagnostico.isUrgenciaTratamiento());
    }
}
