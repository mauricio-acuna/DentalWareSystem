package com.clinica.odonto.controller;

import com.clinica.odonto.dto.DiagnosticoRequest;
import com.clinica.odonto.dto.DiagnosticoResponse;
import com.clinica.odonto.service.DiagnosticoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pacientes/{idPaciente}/diagnosticos")
public class DiagnosticoController {
    private final DiagnosticoService service;

    public DiagnosticoController(DiagnosticoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DiagnosticoResponse crear(@PathVariable UUID idPaciente, @Valid @RequestBody DiagnosticoRequest request) {
        return service.crear(idPaciente, request);
    }

    @GetMapping
    public List<DiagnosticoResponse> porPaciente(@PathVariable UUID idPaciente) {
        return service.porPaciente(idPaciente);
    }
}
