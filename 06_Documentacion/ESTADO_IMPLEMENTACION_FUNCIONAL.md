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

El codigo actual **todavia no implementa todo el sistema descrito en la documentacion funcional**, pero ya avanzo mas alla del MVP inicial.

Lo existente es un **backend funcional en desarrollo** con verticales de:

- Pacientes.
- Citas.
- Disponibilidad basica.
- Tratamientos y sesiones clinicas basicas.
- Diagnosticos clinicos basicos.
- Inventario basico.
- Facturas y pagos basicos.
- Auditoria persistente basica.
- Usuarios, roles y login JWT basico.
- Reportes basicos de ingresos y actividad.

La documentacion describe un sistema completo con 25+ entidades, 40+ endpoints, seguridad avanzada, documentos, radiografias, compras, integraciones y cumplimiento operativo. Una parte importante del alcance sigue pendiente, especialmente seguridad productiva completa, compras/proveedores, historia clinica avanzada, documentos clinicos, frontend y RGPD completo.

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
- Pruebas: 11 ejecutadas, 0 fallos.
- JaCoCo: puerta de cobertura configurada superada.
- Artefacto: `target/odonto-backend-1.0.0.jar` generado.

Esto confirma que el backend compila y que los flujos cubiertos por pruebas funcionan. No confirma que el sistema completo este implementado.

---

## 3. Endpoints Implementados

Controladores actuales:

- `PacienteController`
- `CitaController`
- `InsumoController`
- `FacturaController`
- `TratamientoController`
- `DiagnosticoController`
- `ReporteController`
- `AuditoriaController`
- `AuthController`

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
| Citas | GET | `/api/v1/disponibilidad` | Implementado basico |
| Citas | POST | `/api/v1/citas/{id}/confirmar` | Implementado |
| Citas | PUT | `/api/v1/citas/{id}` | Implementado |
| Tratamientos | POST | `/api/v1/pacientes/{idPaciente}/tratamientos` | Implementado basico |
| Tratamientos | GET | `/api/v1/pacientes/{idPaciente}/tratamientos` | Implementado basico |
| Tratamientos | GET | `/api/v1/tratamientos/{idTratamiento}` | Implementado basico |
| Tratamientos | POST | `/api/v1/tratamientos/{idTratamiento}/sesiones` | Implementado basico |
| Tratamientos | PUT | `/api/v1/tratamientos/{idTratamiento}/completar` | Implementado basico |
| Diagnosticos | POST | `/api/v1/pacientes/{idPaciente}/diagnosticos` | Implementado basico |
| Diagnosticos | GET | `/api/v1/pacientes/{idPaciente}/diagnosticos` | Implementado basico |
| Inventario | POST | `/api/v1/inventario` | Implementado, no estaba en especificacion inicial |
| Inventario | GET | `/api/v1/inventario` | Implementado basico |
| Inventario | PATCH | `/api/v1/inventario/{id}/stock` | Implementado, no estaba en especificacion inicial |
| Facturas | POST | `/api/v1/facturas` | Implementado basico |
| Facturas | GET | `/api/v1/facturas/{id}` | Implementado |
| Facturas | GET | `/api/v1/facturas/pendientes` | Implementado parcial |
| Facturas | POST | `/api/v1/facturas/{id}/pagos` | Implementado basico |
| Reportes | GET | `/api/v1/reportes/ingresos` | Implementado basico |
| Reportes | GET | `/api/v1/reportes/actividad` | Implementado basico |
| Auditoria | GET | `/api/v1/auditoria/eventos` | Implementado basico |
| Seguridad | POST | `/api/v1/usuarios` | Implementado basico |
| Seguridad | POST | `/api/v1/auth/login` | Implementado basico con JWT |

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
| Obtener disponibilidad | Implementado basico | Calcula huecos de 30 minutos por rango y dentista. Faltan agenda laboral configurable, salas y excepciones. |
| Confirmar cita | Implementado | Basico. |
| Cambiar cita | Implementado parcial | No registra razon de cambio. |
| Crear tratamiento | Implementado basico | Incluye entidad, repositorio, servicio, controlador y auditoria. Falta odontograma, presupuesto y consentimiento asociado. |
| Registrar sesion tratamiento | Implementado basico | Registra fecha, notas, procedimiento y coste. Falta firma, adjuntos y materiales consumidos. |
| Completar tratamiento | Implementado basico | Permite cierre clinico. Falta flujo de revision/cierre documental. |
| Listar insumos | Implementado parcial | No coincide al 100% con respuesta especificada. |
| Crear pedido | No implementado | Pendiente compras/proveedores. |
| Registrar recepcion | No implementado | Pendiente. |
| Crear factura | Implementado parcial | Calcula base, IVA y total. Falta numeracion fiscal configurable, PDF, referencias a tratamientos. |
| Registrar pago | Implementado parcial | Marca pagada/parcial. Falta recibo fiscal completo, conciliacion y devoluciones. |
| Reporte ingresos | Implementado basico | Calcula facturacion, cobros, pendiente y facturas emitidas por periodo. Falta segmentacion avanzada y exportacion. |
| Reporte cobranza | Parcial | Existe `/facturas/pendientes`, no reporte documentado. |
| Reporte actividad clinica | Implementado basico | Cuenta citas, tratamientos y diagnosticos por periodo. Faltan KPIs clinicos avanzados. |
| Rate limiting | No implementado | Solo documentado. |
| OAuth2/OIDC/JWT/2FA | Parcial | Existe login JWT propio y roles basicos. Falta OIDC externo, MFA, politicas de sesion, rotacion, refresh tokens y enforcement productivo completo. |
| Headers requeridos | No implementado | No se exige `X-Clinic-ID`, `X-Request-ID`, `X-Timestamp`. |

---

## 5. Comparacion Contra Procesos de Negocio

| Proceso | Estado real | Brecha principal |
|---------|-------------|------------------|
| Alta de paciente | Parcial | Falta verificacion documental, consentimientos versionados, historia clinica inicial. |
| Actualizacion informacion medica | Parcial | Campos simples, diagnosticos basicos y auditoria basica; falta historial clinico avanzado. |
| Programar cita | Parcial | Incluye disponibilidad basica; faltan salas, recordatorios, reglas avanzadas y calendario laboral configurable. |
| Confirmacion de cita | Parcial | Falta notificacion y trazabilidad completa. |
| Cancelacion/Reprogramacion | Parcial | Falta cancelacion explicita y motivo. |
| Sesion de tratamiento | Parcial | Modulo clinico basico implementado; faltan firmas, adjuntos, odontograma y materiales. |
| Actualizacion diagnostico | Parcial | Diagnosticos basicos implementados; falta actualizacion versionada y relacion completa con historial. |
| Generacion presupuesto | No implementado | Presupuestos pendientes. |
| Crear factura | Parcial | Sin integracion con tratamientos ni reglas fiscales completas. |
| Registro de pago | Parcial | Sin conciliacion, recibos completos ni auditoria. |
| Control de stock | Parcial | Ajuste manual; faltan movimientos, caducidad y alertas. |
| Recepcion de compra | No implementado | Compras/proveedores pendientes. |
| Registro de auditoria automatico | Parcial | Eventos basicos en pacientes, citas, tratamientos, diagnosticos, inventario, facturacion y pagos. Falta usuario real y trazabilidad de peticion. |
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
- `Tratamiento`
- `SesionTratamiento`
- `Diagnostico`
- `EventoAuditoria`
- `UsuarioSistema`

Entidades documentadas pero no implementadas, entre otras:

- Empleado.
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
- Rol.
- Permiso.
- EquipoClinico.
- MantenimientoEquipo.
- CicloEsterilizacion.
- InstrumentoEsterilizado.

---

## 7. Riesgos Actuales

### Criticos antes de produccion

- La autenticacion JWT existe en version inicial y puede activarse con `APP_SECURITY_ENABLED=true`, pero no reemplaza todavia un esquema productivo OIDC/MFA.
- La autorizacion por roles existe como base de modelo, pero faltan reglas finas por endpoint y matriz RBAC completa.
- La auditoria persistente es basica y todavia debe enlazarse a usuario autenticado, IP, request id y tenant/clinica.
- No hay migraciones de base de datos.
- No hay implementacion completa de RGPD.
- No hay cifrado de campos sensibles.
- No hay frontend.
- El modulo clinico existe en version basica, pero todavia no cubre odontograma, radiografias, documentos ni presupuestos.

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
- Consulta de disponibilidad basica por dentista y rango.
- Creacion, consulta, sesion y cierre de tratamientos basicos.
- Creacion y consulta de diagnosticos basicos.
- Creacion de factura simple con calculo de IVA.
- Registro de pago que deja factura pagada.
- Reporte basico de ingresos.
- Reporte basico de actividad clinica.
- Consulta basica de eventos de auditoria.

---

## 9. Recomendacion de Siguiente Paso

Para avanzar de MVP a sistema real, se recomienda implementar por incrementos:

1. Seguridad base productiva: usuarios, roles, JWT/OIDC y auditoria asociada a usuario/request.
2. Migraciones Flyway y pruebas Testcontainers con PostgreSQL.
3. Modulo clinico avanzado: historial, odontograma, documentos, radiografias y presupuestos.
4. RGPD completo: consentimientos, exportacion, anonimizacion y auditoria.
5. Inventario completo: movimientos, proveedores, compras y recepcion.
6. Reportes y dashboard.
7. Frontend web.

Este documento debe actualizarse tras cada incremento para evitar que la documentacion funcional y el codigo vuelvan a separarse.
