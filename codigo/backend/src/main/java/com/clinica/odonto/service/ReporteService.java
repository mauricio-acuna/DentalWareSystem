package com.clinica.odonto.service;

import com.clinica.odonto.dto.ReporteActividadResponse;
import com.clinica.odonto.dto.ReporteIngresosResponse;
import com.clinica.odonto.model.Enums.EstadoCita;
import com.clinica.odonto.model.Enums.EstadoTratamiento;
import com.clinica.odonto.model.Factura;
import com.clinica.odonto.model.Pago;
import com.clinica.odonto.repository.CitaRepository;
import com.clinica.odonto.repository.FacturaRepository;
import com.clinica.odonto.repository.PagoRepository;
import com.clinica.odonto.repository.TratamientoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ReporteService {
    private final FacturaRepository facturaRepository;
    private final PagoRepository pagoRepository;
    private final CitaRepository citaRepository;
    private final TratamientoRepository tratamientoRepository;

    public ReporteService(
            FacturaRepository facturaRepository,
            PagoRepository pagoRepository,
            CitaRepository citaRepository,
            TratamientoRepository tratamientoRepository) {
        this.facturaRepository = facturaRepository;
        this.pagoRepository = pagoRepository;
        this.citaRepository = citaRepository;
        this.tratamientoRepository = tratamientoRepository;
    }

    @Transactional(readOnly = true)
    public ReporteIngresosResponse ingresos(LocalDate fechaInicio, LocalDate fechaFin) {
        BigDecimal facturado = facturaRepository.findByFechaFacturaBetween(fechaInicio, fechaFin).stream()
                .map(Factura::getImporteTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal pagado = pagoRepository.findByFechaPagoBetween(fechaInicio, fechaFin).stream()
                .map(Pago::getImportePagado)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal pendiente = facturado.subtract(pagado).max(BigDecimal.ZERO);
        return new ReporteIngresosResponse(fechaInicio, fechaFin, facturado, pagado, pendiente);
    }

    @Transactional(readOnly = true)
    public ReporteActividadResponse actividad(LocalDate fechaInicio, LocalDate fechaFin) {
        return new ReporteActividadResponse(
                citaRepository.countByFechaCitaBetween(fechaInicio, fechaFin),
                citaRepository.countByEstadoAndFechaCitaBetween(EstadoCita.COMPLETADA, fechaInicio, fechaFin),
                citaRepository.countByEstadoAndFechaCitaBetween(EstadoCita.CANCELADA, fechaInicio, fechaFin),
                citaRepository.countByEstadoAndFechaCitaBetween(EstadoCita.NO_PRESENTADO, fechaInicio, fechaFin),
                tratamientoRepository.countByEstado(EstadoTratamiento.EN_CURSO),
                tratamientoRepository.countByEstado(EstadoTratamiento.COMPLETADO));
    }
}
