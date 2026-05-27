package com.clinica.odonto.service;

import com.clinica.odonto.dto.DireccionRequest;
import com.clinica.odonto.dto.PacienteRequest;
import com.clinica.odonto.dto.PacienteResponse;
import com.clinica.odonto.exception.BusinessRuleException;
import com.clinica.odonto.exception.NotFoundException;
import com.clinica.odonto.model.Enums.EstadoPaciente;
import com.clinica.odonto.model.Paciente;
import com.clinica.odonto.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Service
public class PacienteService {
    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PacienteResponse crear(PacienteRequest request) {
        if (!request.consentimientoRGPD()) {
            throw new BusinessRuleException("El consentimiento RGPD es obligatorio para crear pacientes");
        }
        if (repository.existsByNumeroIdentificacion(request.numeroIdentificacion())) {
            throw new BusinessRuleException("Ya existe un paciente con ese numero de identificacion");
        }
        if (repository.existsByEmail(request.email())) {
            throw new BusinessRuleException("Ya existe un paciente con ese email");
        }
        Paciente paciente = new Paciente();
        applyRequest(paciente, request);
        return toResponse(repository.save(paciente));
    }

    @Transactional(readOnly = true)
    public PacienteResponse obtener(UUID id) {
        return toResponse(find(id));
    }

    @Transactional(readOnly = true)
    public List<PacienteResponse> listar(EstadoPaciente estado) {
        List<Paciente> pacientes = estado == null ? repository.findAll() : repository.findByEstado(estado);
        return pacientes.stream().map(this::toResponse).toList();
    }

    @Transactional
    public PacienteResponse actualizar(UUID id, PacienteRequest request) {
        Paciente paciente = find(id);
        applyRequest(paciente, request);
        return toResponse(repository.save(paciente));
    }

    @Transactional
    public PacienteResponse solicitarEliminacion(UUID id) {
        Paciente paciente = find(id);
        paciente.setEstado(EstadoPaciente.PENDIENTE_ELIMINACION);
        paciente.setFechaBaja(java.time.Instant.now());
        return toResponse(repository.save(paciente));
    }

    public Paciente find(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Paciente no encontrado"));
    }

    private void applyRequest(Paciente paciente, PacienteRequest request) {
        if (request.fechaNacimiento().isAfter(LocalDate.now())) {
            throw new BusinessRuleException("La fecha de nacimiento no puede estar en el futuro");
        }
        paciente.setNumeroIdentificacion(request.numeroIdentificacion());
        paciente.setNombreCompleto(request.nombreCompleto());
        paciente.setApellidos(request.apellidos());
        paciente.setFechaNacimiento(request.fechaNacimiento());
        paciente.setGenero(request.genero());
        paciente.setEmail(request.email());
        paciente.setTelefonoMovil(request.telefonoMovil());
        paciente.setConsentimientoRgpd(request.consentimientoRGPD());
        paciente.setHistorialAlergias(join(request.historialAlergias()));
        paciente.setPatologiasBase(join(request.patologiasBase()));
        paciente.setMedicamentosActuales(join(request.medicamentosActuales()));
        DireccionRequest direccion = request.direccion();
        if (direccion != null) {
            paciente.setCalle(direccion.calle());
            paciente.setNumero(direccion.numero());
            paciente.setCodigoPostal(direccion.codigoPostal());
            paciente.setCiudad(direccion.ciudad());
            paciente.setProvincia(direccion.provincia());
            paciente.setPais(direccion.pais());
        }
    }

    private String join(List<String> values) {
        return values == null ? null : String.join("\n", values);
    }

    private PacienteResponse toResponse(Paciente paciente) {
        DireccionRequest direccion = new DireccionRequest(
                paciente.getCalle(),
                paciente.getNumero(),
                paciente.getCodigoPostal(),
                paciente.getCiudad(),
                paciente.getProvincia(),
                paciente.getPais());
        int edad = Period.between(paciente.getFechaNacimiento(), LocalDate.now()).getYears();
        return new PacienteResponse(
                paciente.getIdPaciente(),
                paciente.getNumeroIdentificacion(),
                paciente.getNombreCompleto(),
                paciente.getApellidos(),
                paciente.getFechaNacimiento(),
                edad,
                paciente.getGenero(),
                paciente.getEmail(),
                paciente.getTelefonoMovil(),
                direccion,
                paciente.isConsentimientoRgpd(),
                paciente.getEstado(),
                paciente.getFechaAlta());
    }
}
