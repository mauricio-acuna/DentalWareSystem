package com.clinica.odonto.service;

import com.clinica.odonto.dto.InsumoRequest;
import com.clinica.odonto.dto.InsumoResponse;
import com.clinica.odonto.exception.BusinessRuleException;
import com.clinica.odonto.exception.NotFoundException;
import com.clinica.odonto.model.Enums.CategoriaInsumo;
import com.clinica.odonto.model.Enums.EstadoInsumo;
import com.clinica.odonto.model.Insumo;
import com.clinica.odonto.repository.InsumoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class InsumoService {
    private final InsumoRepository repository;

    public InsumoService(InsumoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public InsumoResponse crear(InsumoRequest request) {
        if (repository.existsByCodigoInsumo(request.codigoInsumo())) {
            throw new BusinessRuleException("Ya existe un insumo con ese codigo");
        }
        Insumo insumo = new Insumo();
        apply(insumo, request);
        return toResponse(repository.save(insumo));
    }

    @Transactional(readOnly = true)
    public List<InsumoResponse> listar(CategoriaInsumo categoria, EstadoInsumo estado) {
        List<Insumo> insumos = categoria != null && estado != null
                ? repository.findByCategoriaAndEstado(categoria, estado)
                : repository.findAll();
        return insumos.stream().map(this::toResponse).toList();
    }

    @Transactional
    public InsumoResponse ajustarStock(UUID id, int cantidad) {
        Insumo insumo = repository.findById(id).orElseThrow(() -> new NotFoundException("Insumo no encontrado"));
        int nuevoStock = insumo.getStockActual() + cantidad;
        if (nuevoStock < 0) {
            throw new BusinessRuleException("El stock no puede quedar en negativo");
        }
        insumo.setStockActual(nuevoStock);
        return toResponse(repository.save(insumo));
    }

    private void apply(Insumo insumo, InsumoRequest request) {
        insumo.setCodigoInsumo(request.codigoInsumo());
        insumo.setNombreProducto(request.nombreProducto());
        insumo.setCategoria(request.categoria());
        insumo.setFabricante(request.fabricante());
        insumo.setCodigoLote(request.codigoLote());
        insumo.setFechaCaducidad(request.fechaCaducidad());
        insumo.setPrecioCompra(request.precioCompra());
        insumo.setPrecioVenta(request.precioVenta());
        insumo.setStockActual(request.stockActual());
        insumo.setStockMinimo(request.stockMinimo());
        insumo.setStockOptimo(request.stockOptimo());
        insumo.setUbicacionAlmacen(request.ubicacionAlmacen());
        insumo.setCertificacionCe(request.certificacionCE());
        insumo.setBiocompatibilidad(request.biocompatibilidad());
        insumo.setRequiereEsterilizacion(request.requiereEsterilizacion());
    }

    private InsumoResponse toResponse(Insumo insumo) {
        return new InsumoResponse(
                insumo.getIdInsumo(),
                insumo.getCodigoInsumo(),
                insumo.getNombreProducto(),
                insumo.getCategoria(),
                insumo.getPrecioCompra(),
                insumo.getPrecioVenta(),
                insumo.getStockActual(),
                insumo.getStockMinimo(),
                insumo.getUbicacionAlmacen(),
                insumo.getFechaCaducidad(),
                insumo.getEstado(),
                insumo.getStockActual() <= insumo.getStockMinimo());
    }
}
