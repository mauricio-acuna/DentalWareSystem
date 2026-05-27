package com.clinica.odonto.controller;

import com.clinica.odonto.dto.PacienteRequest;
import com.clinica.odonto.dto.PacienteResponse;
import com.clinica.odonto.model.Enums.EstadoPaciente;
import com.clinica.odonto.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PacienteResponse crear(@Valid @RequestBody PacienteRequest request) {
        return service.crear(request);
    }

    @GetMapping("/{id}")
    public PacienteResponse obtener(@PathVariable UUID id) {
        return service.obtener(id);
    }

    @GetMapping
    public List<PacienteResponse> listar(@RequestParam(required = false) EstadoPaciente estado) {
        return service.listar(estado);
    }

    @PutMapping("/{id}")
    public PacienteResponse actualizar(@PathVariable UUID id, @Valid @RequestBody PacienteRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PacienteResponse eliminar(@PathVariable UUID id) {
        return service.solicitarEliminacion(id);
    }
}
