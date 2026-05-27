# Manual de Mantenimiento
## OdontoSystems / DentalWareSystem

**Version**: 1.0  
**Fecha**: 2026-05-28  
**Audiencia**: equipo tecnico, DevOps, soporte y seguridad.

---

## 1. Rutinas Diarias

- Revisar health check de la API.
- Revisar errores 5xx.
- Revisar latencia p95/p99.
- Confirmar ejecucion de backups.
- Revisar alertas de seguridad.
- Verificar espacio en disco.

Comando local:

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/actuator/health
```

---

## 2. Rutinas Semanales

- Revisar dependencias vulnerables.
- Revisar logs de auditoria.
- Revisar usuarios con permisos elevados.
- Ejecutar pruebas automatizadas.
- Revisar tareas bloqueadas del dashboard.
- Revisar crecimiento de base de datos y documentos.

Backend:

```powershell
cd codigo/backend
$env:JAVA_HOME="C:\Program Files\Java\jdk-17"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
mvn.cmd verify
```

---

## 3. Rutinas Mensuales

- Prueba de restauracion de backup.
- Revision de certificados TLS.
- Revision de reglas de firewall/WAF.
- Revision de metricas de rendimiento.
- Actualizacion de documentacion operativa.
- Revision de cumplimiento RGPD.

---

## 4. Rutinas Trimestrales

- Simulacro de recuperacion ante desastre.
- Revision de roles y permisos.
- Rotacion de secretos.
- Analisis de dependencias obsoletas.
- Prueba de carga controlada.
- Revision de incidentes y acciones correctivas.

---

## 5. Mantenimiento de Base de Datos

Tareas:

- Verificar backups.
- Revisar indices lentos.
- Analizar queries con alto coste.
- Revisar tablas con crecimiento anomalo.
- Ejecutar vacuum/analyze segun configuracion gestionada.

Indicadores:

- Conexiones activas.
- Bloqueos.
- Tamaño por tabla.
- Queries sobre 1 segundo.
- Ratio cache hit.

---

## 6. Mantenimiento de Aplicacion

Antes de actualizar:

- Leer changelog.
- Ejecutar `mvn verify`.
- Crear backup si hay migraciones.
- Desplegar primero en staging.
- Ejecutar smoke tests.
- Preparar rollback.

Despues de actualizar:

- Verificar health.
- Revisar logs 30 minutos.
- Revisar metricas de error.
- Confirmar flujos criticos.

---

## 7. Gestion de Incidentes

Severidades:

| Severidad | Ejemplo | Respuesta |
|-----------|---------|-----------|
| SEV1 | Sistema caido o brecha de datos | Inmediata |
| SEV2 | Funcion critica degradada | Menos de 2 horas |
| SEV3 | Bug con workaround | Menos de 1 dia |
| SEV4 | Mejora o incidencia menor | Planificacion |

Pasos:

1. Registrar incidente.
2. Clasificar severidad.
3. Nombrar responsable.
4. Mitigar impacto.
5. Comunicar a usuarios afectados.
6. Resolver causa raiz.
7. Hacer postmortem.
8. Crear acciones preventivas.

Si hay datos personales afectados, activar protocolo RGPD y evaluar notificacion a AEPD en plazo maximo de 72 horas.

---

## 8. Backups y Restauracion

Validacion minima de restauracion:

- Restaurar backup en entorno aislado.
- Ejecutar conteos de tablas criticas.
- Validar integridad referencial.
- Probar login y flujos principales.
- Registrar evidencia.

Nunca restaurar un backup sobre produccion sin aprobacion explicita del responsable tecnico y de negocio.

---

## 9. Actualizacion de Dependencias

Politica:

- Parches de seguridad criticos: menos de 72 horas.
- Parches menores: sprint siguiente.
- Versiones mayores: plan tecnico y pruebas de regresion.

Herramientas recomendadas:

- Dependabot o Renovate.
- OWASP Dependency-Check.
- Trivy para imagenes.
- SBOM con CycloneDX.

---

## 10. Runbook Rapido

API no responde:

1. Verificar proceso o pod.
2. Verificar puerto 8080.
3. Verificar conexion a BD.
4. Revisar logs recientes.
5. Reiniciar replica si es seguro.
6. Activar rollback si el fallo viene de despliegue.

Errores de BD:

1. Revisar estado PostgreSQL.
2. Revisar conexiones maximas.
3. Revisar credenciales/secretos.
4. Revisar migraciones recientes.
5. Restaurar servicio antes de investigar optimizaciones.

Facturacion inconsistente:

1. Congelar cambios manuales sobre facturas afectadas.
2. Exportar evidencia.
3. Revisar pagos asociados.
4. Aplicar ajuste contable o factura rectificativa.
5. Registrar auditoria.

