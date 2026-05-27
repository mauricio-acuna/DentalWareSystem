package com.clinica.odonto.controller;

import com.clinica.odonto.dto.FacturaRequest;
import com.clinica.odonto.dto.FacturaResponse;
import com.clinica.odonto.dto.PagoRequest;
import com.clinica.odonto.dto.PagoResponse;
import com.clinica.odonto.service.FacturaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/facturas")
public class FacturaController {
    private final FacturaService service;

    public FacturaController(FacturaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FacturaResponse crear(@Valid @RequestBody FacturaRequest request) {
        return service.crear(request);
    }

    @GetMapping("/{id}")
    public FacturaResponse obtener(@PathVariable UUID id) {
        return service.obtener(id);
    }

    @GetMapping("/pendientes")
    public List<FacturaResponse> pendientes() {
        return service.listarPendientes();
    }

    @PostMapping("/{id}/pagos")
    @ResponseStatus(HttpStatus.CREATED)
    public PagoResponse pagar(@PathVariable UUID id, @Valid @RequestBody PagoRequest request) {
        return service.registrarPago(id, request);
    }
}
