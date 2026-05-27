package com.clinica.odonto.controller;

import com.clinica.odonto.dto.CitaRequest;
import com.clinica.odonto.dto.CitaResponse;
import com.clinica.odonto.dto.DisponibilidadResponse;
import com.clinica.odonto.service.CitaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping
public class CitaController {
    private final CitaService service;

    public CitaController(CitaService service) {
        this.service = service;
    }

    @PostMapping("/citas")
    @ResponseStatus(HttpStatus.CREATED)
    public CitaResponse crear(@Valid @RequestBody CitaRequest request) {
        return service.crear(request);
    }

    @GetMapping("/citas/{id}")
    public CitaResponse obtener(@PathVariable UUID id) {
        return service.obtener(id);
    }

    @GetMapping("/pacientes/{idPaciente}/citas")
    public List<CitaResponse> porPaciente(@PathVariable UUID idPaciente) {
        return service.listarPorPaciente(idPaciente);
    }

    @PostMapping("/citas/{id}/confirmar")
    public CitaResponse confirmar(@PathVariable UUID id) {
        return service.confirmar(id);
    }

    @PutMapping("/citas/{id}")
    public CitaResponse reprogramar(@PathVariable UUID id, @Valid @RequestBody CitaRequest request) {
        return service.reprogramar(id, request);
    }

    @GetMapping("/disponibilidad")
    public DisponibilidadResponse disponibilidad(
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin,
            @RequestParam(required = false) UUID idDentista) {
        return service.disponibilidad(fechaInicio, fechaFin, idDentista);
    }
}
