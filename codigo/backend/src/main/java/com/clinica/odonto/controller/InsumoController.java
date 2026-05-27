package com.clinica.odonto.controller;

import com.clinica.odonto.dto.InsumoRequest;
import com.clinica.odonto.dto.InsumoResponse;
import com.clinica.odonto.model.Enums.CategoriaInsumo;
import com.clinica.odonto.model.Enums.EstadoInsumo;
import com.clinica.odonto.service.InsumoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/inventario")
public class InsumoController {
    private final InsumoService service;

    public InsumoController(InsumoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InsumoResponse crear(@Valid @RequestBody InsumoRequest request) {
        return service.crear(request);
    }

    @GetMapping
    public List<InsumoResponse> listar(
            @RequestParam(required = false) CategoriaInsumo categoria,
            @RequestParam(required = false) EstadoInsumo estado) {
        return service.listar(categoria, estado);
    }

    @PatchMapping("/{id}/stock")
    public InsumoResponse ajustarStock(@PathVariable UUID id, @RequestParam int cantidad) {
        return service.ajustarStock(id, cantidad);
    }
}
