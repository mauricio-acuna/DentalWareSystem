package com.clinica.odonto.controller;

import com.clinica.odonto.dto.EventoAuditoriaResponse;
import com.clinica.odonto.service.AuditoriaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auditoria")
public class AuditoriaController {
    private final AuditoriaService service;

    public AuditoriaController(AuditoriaService service) {
        this.service = service;
    }

    @GetMapping("/eventos")
    public List<EventoAuditoriaResponse> recientes() {
        return service.recientes();
    }
}
