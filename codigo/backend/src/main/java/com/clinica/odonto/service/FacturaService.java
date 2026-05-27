package com.clinica.odonto.service;

import com.clinica.odonto.dto.*;
import com.clinica.odonto.exception.BusinessRuleException;
import com.clinica.odonto.exception.NotFoundException;
import com.clinica.odonto.model.Enums.EstadoFactura;
import com.clinica.odonto.model.Factura;
import com.clinica.odonto.model.LineaFactura;
import com.clinica.odonto.model.Pago;
import com.clinica.odonto.repository.FacturaRepository;
import com.clinica.odonto.repository.PagoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class FacturaService {
    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    private final FacturaRepository facturaRepository;
    private final PagoRepository pagoRepository;
    private final PacienteService pacienteService;

    public FacturaService(FacturaRepository facturaRepository, PagoRepository pagoRepository, PacienteService pacienteService) {
        this.facturaRepository = facturaRepository;
        this.pagoRepository = pagoRepository;
        this.pacienteService = pacienteService;
    }

    @Transactional
    public FacturaResponse crear(FacturaRequest request) {
        LocalDate hoy = LocalDate.now();
        Factura factura = new Factura();
        factura.setPaciente(pacienteService.find(request.idPaciente()));
        factura.setNumeroFactura(generarNumeroFactura(hoy));
        factura.setFechaFactura(hoy);
        factura.setFechaVencimiento(hoy.plusDays(request.diasCredito() == null ? 30 : request.diasCredito()));
        factura.setTipoFactura(request.tipoFactura() == null ? factura.getTipoFactura() : request.tipoFactura());

        BigDecimal base = BigDecimal.ZERO;
        BigDecimal iva = BigDecimal.ZERO;
        for (LineaFacturaRequest lineRequest : request.lineas()) {
            BigDecimal importeLinea = money(lineRequest.cantidad().multiply(lineRequest.precioUnitario()));
            BigDecimal ivaLinea = money(importeLinea.multiply(lineRequest.ivaPercent()).divide(ONE_HUNDRED, 4, RoundingMode.HALF_UP));
            LineaFactura linea = new LineaFactura();
            linea.setFactura(factura);
            linea.setConcepto(lineRequest.concepto());
            linea.setCantidad(lineRequest.cantidad());
            linea.setPrecioUnitario(lineRequest.precioUnitario());
            linea.setIvaPercent(lineRequest.ivaPercent());
            linea.setImporteLinea(importeLinea);
            factura.getLineas().add(linea);
            base = base.add(importeLinea);
            iva = iva.add(ivaLinea);
        }
        factura.setBaseImponible(money(base));
        factura.setIva(money(iva));
        factura.setImporteTotal(money(base.add(iva)));
        return toResponse(facturaRepository.save(factura));
    }

    @Transactional(readOnly = true)
    public FacturaResponse obtener(UUID id) {
        return toResponse(find(id));
    }

    @Transactional(readOnly = true)
    public List<FacturaResponse> listarPendientes() {
        return facturaRepository.findByEstado(EstadoFactura.EMITIDA).stream().map(this::toResponse).toList();
    }

    @Transactional
    public PagoResponse registrarPago(UUID idFactura, PagoRequest request) {
        Factura factura = find(idFactura);
        if (factura.getEstado() == EstadoFactura.PAGADA) {
            throw new BusinessRuleException("La factura ya esta pagada");
        }
        Pago pago = new Pago();
        pago.setFactura(factura);
        pago.setNumeroRecibo(generarNumeroRecibo(idFactura));
        pago.setFormaPago(request.formaPago());
        pago.setImportePagado(money(request.importePago()));
        pago.setNumeroReferenciaTransaccion(request.numeroTransaccion());
        pago.setFechaPago(request.fecha() == null ? LocalDate.now() : request.fecha());
        pago = pagoRepository.save(pago);

        BigDecimal totalPagado = pagoRepository.findByFacturaIdFactura(idFactura).stream()
                .map(Pago::getImportePagado)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        factura.setEstado(totalPagado.compareTo(factura.getImporteTotal()) >= 0 ? EstadoFactura.PAGADA : EstadoFactura.PARCIAL);
        facturaRepository.save(factura);
        return new PagoResponse(pago.getIdPago(), pago.getNumeroRecibo(), pago.getImportePagado(), factura.getEstado());
    }

    private Factura find(UUID id) {
        return facturaRepository.findById(id).orElseThrow(() -> new NotFoundException("Factura no encontrada"));
    }

    private String generarNumeroFactura(LocalDate fecha) {
        long secuencia = facturaRepository.countByFechaFactura(fecha) + 1;
        return "FAC-" + fecha.getYear() + String.format("%02d", fecha.getMonthValue()) + "-" + String.format("%04d", secuencia);
    }

    private String generarNumeroRecibo(UUID idFactura) {
        long secuencia = pagoRepository.countByFacturaIdFactura(idFactura) + 1;
        return "REC-" + LocalDate.now().getYear() + "-" + String.format("%06d", secuencia);
    }

    private BigDecimal money(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    private FacturaResponse toResponse(Factura factura) {
        List<LineaFacturaResponse> lineas = factura.getLineas().stream()
                .map(linea -> new LineaFacturaResponse(
                        linea.getIdLinea(),
                        linea.getConcepto(),
                        linea.getCantidad(),
                        linea.getPrecioUnitario(),
                        linea.getIvaPercent(),
                        linea.getImporteLinea()))
                .toList();
        return new FacturaResponse(
                factura.getIdFactura(),
                factura.getNumeroFactura(),
                factura.getPaciente().getIdPaciente(),
                factura.getFechaFactura(),
                factura.getFechaVencimiento(),
                factura.getTipoFactura(),
                factura.getEstado(),
                lineas,
                factura.getBaseImponible(),
                factura.getIva(),
                factura.getImporteTotal());
    }
}
