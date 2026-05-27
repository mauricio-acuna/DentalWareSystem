# Estado de Implementacion Funcional
## OdontoSystems / DentalWareSystem

**Fecha de revision**: 2026-05-28  
**Revision realizada contra**:

- `04_API/ESPECIFICACION_REST_API.md`
- `06_Documentacion/PROCESOS_NEGOCIO.md`
- `03_Modelos/MODELOS_DATOS.json`
- Codigo actual en `codigo/backend`

---

## 1. Conclusion Ejecutiva

El codigo actual **no implementa todo el sistema descrito en la documentacion funcional**.

Lo existente es un **MVP backend funcional** con una primera vertical de:

- Pacientes.
- Citas.
- Inventario basico.
- Facturas y pagos basicos.

La documentacion describe un sistema completo con 25+ entidades, 40+ endpoints, seguridad avanzada, auditoria, tratamientos clinicos, documentos, radiografias, compras, reportes, usuarios, roles, integraciones y cumplimiento operativo. Gran parte de ese alcance sigue pendiente.

---

## 2. Verificacion Tecnica Ejecutada

Comando ejecutado en `codigo/backend`:

```powershell
$env:JAVA_HOME="C:\Program Files\Java\jdk-17"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
mvn.cmd verify
```

Resultado:

- Build: correcto.
- Pruebas: 6 ejecutadas, 0 fallos.
- JaCoCo: puerta de cobertura configurada superada.
- Artefacto: `target/odonto-backend-1.0.0.jar` generado.

Esto confirma que el MVP backend compila y que los flujos cubiertos por pruebas funcionan. No confirma que el sistema completo este implementado.

---

## 3. Endpoints Implementados

Controladores actuales:

- `PacienteController`
- `CitaController`
- `InsumoController`
- `FacturaController`

Rutas implementadas:

| Modulo | Metodo | Ruta | Estado |
|--------|--------|------|--------|
| Pacientes | POST | `/api/v1/pacientes` | Implementado |
| Pacientes | GET | `/api/v1/pacientes/{id}` | Implementado |
| Pacientes | GET | `/api/v1/pacientes` | Implementado basico |
| Pacientes | PUT | `/api/v1/pacientes/{id}` | Implementado, no PATCH |
| Pacientes | DELETE | `/api/v1/pacientes/{id}` | Parcial: marca pendiente de eliminacion |
| Citas | POST | `/api/v1/citas` | Implementado |
| Citas | GET | `/api/v1/citas/{id}` | Implementado |
| Citas | GET | `/api/v1/pacientes/{idPaciente}/citas` | Implementado |
| Citas | POST | `/api/v1/citas/{id}/confirmar` | Implementado |
| Citas | PUT | `/api/v1/citas/{id}` | Implementado |
| Inventario | POST | `/api/v1/inventario` | Implementado, no estaba en especificacion inicial |
| Inventario | GET | `/api/v1/inventario` | Implementado basico |
| Inventario | PATCH | `/api/v1/inventario/{id}/stock` | Implementado, no estaba en especificacion inicial |
| Facturas | POST | `/api/v1/facturas` | Implementado basico |
| Facturas | GET | `/api/v1/facturas/{id}` | Implementado |
| Facturas | GET | `/api/v1/facturas/pendientes` | Implementado parcial |
| Facturas | POST | `/api/v1/facturas/{id}/pagos` | Implementado basico |

---

## 4. Comparacion Contra Especificacion REST

| Area documentada | Estado real | Observaciones |
|------------------|-------------|---------------|
| Crear paciente | Implementado | Requiere consentimiento RGPD y valida duplicados. |
| Obtener paciente | Implementado | Respuesta simplificada. No incluye deuda real ni contadores clinicos completos. |
| Actualizar paciente | Parcial | Documentado como `PATCH`; codigo usa `PUT` y reemplazo completo. |
| Listar pacientes | Parcial | Filtro por estado. Faltan ciudad, paginacion y enlaces. |
| Eliminar paciente RGPD | Parcial | Marca `PENDIENTE_ELIMINACION`; falta proceso de anonimizacion/borrado auditado. |
| Crear cita | Implementado parcial | Valida fecha/hora/duracion. Falta recordatorio y disponibilidad real por agenda completa. |
| Obtener disponibilidad | No implementado | Endpoint ausente. |
| Confirmar cita | Implementado | Basico. |
| Cambiar cita | Implementado parcial | No registra razon de cambio. |
| Crear tratamiento | No implementado | Sin entidad, repositorio, servicio ni controlador. |
| Registrar sesion tratamiento | No implementado | Pendiente. |
| Completar tratamiento | No implementado | Pendiente. |
| Listar insumos | Implementado parcial | No coincide al 100% con respuesta especificada. |
| Crear pedido | No implementado | Pendiente compras/proveedores. |
| Registrar recepcion | No implementado | Pendiente. |
| Crear factura | Implementado parcial | Calcula base, IVA y total. Falta numeracion fiscal configurable, PDF, referencias a tratamientos. |
| Registrar pago | Implementado parcial | Marca pagada/parcial. Falta recibo fiscal completo, conciliacion y devoluciones. |
| Reporte ingresos | No implementado | Pendiente. |
| Reporte cobranza | Parcial | Existe `/facturas/pendientes`, no reporte documentado. |
| Reporte actividad clinica | No implementado | Pendiente. |
| Rate limiting | No implementado | Solo documentado. |
| OAuth2/OIDC/JWT/2FA | No implementado | Seguridad actual permite endpoints para desarrollo. |
| Headers requeridos | No implementado | No se exige `X-Clinic-ID`, `X-Request-ID`, `X-Timestamp`. |

---

## 5. Comparacion Contra Procesos de Negocio

| Proceso | Estado real | Brecha principal |
|---------|-------------|------------------|
| Alta de paciente | Parcial | Falta verificacion documental, consentimientos versionados, historia clinica inicial. |
| Actualizacion informacion medica | Parcial | Campos simples; falta historial clinico estructurado y auditoria. |
| Programar cita | Parcial | Falta disponibilidad real, salas, recordatorios, reglas avanzadas. |
| Confirmacion de cita | Parcial | Falta notificacion y trazabilidad completa. |
| Cancelacion/Reprogramacion | Parcial | Falta cancelacion explicita y motivo. |
| Sesion de tratamiento | No implementado | Modulo clinico pendiente. |
| Actualizacion diagnostico | No implementado | Diagnosticos pendientes. |
| Generacion presupuesto | No implementado | Presupuestos pendientes. |
| Crear factura | Parcial | Sin integracion con tratamientos ni reglas fiscales completas. |
| Registro de pago | Parcial | Sin conciliacion, recibos completos ni auditoria. |
| Control de stock | Parcial | Ajuste manual; faltan movimientos, caducidad y alertas. |
| Recepcion de compra | No implementado | Compras/proveedores pendientes. |
| Registro de auditoria automatico | No implementado | Pendiente critico. |
| Revision cumplimiento RGPD | No implementado | Pendiente critico. |

---

## 6. Entidades del Modelo

Entidades implementadas en codigo:

- `Paciente`
- `Cita`
- `Insumo`
- `Factura`
- `LineaFactura`
- `Pago`

Entidades documentadas pero no implementadas, entre otras:

- Empleado.
- Tratamiento.
- Diagnostico.
- Proveedor.
- MovimientoInventario.
- Compra.
- LineaPedido.
- Presupuesto.
- LineaPresupuesto.
- HistorialClinico.
- Radiografia.
- Prescripcion.
- DocumentoClinico.
- EventoAuditoria.
- UsuarioSistema.
- Rol.
- Permiso.
- EquipoClinico.
- MantenimientoEquipo.
- CicloEsterilizacion.
- InstrumentoEsterilizado.

---

## 7. Riesgos Actuales

### Criticos antes de produccion

- No hay autenticacion real.
- No hay autorizacion por roles.
- No hay auditoria persistente.
- No hay migraciones de base de datos.
- No hay implementacion completa de RGPD.
- No hay cifrado de campos sensibles.
- No hay frontend.
- No hay modulo clinico completo.

### Importantes

- Las respuestas API no coinciden siempre con la especificacion documentada.
- Falta paginacion y filtros avanzados.
- Falta versionado formal de contratos OpenAPI.
- Falta integracion con PostgreSQL real en pruebas automatizadas.
- Falta observabilidad productiva.

---

## 8. Alcance que Si Puede Considerarse Funcionando

Con las pruebas actuales, se puede considerar funcionando:

- Alta de paciente con consentimiento RGPD.
- Rechazo de paciente sin consentimiento.
- Rechazo de paciente duplicado.
- Creacion de cita valida.
- Rechazo de cita con horario invalido.
- Confirmacion de cita.
- Creacion de factura simple con calculo de IVA.
- Registro de pago que deja factura pagada.

---

## 9. Recomendacion de Siguiente Paso

Para avanzar de MVP a sistema real, se recomienda implementar por incrementos:

1. Seguridad base productiva: usuarios, roles, JWT/OIDC, auditoria.
2. Migraciones Flyway y pruebas Testcontainers con PostgreSQL.
3. Modulo clinico: tratamientos, sesiones, diagnosticos e historial.
4. RGPD completo: consentimientos, exportacion, anonimizacion y auditoria.
5. Inventario completo: movimientos, proveedores, compras y recepcion.
6. Reportes y dashboard.
7. Frontend web.

Este documento debe actualizarse tras cada incremento para evitar que la documentacion funcional y el codigo vuelvan a separarse.

