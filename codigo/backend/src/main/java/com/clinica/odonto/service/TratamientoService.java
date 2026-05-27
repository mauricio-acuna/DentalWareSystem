package com.clinica.odonto.service;

import com.clinica.odonto.dto.*;
import com.clinica.odonto.exception.BusinessRuleException;
import com.clinica.odonto.exception.NotFoundException;
import com.clinica.odonto.model.Cita;
import com.clinica.odonto.model.Enums.EstadoTratamiento;
import com.clinica.odonto.model.Enums.TipoEventoAuditoria;
import com.clinica.odonto.model.Paciente;
import com.clinica.odonto.model.SesionTratamiento;
import com.clinica.odonto.model.Tratamiento;
import com.clinica.odonto.repository.CitaRepository;
import com.clinica.odonto.repository.SesionTratamientoRepository;
import com.clinica.odonto.repository.TratamientoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TratamientoService {
    private final TratamientoRepository tratamientoRepository;
    private final SesionTratamientoRepository sesionRepository;
    private final CitaRepository citaRepository;
    private final PacienteService pacienteService;
    private final AuditoriaService auditoriaService;

    public TratamientoService(
            TratamientoRepository tratamientoRepository,
            SesionTratamientoRepository sesionRepository,
            CitaRepository citaRepository,
            PacienteService pacienteService,
            AuditoriaService auditoriaService) {
        this.tratamientoRepository = tratamientoRepository;
        this.sesionRepository = sesionRepository;
        this.citaRepository = citaRepository;
        this.pacienteService = pacienteService;
        this.auditoriaService = auditoriaService;
    }

    @Transactional
    public TratamientoResponse crear(UUID idPaciente, TratamientoRequest request) {
        Paciente paciente = pacienteService.find(idPaciente);
        Cita cita = request.idCita() == null ? null : citaRepository.findById(request.idCita())
                .orElseThrow(() -> new NotFoundException("Cita no encontrada"));
        if (cita != null && !cita.getPaciente().getIdPaciente().equals(idPaciente)) {
            throw new BusinessRuleException("La cita no pertenece al paciente indicado");
        }

        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setPaciente(paciente);
        tratamiento.setCita(cita);
        tratamiento.setCodigoTratamiento(generarCodigo(LocalDate.now()));
        tratamiento.setNombreTratamiento(request.nombreTratamiento());
        tratamiento.setDescripcionDetallada(request.descripcionDetallada());
        tratamiento.setClasificacion(request.clasificacion());
        tratamiento.setDienteAfectado(request.dienteAfectado());
        tratamiento.setSuperficieDiente(request.superficieDiente());
        tratamiento.setDiagnosticoAsociado(request.diagnosticoAsociado());
        tratamiento.setPlanTratamiento(request.planTratamiento());
        tratamiento.setPresupuestoInicial(request.presupuesto());
        tratamiento.setCoberturaSeguro(request.coberturaSeguro());
        tratamiento.setCopagoPaciente(request.copagoPaciente());
        tratamiento.setDuracionEstimadaMinutos(request.duracionEstimadaMinutos());
        tratamiento.setEmpleadoResponsable(request.empleadoResponsable());
        tratamiento = tratamientoRepository.save(tratamiento);
        auditoriaService.registrar(TipoEventoAuditoria.CREACION, "TRATAMIENTOS", "Tratamiento",
                tratamiento.getIdTratamiento().toString(), "Tratamiento creado");
        return toResponse(tratamiento);
    }

    @Transactional(readOnly = true)
    public List<TratamientoResponse> porPaciente(UUID idPaciente) {
        return tratamientoRepository.findByPacienteIdPaciente(idPaciente).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TratamientoResponse obtener(UUID idTratamiento) {
        return toResponse(find(idTratamiento));
    }

    @Transactional
    public SesionTratamientoResponse registrarSesion(UUID idTratamiento, SesionTratamientoRequest request) {
        Tratamiento tratamiento = find(idTratamiento);
        if (tratamiento.getEstado() == EstadoTratamiento.COMPLETADO || tratamiento.getEstado() == EstadoTratamiento.CANCELADO) {
            throw new BusinessRuleException("No se pueden registrar sesiones en un tratamiento cerrado");
        }
        SesionTratamiento sesion = new SesionTratamiento();
        sesion.setTratamiento(tratamiento);
        sesion.setNumeroSesion(request.numeroSesion());
        sesion.setFechaRealizacion(request.fechaRealizacion());
        sesion.setTiempoReal(request.tiempoReal());
        sesion.setProcedimientos(join(request.procedimientos()));
        sesion.setComplicaciones(request.complicaciones());
        sesion.setProximaSesion(request.proximaSesion());
        sesion.setEmpleadoResponsable(request.empleadoResponsable());
        tratamiento.setEstado(EstadoTratamiento.EN_CURSO);
        if (request.tiempoReal() != null) {
            int total = tratamiento.getDuracionRealMinutos() == null ? 0 : tratamiento.getDuracionRealMinutos();
            tratamiento.setDuracionRealMinutos(total + request.tiempoReal());
        }
        tratamientoRepository.save(tratamiento);
        sesion = sesionRepository.save(sesion);
        auditoriaService.registrar(TipoEventoAuditoria.CREACION, "TRATAMIENTOS", "SesionTratamiento",
                sesion.getIdSesion().toString(), "Sesion de tratamiento registrada");
        return toSesionResponse(sesion);
    }

    @Transactional
    public TratamientoResponse completar(UUID idTratamiento, CompletarTratamientoRequest request) {
        Tratamiento tratamiento = find(idTratamiento);
        if (tratamiento.getSesiones().isEmpty()) {
            throw new BusinessRuleException("No se puede completar un tratamiento sin sesiones registradas");
        }
        tratamiento.setEstado(EstadoTratamiento.COMPLETADO);
        tratamiento.setFechaCompletacion(LocalDate.now());
        tratamiento.setResultadoClinico(request.resultadoClinico());
        tratamiento.setObservaciones(request.observaciones());
        tratamiento.setFechaProximoControl(request.proximoControl());
        tratamiento = tratamientoRepository.save(tratamiento);
        auditoriaService.registrar(TipoEventoAuditoria.MODIFICACION, "TRATAMIENTOS", "Tratamiento",
                tratamiento.getIdTratamiento().toString(), "Tratamiento completado");
        return toResponse(tratamiento);
    }

    Tratamiento find(UUID idTratamiento) {
        return tratamientoRepository.findById(idTratamiento)
                .orElseThrow(() -> new NotFoundException("Tratamiento no encontrado"));
    }

    private String generarCodigo(LocalDate fecha) {
        long secuencia = tratamientoRepository.countByFechaInicio(fecha) + 1;
        return "TRT-" + fecha.getYear() + String.format("%02d", fecha.getMonthValue()) + "-" + String.format("%04d", secuencia);
    }

    private String join(List<String> values) {
        return values == null ? null : String.join("\n", values);
    }

    private TratamientoResponse toResponse(Tratamiento tratamiento) {
        List<SesionTratamientoResponse> sesiones = tratamiento.getSesiones().stream()
                .map(this::toSesionResponse)
                .toList();
        return new TratamientoResponse(
                tratamiento.getIdTratamiento(),
                tratamiento.getPaciente().getIdPaciente(),
                tratamiento.getCita() == null ? null : tratamiento.getCita().getIdCita(),
                tratamiento.getCodigoTratamiento(),
                tratamiento.getNombreTratamiento(),
                tratamiento.getClasificacion(),
                tratamiento.getDienteAfectado(),
                tratamiento.getPresupuestoInicial(),
                tratamiento.getEstado(),
                tratamiento.getFechaInicio(),
                tratamiento.getFechaCompletacion(),
                tratamiento.getResultadoClinico(),
                tratamiento.getFechaProximoControl(),
                sesiones);
    }

    private SesionTratamientoResponse toSesionResponse(SesionTratamiento sesion) {
        return new SesionTratamientoResponse(
                sesion.getIdSesion(),
                sesion.getNumeroSesion(),
                sesion.getFechaRealizacion(),
                sesion.getTiempoReal(),
                sesion.getProcedimientos(),
                sesion.getComplicaciones(),
                sesion.getProximaSesion(),
                sesion.getEmpleadoResponsable());
    }
}
