# Despliegue de Produccion
## OdontoSystems / DentalWareSystem

**Version**: 1.0  
**Fecha**: 2026-05-28  
**Alcance**: Backend Spring Boot actual y arquitectura objetivo con frontend, PostgreSQL, observabilidad y servicios externos.

---

## 1. Objetivo

Este documento define una ruta de despliegue reproducible para pasar de desarrollo local a produccion sin depender de pasos manuales no documentados.

El sistema actual incluye un backend Spring Boot en `codigo/backend`, con API REST bajo `/api/v1`, H2 para desarrollo y configuracion preparada para PostgreSQL.

---

## 2. Entornos

| Entorno | Uso | Datos | Infraestructura | Despliegue |
|---------|-----|-------|-----------------|------------|
| local | Desarrollo individual | Sinteticos | H2 o PostgreSQL local | Manual |
| dev | Integracion temprana | Sinteticos | Docker Compose | CI/CD |
| staging | Validacion preproduccion | Anonimizados | Igual a produccion a menor escala | CI/CD con aprobacion |
| prod | Operacion clinica real | Reales y sensibles | Alta disponibilidad | CI/CD con change record |

Regla: ningun dato personal o clinico real debe usarse en `local` o `dev`.

---

## 3. Requisitos de Produccion

### 3.1 Infraestructura minima

- 2 instancias de aplicacion, 2 vCPU y 4 GB RAM cada una.
- PostgreSQL 15+ gestionado, cifrado en reposo y backups automatizados.
- Almacenamiento privado para documentos clinicos, radiografias y facturas.
- Redis gestionado para cache, rate limiting y sesiones si se activa autenticacion distribuida.
- Balanceador TLS con certificados gestionados.
- Red privada entre aplicacion, base de datos y cache.

### 3.2 Requisitos recomendados

- Kubernetes o ECS/Fargate para escalado y despliegues rolling.
- WAF delante de la API publica.
- Secret manager para claves y credenciales.
- SIEM o centralizacion de logs para auditoria.
- Monitorizacion con metricas, trazas y alertas.

---

## 4. Preparacion del Artefacto

Desde `codigo/backend`:

```powershell
$env:JAVA_HOME="C:\Program Files\Java\jdk-17"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
mvn.cmd verify
```

Salida esperada:

- Compilacion correcta.
- Pruebas automatizadas en verde.
- JaCoCo supera el umbral configurado.
- JAR generado en `target/odonto-backend-1.0.0.jar`.

---

## 5. Variables de Entorno Minimas

```env
SERVER_PORT=8080
JPA_DDL_AUTO=validate
DB_URL=jdbc:postgresql://postgres.internal:5432/odonto
DB_USERNAME=odonto_app
DB_PASSWORD=usar_secret_manager
DB_DRIVER=org.postgresql.Driver
H2_CONSOLE=false
LOG_LEVEL=INFO
```

Para produccion, `JPA_DDL_AUTO` debe ser `validate`. Las migraciones de esquema deben ejecutarse con una herramienta formal como Flyway o Liquibase antes de iniciar la aplicacion.

---

## 6. Dockerfile Recomendado

```dockerfile
FROM eclipse-temurin:17-jre-alpine

RUN addgroup -S odonto && adduser -S odonto -G odonto
WORKDIR /app

COPY target/odonto-backend-1.0.0.jar /app/app.jar

USER odonto
EXPOSE 8080

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "/app/app.jar"]
```

Build:

```bash
mvn verify
docker build -t dentalware/odonto-backend:1.0.0 .
```

---

## 7. Estrategia de Despliegue

### 7.1 Rolling deployment

Uso recomendado por defecto:

- Publicar nueva imagen con tag semantico.
- Desplegar una replica nueva.
- Verificar readiness.
- Retirar replicas anteriores progresivamente.

### 7.2 Blue/Green

Uso recomendado para cambios con riesgo:

- Mantener entorno `blue` estable.
- Desplegar nueva version en `green`.
- Ejecutar smoke tests contra `green`.
- Cambiar trafico en el balanceador.
- Mantener rollback rapido a `blue`.

### 7.3 Canary

Uso recomendado para funcionalidades nuevas:

- Enviar 5% del trafico a la version nueva.
- Observar errores, latencia y metricas clinicas.
- Subir a 25%, 50%, 100% si no hay alertas.

---

## 8. Checklist Pre-Go-Live

- [ ] `mvn verify` completado.
- [ ] Imagen firmada o publicada desde CI confiable.
- [ ] Variables de entorno validadas.
- [ ] Secretos cargados en secret manager.
- [ ] Migraciones de BD ejecutadas y verificadas.
- [ ] Backups recientes probados con restauracion.
- [ ] TLS activo.
- [ ] Logs de auditoria activos.
- [ ] Health check responde `UP`.
- [ ] Swagger deshabilitado o restringido si la politica lo exige.
- [ ] Plan de rollback aprobado.

---

## 9. Smoke Tests

```bash
curl -f https://api.clinica.example/api/v1/actuator/health
curl -f https://api.clinica.example/api/v1/v3/api-docs
```

Flujos minimos:

- Crear paciente sintetico.
- Crear cita para paciente sintetico.
- Crear factura sintetica.
- Registrar pago sintetico.
- Verificar que se generan eventos de log/auditoria.

---

## 10. Rollback

Rollback de aplicacion:

1. Cambiar imagen a la version anterior.
2. Reiniciar replicas con readiness activo.
3. Verificar health check.
4. Confirmar que no hay errores 5xx.

Rollback de base de datos:

- Evitar migraciones destructivas.
- Toda migracion debe tener plan `down` o script compensatorio.
- Si se requiere restauracion, activar procedimiento de incidente y usar backup verificado.

---

## 11. Responsabilidades

| Rol | Responsabilidad |
|-----|-----------------|
| Tech Lead | Aprueba release tecnico |
| DevOps | Ejecuta despliegue y rollback |
| QA | Certifica smoke tests |
| DPO/Seguridad | Valida cambios con impacto RGPD |
| Direccion Clinica | Autoriza go-live funcional |

