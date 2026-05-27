# Procesos de Negocio del Sistema
## Clínica Odontológica

---

## 1. PROCESO: GESTIÓN DE PACIENTES

### 1.1 Alta de Paciente
```
Participantes: Recepcionista, Sistema, BD

Flujo:
1. Paciente llega a clínica
2. Recepcionista inicia sesión en sistema
3. Busca paciente existente por DNI/Email
   ├─ SI existe: Confirmar datos y continuar
   └─ NO existe: Iniciar nuevo registro
4. Sistema solicita datos obligatorios:
   ├─ Identificación personal
   ├─ Datos contacto
   ├─ Información médica (alergias, patologías)
   ├─ Consentimiento RGPD
   └─ Autorización tratamiento dental
5. Sistema valida:
   ├─ Formato de datos
   ├─ Obligatoriedad de campos
   ├─ Unicidad de email/DNI
   └─ Consentimiento RGPD = SI
6. Sistema guarda registro en BD
7. Genera ID único del paciente
8. Imprime/Envía ficha a paciente
9. Fin

Tiempo estimado: 15-20 minutos
Alternativas:
- Paciente rellena ficha digital antes
- Migracion desde sistema antiguo
- Importación masiva para multiclínica

Validaciones:
- Email válido y único
- Edad > 0 años
- Consentimiento RGPD obligatorio
```

### 1.2 Actualización Información Médica
```
Participantes: Dentista, Paciente, Sistema

Flujo:
1. Dentista consulta a paciente sobre cambios en salud
2. Sistema abre pantalla de actualización
3. Dentista registra:
   ├─ Nuevas alergias/intolerancias
   ├─ Cambios en medicamentos
   ├─ Nuevas patologías
   └─ Cambios en hábitos
4. Sistema marca cambios como "PENDIENTE_REVISIÓN"
5. Genera notificación para revisión médica
6. Dentista verifica y aprueba
7. Sistema actualiza historial clínico
8. Registra cambio en auditoría (quién, cuándo, qué)
9. Fin

Validaciones:
- Cambios solo por personal médico autorizado
- Auditoría de cambios activada
- Notificación a paciente si es necesario
```

---

## 2. PROCESO: GESTIÓN DE CITAS

### 2.1 Programar Nueva Cita
```
Participantes: Recepcionista, Paciente, Dentista, Sistema

Flujo:
1. Paciente solicita cita (teléfono/presencial/web)
2. Recepcionista accede a módulo de citas
3. Busca paciente en BD
4. Sistema muestra disponibilidad:
   ├─ Próximas 4 semanas
   ├─ Filtrada por tipo de cita
   └─ Y especialidad requerida
5. Paciente elige fecha/hora
6. Recepcionista confirma:
   ├─ Datos del paciente
   ├─ Motivo de la consulta
   ├─ Dentista asignado
   └─ Confirmación de contacto
7. Sistema genera cita con estado "PROGRAMADA"
8. Asigna ID único a cita
9. Envía confirmación a paciente (SMS/Email)
10. Genera recordatorio 24h antes
11. Fin

Duraciones por tipo:
- Consulta: 30 minutos
- Tratamiento: 60 minutos
- Urgencia: 15-30 minutos
- Seguimiento: 20 minutos

Reglas de negocio:
- No permitir citas pasadas
- Validar disponibilidad dentista
- Mantener buffer de 10 min entre citas
- Máximo 8 citas por dentista/día
- Urgencias: Máximo 24h de espera
```

### 2.2 Confirmación de Cita
```
Participantes: Sistema, Paciente, Recepcionista

Flujo automático 24h antes de cita:
1. Sistema identifica cita próxima (24h)
2. Envía SMS/Email a paciente:
   "Recordatorio: Cita mañana a las 10:00 con Dr. García"
3. Paciente confirma vía:
   └─ Clic en link
   └─ Respuesta SMS
   └─ Llamada
4. Sistema registra confirmación
5. Actualiza estado a "CONFIRMADA"
6. Si NO confirma en 2h:
   ├─ Sistema intenta contacto (llamada)
   └─ Si no contesta: Marcar como "DUDOSA"
7. Dentista ve lista de citas confirmadas
8. Fin

Tiempos de recordatorio:
- 24 horas antes
- 3 horas antes (opcional)
- 30 minutos antes
```

### 2.3 Cancelación/Reprogramación
```
Participantes: Paciente, Recepcionista, Sistema

Opciones de cancelación:
A) Paciente cancela:
   1. Sistema recibe cancelación
   2. Libera franja horaria
   3. Genera notificación a recepcionista
   4. Envía confirmación a paciente
   5. Recomendación: Reprogramar
   6. Penalización: Según política

B) Clínica cancela:
   1. Motivo: Disponibilidad dentista, emergencia médica
   2. Sistema busca alternativas:
      ├─ Mismo día, hora diferente
      ├─ Próximo día disponible
      ├─ Mismo dentista disponible
   3. Propone opciones a paciente
   4. Contacta telefónicamente
   5. Registra cancelación con motivo

Regla de tiempo:
- Cancelación ≥ 2h antes: Sin penalización
- Cancelación < 2h: Registro de no presentación
- 3 no presentaciones: Revisión de política

Estados de cita:
PROGRAMADA → CONFIRMADA → COMPLETADA/CANCELADA/NO_PRESENTADO
```

---

## 3. PROCESO: REALIZACIÓN DE TRATAMIENTO

### 3.1 Sesión de Tratamiento
```
Participantes: Dentista, Higienista, Paciente, Auxiliar, Sistema

Flujo:
1. Paciente ingresa a consultorio
2. Auxiliar verifica datos: Alergias, medicamentos
3. Dentista revisa historial clínico en sistema
4. Abre pantalla de tratamiento
5. Documenta sesión en tiempo real:
   ├─ Anestesia aplicada
   ├─ Procedimientos realizados
   ├─ Hallazgos clínicos
   ├─ Materiales utilizados
   ├─ Tiempo real de procedimiento
   └─ Complicaciones (si aplica)
6. Toma fotos clínicas (si corresponde)
7. Sistema descuenta materiales del inventario
8. Registra en historial como "EN_CURSO"
9. Al finalizar:
   ├─ Marca como "COMPLETADA_SESION"
   ├─ Propone próxima cita de seguimiento
   └─ Genera recomendaciones de cuidado
10. Paciente recibe instrucciones post-tratamiento
11. Auxiliar programa próxima sesión
12. Fin

Documentación obligatoria:
- ✓ Consentimiento informado firmado
- ✓ Fotos antes/después
- ✓ Radiografías realizadas
- ✓ Diagnóstico confirmado
- ✓ Procedimientos documentados
- ✓ Firma digital del dentista
```

### 3.2 Actualización de Diagnóstico
```
Participantes: Dentista, Sistema, BD

Flujo:
1. Dentista visualiza radiografías
2. Sistema prepara pantalla de diagnóstico
3. Dentista selecciona diente(s) afectado(s)
4. Introduce código CIE-10
5. Describe hallazgos:
   ├─ Tipo de patología
   ├─ Severidad
   ├─ Extensión
   └─ Hallazgos radiográficos
6. Sistema valida código CIE-10
7. Propone plan de tratamiento
8. Dentista revisa/modifica
9. Sistema calcula presupuesto
10. Genera documento de plan de tratamiento
11. Registra diagnóstico en BD
12. Genera orden de trabajo
13. Fin

Posibles diagnósticos:
- Caries
- Enfermedad periodontal
- Pulpitis
- Absceso
- Maloclusión
- Erosión
- Tinciones
- Otros
```

### 3.3 Generación de Presupuesto
```
Participantes: Dentista, Administrativo, Sistema, Paciente

Flujo:
1. Sistema genera presupuesto automático basado en:
   ├─ Tratamientos programados
   ├─ Materiales necesarios
   ├─ Tarifas clínica
   ├─ Cobertura seguro
   └─ Vigencia (30 días típicamente)
2. Presupuesto incluye:
   ├─ Línea por cada procedimiento
   ├─ Copago estimado
   ├─ Cobertura seguro
   ├─ Urgencia del tratamiento
   └─ Prioridad recomendada
3. Dentista revisa y aprueba
4. Auxiliar imprime y entrega a paciente
5. Paciente revisa y firma (consentimiento)
6. Sistema almacena con firma del paciente
7. Notificación a sistemas:
   ├─ Si aprobado: Activa orden de trabajo
   ├─ Si rechazado: Genera informe
   └─ Si pendiente: Recordatorio en 7 días
8. Fin

Situaciones:
- Presupuesto aceptado completo
- Presupuesto parcialmente aceptado
- Presupuesto rechazado (paciente elige otras opciones)
- Presupuesto pendiente (revisión de cobertura seguro)
```

---

## 4. PROCESO: FACTURACIÓN Y PAGOS

### 4.1 Crear Factura
```
Participantes: Sistema, Administrativo, BD, Contabilidad

Trigger: Tratamiento completado

Flujo:
1. Al completar sesión, sistema genera factura automática
2. Factura incluye:
   ├─ Tratamientos realizados
   ├─ Materiales usados
   ├─ Servicios prestados
   ├─ Consultas médicas
   └─ Otros conceptos
3. Sistema calcula totales:
   ├─ Base imponible
   ├─ IVA aplicable
   ├─ Retenciones (si aplica)
   └─ Importe total
4. Valida datos:
   ├─ Datos paciente completos
   ├─ Datos clínica correctos
   ├─ Numeración secuencial
   └─ Fecha válida
5. Sistema asigna:
   ├─ Número de factura único
   ├─ Código QR de validación
   ├─ Firma digital de emisor
   └─ Fecha de vencimiento
6. Registra en BD con estado "EMITIDA"
7. Genera PDF para impresión/envío
8. Envía por email a paciente (si email registrado)
9. Contabilidad recibe notificación
10. Fin

Reglas de facturación:
- Factura ordinaria para servicios > 100€
- Factura simplificada para servicios < 100€
- Rectificativas si hay errores
- Numeración obligatoria y secuencial
```

### 4.2 Registro de Pago
```
Participantes: Paciente, Recepcionista, Sistema, Banco

Flujo manual:
1. Paciente realiza pago:
   ├─ Efectivo
   ├─ Transferencia bancaria
   ├─ Tarjeta débito/crédito
   ├─ Domiciliación
   └─ Cheque
2. Recepcionista registra pago en sistema
3. Sistema solicita:
   ├─ Método de pago
   ├─ Importe
   ├─ Referencia de transacción
   └─ Fecha
4. Paciente recibe comprobante
5. Sistema:
   ├─ Actualiza estado factura a "PAGADA"
   ├─ Genera recibo numerado
   ├─ Registra en auditoría
   ├─ Actualiza flujo de caja
   └─ Notifica contabilidad
6. Contabilidad concilia diariamente
7. Fin

Flujo automático (Banco):
1. Banco envía notificación de pago
2. Sistema valida:
   ├─ IBAN correcto
   ├─ Importe correcto
   ├─ Referencia coincide
3. Si valida: Marca como "PAGADA"
4. Si no valida: Genera incidencia
5. Fin

Estados factura:
EMITIDA → PAGADA
EMITIDA → PARCIAL (si pago parcial)
EMITIDA → VENCIDA (si > fecha vencimiento sin pago)
```

---

## 5. PROCESO: GESTIÓN DE INVENTARIO

### 5.1 Control de Stock
```
Participantes: Administrativo, Dentista, Sistema, Almacén

Monitoreo automático:
1. Sistema ejecuta cada 2 horas:
   ├─ Revisa stock de cada insumo
   ├─ Compara con stock mínimo
   └─ Compara con stock óptimo
2. Si stock < mínimo:
   ├─ Genera alerta
   ├─ Notifica administrativo
   ├─ Propone cantidad a comprar
   └─ Sugiere proveedor habitual
3. Si stock < crítico (mínimo * 0.7):
   ├─ Alerta ROJA
   ├─ Restricción de uso (solo urgencias)
   └─ Notificación urgente
4. Fin

Movimientos de stock:
```

### 5.2 Recepción de Compra
```
Participantes: Administrativo, Proveedor, Almacén, Sistema

Flujo:
1. Proveedor entrega pedido
2. Almacén recibe notificación
3. Verifica:
   ├─ Número de pedido
   ├─ Cantidad de bultos
   ├─ Estado físico
   └─ Fechas de caducidad (muestras)
4. Sistema abre pantalla de recepción
5. Scannea cada código de barras
6. Verifica:
   ├─ Cantidad recibida vs solicitada
   ├─ Lote vs registrado
   ├─ Vencimiento
   └─ Precio vs factura
7. Si hay discrepancias:
   ├─ Genera incidencia
   ├─ Permite recibir parcialmente
   └─ Notifica proveedor
8. Si todo OK:
   ├─ Sistema actualiza inventario
   ├─ Almacena ubicación
   ├─ Registra movimiento
   └─ Cierra recepción
9. Sistema compara con factura
10. Si coincide: Apruba pago
11. Si no coincide: Genera alerta para revisar
12. Fin

Incidencias posibles:
- Cantidad incorrecta
- Producto dañado
- Fecha de caducidad próxima
- Producto erróneo
- Falta de documentación
```

---

## 6. PROCESO: AUDITORÍA Y CUMPLIMIENTO

### 6.1 Registro de Auditoría Automático
```
Participantes: Sistema, BD de Auditoría

Eventos auditados:
- Login/Logout de usuarios
- Acceso a datos sensibles (pacientes, diagnósticos)
- Modificaciones de datos
- Eliminación de registros
- Cambios en permisos/roles
- Exportación de datos
- Cambios de configuración
- Intentos fallidos de acceso

Datos registrados:
- ID de usuario
- Timestamp exacto
- Tipo de evento
- Entidad afectada
- Valor anterior/nuevo
- IP origen
- User-agent
- Resultado (éxito/error)
- Código de error (si aplica)

Retención: 10 años (como mínimo por normativa sanitaria)
```

### 6.2 Revisión de Cumplimiento RGPD
```
Participantes: DPO, Administrativo, Sistema

Frecuencia: Trimestral

Checklist:
- ✓ Consentimientos vigentes actualizados
- ✓ Solicitudes de derecho al olvido procesadas
- ✓ Auditorías de acceso revisadas
- ✓ Datos de menores protegidos
- ✓ Brechas de seguridad documentadas
- ✓ Evaluaciones de impacto actualizadas
- ✓ Cláusulas de privacidad vigentes
- ✓ Registro de actividades de tratamiento

Reportes:
- Incidentes de seguridad
- Solicitudes RGPD procesadas
- Cambios en política de privacidad
- Hallazgos de auditoría
- Acciones correctivas requeridas
```

---

**Versión**: 1.0  
**Fecha**: 2024-05-27  
**Próxima revisión**: 2024-11-27
