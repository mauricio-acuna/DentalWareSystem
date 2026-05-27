package com.clinica.odonto.controller;

import com.clinica.odonto.dto.CompletarTratamientoRequest;
import com.clinica.odonto.dto.SesionTratamientoRequest;
import com.clinica.odonto.dto.SesionTratamientoResponse;
import com.clinica.odonto.dto.TratamientoRequest;
import com.clinica.odonto.dto.TratamientoResponse;
import com.clinica.odonto.service.TratamientoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
public class TratamientoController {
    private final TratamientoService service;

    public TratamientoController(TratamientoService service) {
        this.service = service;
    }

    @PostMapping("/pacientes/{idPaciente}/tratamientos")
    @ResponseStatus(HttpStatus.CREATED)
    public TratamientoResponse crear(@PathVariable UUID idPaciente, @Valid @RequestBody TratamientoRequest request) {
        return service.crear(idPaciente, request);
    }

    @GetMapping("/pacientes/{idPaciente}/tratamientos")
    public List<TratamientoResponse> porPaciente(@PathVariable UUID idPaciente) {
        return service.porPaciente(idPaciente);
    }

    @GetMapping("/tratamientos/{idTratamiento}")
    public TratamientoResponse obtener(@PathVariable UUID idTratamiento) {
        return service.obtener(idTratamiento);
    }

    @PostMapping("/tratamientos/{idTratamiento}/sesiones")
    @ResponseStatus(HttpStatus.CREATED)
    public SesionTratamientoResponse registrarSesion(
            @PathVariable UUID idTratamiento,
            @Valid @RequestBody SesionTratamientoRequest request) {
        return service.registrarSesion(idTratamiento, request);
    }

    @PutMapping("/tratamientos/{idTratamiento}/completar")
    public TratamientoResponse completar(
            @PathVariable UUID idTratamiento,
            @Valid @RequestBody CompletarTratamientoRequest request) {
        return service.completar(idTratamiento, request);
    }
}
