# Roadmap Tecnico, Stack y Mejoras Futuras
## OdontoSystems / DentalWareSystem

**Version**: 1.0  
**Fecha**: 2026-05-28

---

## 1. Estado Actual

Backend MVP:

- Java 17.
- Spring Boot 3.2.
- Spring Web.
- Spring Data JPA.
- Spring Security base.
- H2 local y PostgreSQL-ready.
- Pruebas de integracion con MockMvc.
- JaCoCo como puerta inicial de cobertura.

Modulos implementados:

- Pacientes.
- Citas.
- Inventario.
- Facturas y pagos.

---

## 2. Mejoras Prioritarias

### P0 - Antes de Produccion Real

- Autenticacion y autorizacion real con JWT/OIDC.
- RBAC por roles clinicos y administrativos.
- Auditoria persistente de accesos y cambios.
- Migraciones de base de datos con Flyway o Liquibase.
- Configuracion por perfiles `local`, `test`, `staging`, `prod`.
- Cifrado de datos sensibles.
- Gestion formal de secretos.
- Endpoint de metricas Prometheus.
- CI/CD con tests, analisis estatico y escaneo de dependencias.

### P1 - MVP Clinico Completo

- Tratamientos y sesiones.
- Historial clinico.
- Diagnosticos.
- Documentos clinicos.
- Consentimientos versionados.
- Adjuntos y radiografias.
- Proveedores y compras.
- Movimientos de inventario.

### P2 - Escalabilidad y Experiencia

- Frontend web.
- Busqueda avanzada.
- Notificaciones por email/SMS.
- Exportaciones PDF/Excel.
- Multi-clinica.
- Reportes operativos.
- Dashboard de direccion.

---

## 3. Mejoras de Stack Tecnologico

Backend:

- Mantener Spring Boot y Java 17 o subir a Java LTS posterior cuando el ecosistema lo permita.
- Agregar MapStruct para mapeo DTO-entidad si crece la capa de transformacion.
- Agregar Flyway para migraciones.
- Agregar Testcontainers para pruebas con PostgreSQL real.
- Agregar OpenTelemetry para trazas distribuidas.

Frontend futuro:

- React + TypeScript.
- Vite.
- TanStack Query para sincronizacion server-state.
- React Hook Form + Zod para formularios.
- Playwright para E2E.
- Design system propio con componentes accesibles.

Infraestructura:

- Docker para empaquetado.
- Kubernetes/ECS para produccion.
- Terraform para infraestructura.
- GitHub Actions para CI/CD.
- Prometheus + Grafana para metricas.
- Loki/OpenSearch para logs.

---

## 4. Mejoras de Diseno de Dominio

Recomendaciones:

- Separar paquetes por modulo cuando el codigo crezca:
  - `pacientes`
  - `agenda`
  - `facturacion`
  - `inventario`
  - `seguridad`
  - `auditoria`
- Introducir servicios de dominio para reglas clinicas complejas.
- Evitar que controladores conozcan detalles de persistencia.
- Crear eventos de dominio para acciones auditables.
- Modelar estados con transiciones explicitas.

Estados que requieren maquina de estados:

- Cita.
- Tratamiento.
- Factura.
- Presupuesto.
- Pedido.
- Proceso RGPD.

---

## 5. Calidad

Objetivo inicial:

- Cobertura lineal minima: 60%.
- Cobertura de servicios criticos: 80%.
- Pruebas de contrato para API.
- Pruebas E2E para flujos principales.
- Pruebas de seguridad automatizadas.

Suite recomendada:

- Unitarias: reglas de negocio.
- Integracion: repositorios y servicios.
- API: MockMvc o RestAssured.
- Contrato: OpenAPI validation.
- E2E: Playwright cuando exista frontend.
- Carga: k6.

---

## 6. Observabilidad

Metricas clave:

- Latencia por endpoint.
- Error rate 4xx/5xx.
- Throughput.
- Conexiones DB.
- Tiempo de queries.
- Creacion de citas por dia.
- Facturas emitidas y cobradas.
- Stock bajo.
- Intentos fallidos de login.

Trazas:

- Request ID obligatorio.
- Propagacion entre servicios.
- Trazas en integraciones externas.

Logs:

- JSON estructurado.
- Sin datos clinicos sensibles.
- Correlacion por usuario, clinica y request.

---

## 7. Seguridad Futura

- OIDC con proveedor externo.
- MFA obligatorio para perfiles clinicos y administradores.
- Politicas de contrasena y bloqueo.
- Scopes por modulo.
- Consentimientos versionados.
- Enmascaramiento de datos sensibles.
- Deteccion de accesos anormales.
- Export RGPD auditado.
- Borrado logico y anonimizacion controlada.

