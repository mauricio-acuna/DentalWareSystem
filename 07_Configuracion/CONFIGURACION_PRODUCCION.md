# Configuracion de Produccion
## OdontoSystems / DentalWareSystem

**Version**: 1.0  
**Fecha**: 2026-05-28

---

## 1. Principios

- Los secretos no se guardan en Git.
- Las credenciales se rotan y se auditan.
- Produccion usa PostgreSQL, no H2.
- La consola H2 se desactiva siempre en produccion.
- Los cambios de configuracion pasan por revision de pares.

---

## 2. Perfil de Produccion

Variables base:

```env
APP_ENVIRONMENT=production
SERVER_PORT=8080
H2_CONSOLE=false
JPA_DDL_AUTO=validate
LOG_LEVEL=INFO
```

Base de datos:

```env
DB_URL=jdbc:postgresql://postgres.internal:5432/odonto
DB_USERNAME=odonto_app
DB_PASSWORD=${SECRET_DB_PASSWORD}
DB_DRIVER=org.postgresql.Driver
DATABASE_SSL_MODE=require
DATABASE_POOL_SIZE=20
```

Seguridad:

```env
CORS_ALLOWED_ORIGINS=https://app.dentalware.example
SESSION_TIMEOUT_MINUTES=60
MAX_LOGIN_ATTEMPTS=5
PASSWORD_MIN_LENGTH=12
ENABLE_2FA=true
```

Observabilidad:

```env
MANAGEMENT_ENDPOINTS=health,info,metrics,prometheus
SENTRY_ENVIRONMENT=production
TRACE_SAMPLE_RATE=0.05
```

---

## 3. Secretos

Secretos obligatorios:

- `DB_PASSWORD`
- `JWT_PRIVATE_KEY` y `JWT_PUBLIC_KEY` cuando se implemente JWT RS256.
- `ENCRYPTION_KEY`
- Credenciales SMTP/SMS.
- Credenciales de almacenamiento de documentos.
- API keys de integraciones externas.

Herramientas recomendadas:

- AWS Secrets Manager.
- Azure Key Vault.
- Google Secret Manager.
- HashiCorp Vault.

Reglas:

- Rotacion trimestral de secretos tecnicos.
- Rotacion inmediata tras salida de personal con acceso privilegiado.
- Acceso minimo necesario por entorno.
- Auditoria de lectura de secretos.

---

## 4. Base de Datos

Configuracion recomendada:

- PostgreSQL 15+.
- Cifrado en reposo.
- TLS obligatorio.
- Backups diarios completos y PITR si el proveedor lo permite.
- Replica de lectura para reportes cuando el volumen crezca.
- Usuario de aplicacion sin permisos de superusuario.

Parametros iniciales:

```text
max_connections: segun pool total + margen operativo
shared_buffers: 25% memoria disponible
work_mem: ajustar tras pruebas de carga
statement_timeout: 30s
idle_in_transaction_session_timeout: 60s
```

---

## 5. Logs

Formato recomendado: JSON.

Campos minimos:

- timestamp
- level
- requestId
- userId o identificador pseudonimo
- clinicId
- module
- action
- entity
- status
- latencyMs
- ip

No registrar:

- Contraseñas.
- Tokens.
- Numeros completos de identificacion.
- Diagnosticos o notas clinicas completas en logs tecnicos.

---

## 6. Seguridad HTTP

Headers esperados:

```text
Strict-Transport-Security: max-age=31536000; includeSubDomains
X-Content-Type-Options: nosniff
X-Frame-Options: SAMEORIGIN
Referrer-Policy: strict-origin-when-cross-origin
Content-Security-Policy: default-src 'self'
```

Rate limiting inicial:

- 1000 requests/hora por usuario autenticado.
- 5000 requests/hora por IP.
- Endpoints de login con limites mas estrictos.

---

## 7. Backups

Politica inicial:

| Activo | Frecuencia | Retencion |
|--------|------------|-----------|
| PostgreSQL completo | Diario | 35 dias |
| PostgreSQL incremental/PITR | Continuo | 7-14 dias |
| Documentos clinicos | Diario | 10 anos segun politica |
| Configuracion | Por cambio | Indefinida |

Pruebas:

- Restauracion tecnica mensual.
- Simulacro de desastre trimestral.
- Evidencia documentada de cada prueba.

---

## 8. Hardening de Runtime

- Ejecutar contenedores como usuario no root.
- Filesystem de contenedor de solo lectura donde sea posible.
- Limitar memoria y CPU.
- Deshabilitar shell y herramientas innecesarias en imagen final.
- Escanear dependencias e imagenes en CI.
- Publicar SBOM en releases formales.

---

## 9. Feature Flags

Usar flags para:

- Nuevas integraciones externas.
- Servicios de IA.
- Cambios de facturacion.
- Cambios de permisos.
- Funcionalidades por clinica o piloto.

Cada flag debe tener:

- Responsable.
- Fecha de expiracion.
- Plan de eliminacion.
- Estado por entorno.

