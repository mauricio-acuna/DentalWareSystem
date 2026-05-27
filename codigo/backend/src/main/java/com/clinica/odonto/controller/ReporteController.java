package com.clinica.odonto.controller;

import com.clinica.odonto.dto.ReporteActividadResponse;
import com.clinica.odonto.dto.ReporteIngresosResponse;
import com.clinica.odonto.service.ReporteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/reportes")
public class ReporteController {
    private final ReporteService service;

    public ReporteController(ReporteService service) {
        this.service = service;
    }

    @GetMapping("/ingresos")
    public ReporteIngresosResponse ingresos(@RequestParam LocalDate fechaInicio, @RequestParam LocalDate fechaFin) {
        return service.ingresos(fechaInicio, fechaFin);
    }

    @GetMapping("/actividad")
    public ReporteActividadResponse actividad(@RequestParam LocalDate fechaInicio, @RequestParam LocalDate fechaFin) {
        return service.actividad(fechaInicio, fechaFin);
    }
}
