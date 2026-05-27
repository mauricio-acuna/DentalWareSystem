# Especificación de APIs REST
## Sistema de Clínica Odontológica v1.0

### Base URL
```
https://api.clinica-odonto.eu/v1
```

### Autenticación
- **OAuth 2.0** + **OpenID Connect**
- **JWT Bearer Token** con firma RSA-256
- **2FA obligatorio** para personal clínico
- **Expiración de token**: 1 hora
- **Refresh token**: 30 días

### Headers Requeridos
```
Authorization: Bearer {token}
Content-Type: application/json
X-Clinic-ID: {clinic-uuid}
X-Request-ID: {uuid}
X-Timestamp: {ISO-8601}
```

---

## ENDPOINTS - GESTIÓN DE PACIENTES

### 1. Crear Paciente
```
POST /pacientes
Content-Type: application/json

{
  "numeroIdentificacion": "12345678X",
  "nombreCompleto": "Juan",
  "apellidos": "García López",
  "fechaNacimiento": "1990-05-15",
  "genero": "M",
  "email": "juan.garcia@email.com",
  "telefonoMovil": "+34612345678",
  "direccion": {
    "calle": "Calle Principal 123",
    "codigoPostal": "28001",
    "ciudad": "Madrid",
    "pais": "España"
  },
  "consentimientoRGPD": true,
  "historialAlergias": [
    {
      "alergia": "Penicilina",
      "severidad": "SEVERA",
      "reaccion": "Anafilaxia"
    }
  ],
  "patologiasBase": ["Diabetes", "Hipertensión"],
  "medicamentosActuales": [
    {
      "farmaco": "Metformina",
      "dosis": "500mg",
      "frecuencia": "2 veces/día"
    }
  ]
}

Response: 201 Created
{
  "idPaciente": "550e8400-e29b-41d4-a716-446655440000",
  "numeroIdentificacion": "12345678X",
  "estado": "ACTIVO",
  "consentimientoRGPD": true,
  "fechaAlta": "2024-05-27T10:30:00Z",
  "links": {
    "self": "/pacientes/550e8400-e29b-41d4-a716-446655440000",
    "historico": "/pacientes/550e8400-e29b-41d4-a716-446655440000/historico",
    "citas": "/pacientes/550e8400-e29b-41d4-a716-446655440000/citas"
  }
}
```

### 2. Obtener Paciente
```
GET /pacientes/{idPaciente}

Response: 200 OK
{
  "idPaciente": "550e8400-e29b-41d4-a716-446655440000",
  "numeroIdentificacion": "12345678X",
  "nombreCompleto": "Juan",
  "apellidos": "García López",
  "fechaNacimiento": "1990-05-15",
  "genero": "M",
  "email": "juan.garcia@email.com",
  "edad": 34,
  "direccion": {...},
  "historialAlergias": [...],
  "citas_proximas": 2,
  "tratamientos_activos": 1,
  "deuda": 150.00
}
```

### 3. Actualizar Paciente
```
PATCH /pacientes/{idPaciente}
Content-Type: application/json

{
  "telefono": "+34612999999",
  "medicamentosActuales": [...]
}

Response: 200 OK
```

### 4. Listar Pacientes
```
GET /pacientes?estado=ACTIVO&ciudad=Madrid&pagina=1&tamaño=20

Response: 200 OK
{
  "total": 345,
  "pagina": 1,
  "tamaño": 20,
  "datos": [
    {...},
    {...}
  ],
  "links": {
    "primera": "/pacientes?pagina=1",
    "proxima": "/pacientes?pagina=2",
    "ultima": "/pacientes?pagina=18"
  }
}
```

### 5. Eliminar Paciente (Derecho al Olvido RGPD)
```
DELETE /pacientes/{idPaciente}
X-Razon-Eliminacion: Solicitud RGPD

Response: 202 Accepted
{
  "status": "PENDIENTE_ELIMINACION",
  "idProceso": "uuid",
  "estimacion": "30 días",
  "descripcion": "Eliminación de datos personales en proceso"
}
```

---

## ENDPOINTS - GESTIÓN DE CITAS

### 1. Crear Cita
```
POST /citas
Content-Type: application/json

{
  "idPaciente": "550e8400-e29b-41d4-a716-446655440000",
  "idDentista": "660e8400-e29b-41d4-a716-446655440001",
  "fechaCita": "2024-06-15",
  "horaInicio": "10:00",
  "horaFin": "10:30",
  "tipoCita": "CONSULTA",
  "motivoConsulta": "Revisión general y limpieza",
  "prioridad": "MEDIA",
  "metodoRecordatorio": "SMS"
}

Response: 201 Created
{
  "idCita": "770e8400-e29b-41d4-a716-446655440002",
  "estado": "PROGRAMADA",
  "fechaConfirmacion": null,
  "links": {
    "confirmacion": "/citas/770e8400-e29b-41d4-a716-446655440002/confirmar"
  }
}
```

### 2. Obtener Disponibilidad
```
GET /disponibilidad?fechaInicio=2024-06-15&fechaFin=2024-06-20&idDentista={uuid}

Response: 200 OK
{
  "disponibilidad": [
    {
      "fecha": "2024-06-15",
      "horas": [
        {"hora": "09:00", "disponible": true},
        {"hora": "09:30", "disponible": true},
        {"hora": "10:00", "disponible": false}
      ]
    }
  ]
}
```

### 3. Confirmar Cita
```
POST /citas/{idCita}/confirmar
Content-Type: application/json

{
  "confirmacion": true,
  "notas": "Tomo medicamentos para la ansiedad"
}

Response: 200 OK
```

### 4. Cambiar Cita
```
PUT /citas/{idCita}
Content-Type: application/json

{
  "fechaCita": "2024-06-16",
  "horaInicio": "14:00",
  "razonCambio": "Incompatibilidad con horario laboral"
}

Response: 200 OK
```

---

## ENDPOINTS - TRATAMIENTOS

### 1. Crear Tratamiento
```
POST /pacientes/{idPaciente}/tratamientos
Content-Type: application/json

{
  "idCita": "770e8400-e29b-41d4-a716-446655440002",
  "nombreTratamiento": "Obturación amalgama",
  "clasificacion": "RESTAURADORA",
  "dienteAfectado": "14",
  "superficieDiente": "O",
  "presupuesto": 85.50,
  "materialesUtilizados": [
    {
      "idInsumo": "880e8400-e29b-41d4-a716-446655440003",
      "cantidad": 2,
      "precio": 15.00
    }
  ],
  "planTratamiento": "Primera sesión: preparación cavitaria"
}

Response: 201 Created
```

### 2. Registrar Sesión de Tratamiento
```
POST /tratamientos/{idTratamiento}/sesiones
Content-Type: application/json

{
  "numeroSesion": 1,
  "fechaRealizacion": "2024-05-28",
  "tiempoReal": 30,
  "procedimientos": ["Anestesia local", "Preparación cavitaria"],
  "complicaciones": "",
  "proximaSesion": "2024-06-04",
  "empleadoResponsable": "660e8400-e29b-41d4-a716-446655440001"
}

Response: 201 Created
```

### 3. Completar Tratamiento
```
PUT /tratamientos/{idTratamiento}/completar
Content-Type: application/json

{
  "resultadoClinico": "Excelente",
  "observaciones": "Paciente bien toleró tratamiento",
  "proximoControl": "2024-08-28",
  "fotografiasAntesDepues": ["url1", "url2"]
}

Response: 200 OK
{
  "estado": "COMPLETADO",
  "facturaGenerada": true,
  "numeroFactura": "FAC-202405-001"
}
```

---

## ENDPOINTS - INVENTARIO

### 1. Listar Insumos
```
GET /inventario?categoria=MATERIALES_OBTURACION&estado=DISPONIBLE

Response: 200 OK
{
  "insumos": [
    {
      "idInsumo": "880e8400-e29b-41d4-a716-446655440003",
      "codigoInsumo": "OBTU-AMAL-001",
      "nombreProducto": "Amalgama de plata",
      "stockActual": 45,
      "stockMinimo": 20,
      "precio": 15.00,
      "ubicacion": "A-3-15",
      "caducidad": "2025-12-31"
    }
  ]
}
```

### 2. Crear Pedido
```
POST /pedidos
Content-Type: application/json

{
  "idProveedor": "990e8400-e29b-41d4-a716-446655440004",
  "lineas": [
    {
      "idInsumo": "880e8400-e29b-41d4-a716-446655440003",
      "cantidad": 100,
      "precioUnitario": 14.50
    }
  ],
  "fechaEntregaPrevista": "2024-06-10"
}

Response: 201 Created
{
  "idPedido": "aa0e8400-e29b-41d4-a716-446655440005",
  "numeroPedido": "PED-202405-001",
  "estado": "CONFIRMADA",
  "importeTotal": 1450.00
}
```

### 3. Registrar Recepción
```
POST /pedidos/{idPedido}/recibir
Content-Type: application/json

{
  "cantidadRecibida": 100,
  "numeroFacturaProveedor": "FAC-PROV-2024-001",
  "controlCalidad": true,
  "incidencias": "Embalaje en perfecto estado"
}

Response: 200 OK
```

---

## ENDPOINTS - FACTURACIÓN

### 1. Crear Factura
```
POST /facturas
Content-Type: application/json

{
  "idPaciente": "550e8400-e29b-41d4-a716-446655440000",
  "tipoFactura": "ORDINARIA",
  "lineas": [
    {
      "concepto": "Consulta general",
      "cantidad": 1,
      "precioUnitario": 50.00,
      "ivaPercent": 21
    },
    {
      "idTratamiento": "bb0e8400-e29b-41d4-a716-446655440006",
      "cantidad": 1,
      "precioUnitario": 85.50,
      "ivaPercent": 21
    }
  ],
  "diasCredito": 30
}

Response: 201 Created
{
  "numeroFactura": "FAC-2024-05-001",
  "baseImponible": 135.50,
  "iva": 28.46,
  "importeTotal": 163.96,
  "links": {
    "pdf": "/facturas/cc0e8400-e29b-41d4-a716-446655440007/pdf"
  }
}
```

### 2. Registrar Pago
```
POST /facturas/{idFactura}/pagos
Content-Type: application/json

{
  "formaPago": "TRANSFERENCIA",
  "importePago": 163.96,
  "numeroTransaccion": "TR-2024-05-001",
  "fecha": "2024-05-28"
}

Response: 201 Created
{
  "numeroRecibo": "REC-2024-05-001",
  "estado": "PAGADA"
}
```

---

## ENDPOINTS - REPORTES

### 1. Ingresos por período
```
GET /reportes/ingresos?fechaInicio=2024-05-01&fechaFin=2024-05-31

Response: 200 OK
{
  "periodo": "2024-05",
  "ingresoTotal": 25500.00,
  "facturado": 25500.00,
  "pagado": 18200.00,
  "pendiente": 7300.00,
  "por_dentista": {...},
  "por_tratamiento": {...}
}
```

### 2. Pendientes de Cobro
```
GET /reportes/cobranza/pendientes

Response: 200 OK
{
  "total": 7300.00,
  "facturas": [
    {
      "numeroFactura": "FAC-2024-04-001",
      "paciente": "Juan García López",
      "importe": 163.96,
      "diasVencimiento": -7,
      "estado": "VENCIDA"
    }
  ]
}
```

### 3. Actividad Clínica
```
GET /reportes/actividad?mesAnio=2024-05

Response: 200 OK
{
  "citasTotales": 245,
  "citasCompletadas": 238,
  "citasCanceladas": 4,
  "citasNoPresentado": 3,
  "tasaOcupacion": 92.5,
  "pacientesAtendidos": 185,
  "pacientesNuevos": 12
}
```

---

## CÓDIGOS DE ERROR

| Código | Descripción |
|--------|-------------|
| 400 | Bad Request - Datos inválidos |
| 401 | Unauthorized - Token inválido/expirado |
| 403 | Forbidden - Permisos insuficientes |
| 404 | Not Found - Recurso no existe |
| 409 | Conflict - Datos duplicados |
| 422 | Unprocessable Entity - Validación fallida |
| 429 | Too Many Requests - Rate limit excedido |
| 500 | Internal Server Error |

---

## RATE LIMITING
- **Límite por usuario**: 1000 requests/hora
- **Límite por IP**: 5000 requests/hora
- **Headers**: `X-RateLimit-Limit`, `X-RateLimit-Remaining`

---

## VERSIONADO
- Versión actual: **v1**
- Deprecación: 18 meses de soporte
- Nueva versión: Al menos 6 meses de compatibilidad con anterior

---

## SEGURIDAD ADICIONAL
- ✅ HTTPS/TLS 1.3 obligatorio
- ✅ CORS restringido a dominios autorizados
- ✅ CSP headers implementados
- ✅ Input validation y sanitización
- ✅ Rate limiting y DDoS protection
- ✅ Logging de todos los accesos
- ✅ Encriptación end-to-end para datos sensibles
