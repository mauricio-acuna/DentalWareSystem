package com.clinica.odonto.model;

public final class Enums {
    private Enums() {
    }

    public enum Genero {M, F, O}
    public enum EstadoPaciente {ACTIVO, INACTIVO, SUSPENDIDO, PENDIENTE_ELIMINACION}
    public enum TipoCita {CONSULTA, TRATAMIENTO, URGENCIA, SEGUIMIENTO}
    public enum EstadoCita {PROGRAMADA, CONFIRMADA, COMPLETADA, CANCELADA, NO_PRESENTADO}
    public enum Prioridad {BAJA, MEDIA, ALTA, URGENCIA}
    public enum CategoriaInsumo {INSTRUMENTOS, MATERIALES_OBTURACION, MEDICAMENTOS, EQUIPOS_CONSUMIBLES, OTROS}
    public enum EstadoInsumo {DISPONIBLE, DESCONTINUADO, RETIRADO}
    public enum TipoFactura {ORDINARIA, SIMPLIFICADA, RECTIFICATIVA, COMPLEMENTARIA}
    public enum EstadoFactura {EMITIDA, PAGADA, PARCIAL, VENCIDA, CANCELADA, ANULADA}
    public enum FormaPago {EFECTIVO, TRANSFERENCIA, TARJETA, CHEQUE, DOMICILIACION}
    public enum ClasificacionTratamiento {PREVENTIVA, RESTAURADORA, ENDODONCIA, PERIODONTAL, QUIRURGICA, ORTODONCIA, ESTETICA}
    public enum EstadoTratamiento {PENDIENTE, EN_CURSO, COMPLETADO, SUSPENDIDO, CANCELADO}
    public enum SeveridadDiagnostico {LEVE, MODERADA, SEVERA}
    public enum TipoEventoAuditoria {ACCESO, LECTURA, MODIFICACION, ELIMINACION, CREACION, EXPORTACION, ACCESO_DENEGADO}
    public enum ResultadoEvento {EXITO, ERROR}
    public enum NivelSeveridadEvento {INFO, ADVERTENCIA, CRITICO}
    public enum RolUsuario {ADMIN, ODONTOLOGO, HIGIENISTA, RECEPCION, FINANZAS}
    public enum EstadoUsuario {ACTIVO, BLOQUEADO, INACTIVO}
}
