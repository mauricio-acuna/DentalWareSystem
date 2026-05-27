# Integraciones con APIs Externas y Servicios de IA
## OdontoSystems / DentalWareSystem

**Version**: 1.0  
**Fecha**: 2026-05-28

---

## 1. Principios

- Toda integracion externa debe pasar por revision de seguridad.
- No enviar datos clinicos o personales a terceros sin base legal y contrato.
- Usar colas y reintentos para integraciones no criticas.
- Registrar auditoria de solicitudes y respuestas relevantes.
- Versionar contratos de integracion.

---

## 2. Arquitectura Recomendada

Componentes:

- `integration-gateway`: capa interna para APIs externas.
- `event-bus`: eventos asincronos para notificaciones y procesos largos.
- `webhook-handler`: recepcion segura de eventos externos.
- `audit-log`: trazabilidad de datos enviados y recibidos.
- `consent-service`: verificacion de consentimiento antes de compartir datos.

Patron:

```text
Modulo interno -> Servicio de integracion -> Cliente externo -> Auditoria
```

---

## 3. Integraciones Prioritarias

### 3.1 Email

Casos:

- Confirmacion de cita.
- Recordatorio.
- Factura enviada.
- Recuperacion de acceso.

Proveedores posibles:

- SMTP corporativo.
- SendGrid.
- Amazon SES.
- Mailgun.

### 3.2 SMS y WhatsApp

Casos:

- Recordatorios de cita.
- Confirmaciones.
- Avisos de cambio.

Proveedores posibles:

- Twilio.
- Vonage.
- WhatsApp Business Platform.

Riesgo: evitar incluir diagnosticos o datos clinicos en mensajes.

### 3.3 Pasarela de Pago

Casos:

- Pago con tarjeta.
- Link de pago.
- Conciliacion.
- Devoluciones.

Proveedores posibles:

- Stripe.
- Redsys.
- Adyen.
- Banco local.

Requisito: no almacenar datos de tarjeta en el sistema.

### 3.4 Contabilidad

Casos:

- Export de facturas.
- Export de pagos.
- Conciliacion.

Sistemas posibles:

- Holded.
- Sage.
- A3.
- Contasol.
- ERP personalizado.

### 3.5 Radiologia e Imagen

Casos:

- Gestion DICOM.
- Visualizacion de radiografias.
- Archivo de imagen clinica.

Estandares:

- DICOM.
- DICOMweb.
- FHIR ImagingStudy si se integra con sistemas sanitarios.

### 3.6 Interoperabilidad Sanitaria

Casos:

- Intercambio de pacientes.
- Informes clinicos.
- Prescripciones.

Estandares:

- HL7 FHIR.
- SNOMED CT.
- CIE-10.
- LOINC si aplica a resultados.

---

## 4. Servicios de IA

### 4.1 Casos de Uso Permitidos Iniciales

- Resumir notas administrativas no sensibles.
- Ayudar a redactar instrucciones postoperatorias revisadas por profesional.
- Clasificar tickets de soporte.
- Sugerir horarios optimos segun disponibilidad.
- Detectar patrones de no asistencia.
- Generar borradores de recordatorios.
- Ayudar a buscar informacion en documentacion interna.

### 4.2 Casos de Uso que Requieren Control Estricto

- Resumen de historial clinico.
- Analisis de radiografias.
- Sugerencias de diagnostico.
- Recomendaciones de tratamiento.
- Prediccion de riesgo clinico.

Estos casos requieren:

- Validacion por profesional sanitario.
- Consentimiento y base legal.
- Evaluacion DPIA.
- Registro de auditoria.
- Explicacion de limitaciones.
- Trazabilidad del modelo usado.

### 4.3 Casos No Permitidos sin Aprobacion

- Toma automatica de decisiones clinicas.
- Envio de datos identificables a modelos sin contrato de encargado de tratamiento.
- Sustituir diagnostico profesional.
- Entrenar modelos externos con datos de pacientes sin autorizacion formal.

---

## 5. Diseno de IA Seguro

Requisitos:

- Pseudonimizar datos antes de enviarlos.
- Minimizar campos enviados.
- Registrar finalidad de cada llamada.
- Aplicar filtros de datos sensibles.
- Mantener humano en el circuito.
- Permitir desactivar IA por clinica.
- Mostrar que la salida es asistida y requiere revision.

Campos de auditoria:

- usuario
- paciente o pseudonimo
- finalidad
- proveedor
- modelo
- version
- timestamp
- datos enviados resumidos
- resultado aceptado o rechazado

---

## 6. Arquitectura IA Recomendada

```text
Frontend
  -> Backend
    -> AI Orchestrator
      -> Policy Guard
      -> Redaction/Pseudonymization
      -> Provider Adapter
      -> Audit Log
```

El backend no debe llamar directamente a multiples proveedores desde controladores. Usar adaptadores por proveedor permite cambiar de modelo, aplicar politicas y auditar de forma uniforme.

---

## 7. Evaluacion de Proveedores de IA

Criterios:

- Residencia de datos.
- Contrato de tratamiento de datos.
- Retencion de datos configurable.
- No entrenamiento con datos enviados.
- Certificaciones de seguridad.
- Soporte de auditoria.
- Latencia.
- Coste por caso de uso.
- Capacidad multimodal si se analiza imagen en el futuro.

---

## 8. Backlog de Integraciones

| Prioridad | Integracion | Valor | Riesgo |
|-----------|-------------|-------|--------|
| P0 | Email transaccional | Alto | Bajo |
| P0 | SMS recordatorios | Alto | Medio |
| P1 | Pasarela de pago | Alto | Medio |
| P1 | Contabilidad | Medio | Medio |
| P1 | Almacenamiento documental cifrado | Alto | Alto |
| P2 | FHIR/HL7 | Alto | Alto |
| P2 | IA para resumen administrativo | Medio | Medio |
| P3 | IA clinica asistida | Alto | Critico |

---

## 9. Contrato de Integracion

Toda integracion debe documentar:

- Proveedor.
- Finalidad.
- Datos enviados.
- Datos recibidos.
- Base legal.
- Retencion.
- SLA.
- Politica de errores.
- Plan de contingencia.
- Responsable interno.

