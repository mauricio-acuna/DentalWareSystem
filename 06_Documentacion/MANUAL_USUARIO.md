# Manual de Usuario
## OdontoSystems / DentalWareSystem

**Version**: 1.0  
**Fecha**: 2026-05-28  
**Audiencia**: recepcion, personal administrativo, odontologos, higienistas y direccion.

---

## 1. Alcance Actual

La version actual del backend permite operar los flujos base mediante API:

- Pacientes.
- Citas.
- Inventario.
- Facturas y pagos.

Cuando exista frontend, este manual debe ampliarse con capturas de pantalla y pasos visuales.

---

## 2. Roles de Usuario

| Rol | Funciones |
|-----|-----------|
| Recepcion | Alta de pacientes, agenda, confirmaciones |
| Clinico | Consulta de pacientes, citas y registro clinico futuro |
| Administracion | Facturas, pagos, inventario |
| Direccion | Reportes, indicadores, supervision |
| Administrador | Usuarios, permisos, configuracion |

---

## 3. Alta de Paciente

Datos obligatorios:

- Numero de identificacion.
- Nombre.
- Apellidos.
- Fecha de nacimiento.
- Email.
- Consentimiento RGPD.

Reglas:

- No se puede crear un paciente sin consentimiento RGPD.
- El email y el numero de identificacion no pueden repetirse.
- La fecha de nacimiento no puede estar en el futuro.

Ejemplo API:

```http
POST /api/v1/pacientes
Content-Type: application/json
```

```json
{
  "numeroIdentificacion": "12345678X",
  "nombreCompleto": "Ana",
  "apellidos": "Garcia Lopez",
  "fechaNacimiento": "1990-05-15",
  "genero": "F",
  "email": "ana@example.com",
  "telefonoMovil": "+34612345678",
  "consentimientoRGPD": true
}
```

---

## 4. Gestion de Citas

Acciones disponibles:

- Crear cita.
- Consultar cita.
- Listar citas por paciente.
- Confirmar cita.
- Reprogramar cita.

Reglas:

- La cita no puede estar en el pasado.
- La hora de fin debe ser posterior a la hora de inicio.
- La duracion permitida esta entre 15 y 240 minutos.
- Una cita cancelada no puede confirmarse.

Ejemplo:

```http
POST /api/v1/citas
```

```json
{
  "idPaciente": "uuid-del-paciente",
  "fechaCita": "2026-06-01",
  "horaInicio": "10:00",
  "horaFin": "10:30",
  "tipoCita": "CONSULTA",
  "motivoConsulta": "Revision general",
  "prioridad": "MEDIA"
}
```

---

## 5. Inventario

Acciones disponibles:

- Crear insumo.
- Listar insumos.
- Filtrar por categoria y estado.
- Ajustar stock.

Buenas practicas:

- Registrar lote y caducidad cuando aplique.
- Mantener stock minimo realista.
- Revisar alertas de bajo stock antes de cerrar el dia.

Ejemplo de ajuste:

```http
PATCH /api/v1/inventario/{idInsumo}/stock?cantidad=-2
```

---

## 6. Facturacion y Pagos

Acciones disponibles:

- Crear factura.
- Consultar factura.
- Listar facturas pendientes.
- Registrar pago.

Reglas:

- Una factura debe tener al menos una linea.
- El total se calcula como base imponible + IVA.
- Una factura queda `PAGADA` cuando los pagos cubren el total.
- Pagos inferiores dejan la factura en estado `PARCIAL`.

Ejemplo:

```json
{
  "idPaciente": "uuid-del-paciente",
  "tipoFactura": "ORDINARIA",
  "diasCredito": 30,
  "lineas": [
    {
      "concepto": "Consulta general",
      "cantidad": 1,
      "precioUnitario": 100.00,
      "ivaPercent": 21
    }
  ]
}
```

---

## 7. Errores Frecuentes

| Error | Causa | Accion |
|-------|-------|--------|
| 400 | Datos invalidos | Revisar campos obligatorios y formatos |
| 404 | Registro no encontrado | Verificar identificador |
| 409 | Regla de negocio incumplida | Revisar duplicados, RGPD u horarios |
| 500 | Error interno | Contactar soporte tecnico |

---

## 8. Buenas Practicas

- Confirmar identidad del paciente antes de modificar datos.
- No registrar informacion clinica en campos administrativos.
- Mantener actualizados telefono y email.
- Registrar pagos el mismo dia de recepcion.
- Notificar incidencias de privacidad inmediatamente.

---

## 9. Soporte

Informacion que debe incluir un ticket:

- Usuario afectado.
- Fecha y hora.
- Pantalla o endpoint.
- Mensaje de error.
- Pasos para reproducir.
- Si contiene datos personales, no copiar datos sensibles completos.

