# Dashboard de Tareas
## OdontoSystems / DentalWareSystem

**Version**: 1.0  
**Fecha**: 2026-05-28

---

## 1. Objetivo

Este documento define la estructura recomendada para gestionar el proyecto en GitHub Projects, Jira, Linear, Azure DevOps o Trello.

---

## 2. Estados del Tablero

| Estado | Significado |
|--------|-------------|
| Inbox | Ideas sin refinar |
| Ready | Lista para tomar |
| In Progress | En desarrollo |
| Review | En PR/revision |
| QA | Validacion funcional |
| Security Review | Revision RGPD/seguridad |
| Ready to Release | Lista para despliegue |
| Done | Entregada y validada |
| Blocked | Bloqueada por dependencia |

---

## 3. Campos Recomendados

| Campo | Valores |
|-------|---------|
| Tipo | Feature, Bug, Tech Debt, Security, Documentation, Operations |
| Prioridad | P0, P1, P2, P3 |
| Modulo | Pacientes, Citas, Tratamientos, Facturacion, Inventario, Seguridad, Plataforma |
| Riesgo RGPD | Bajo, Medio, Alto, Critico |
| Entorno | Local, Dev, Staging, Prod |
| Release | 1.0.0, 1.1.0, etc. |
| Responsable | Persona o equipo |
| Fecha objetivo | Fecha estimada |
| Dependencias | Issues relacionados |

---

## 4. Epicas Iniciales

### EPIC-001 Plataforma Backend MVP

Objetivo: estabilizar el backend actual.

Tareas:

- [ ] Normalizar configuracion por perfiles Spring.
- [ ] Agregar migraciones Flyway.
- [ ] Implementar autenticacion JWT real.
- [ ] Agregar auditoria de eventos.
- [ ] Endurecer CORS por entorno.

### EPIC-002 Gestion Clinica

Objetivo: cubrir el ciclo clinico completo.

Tareas:

- [ ] Tratamientos con sesiones.
- [ ] Diagnosticos.
- [ ] Historial clinico.
- [ ] Documentos clinicos y consentimientos.
- [ ] Adjuntos y radiografias.

### EPIC-003 Administracion y Finanzas

Objetivo: cerrar ciclo de facturacion y cobro.

Tareas:

- [ ] Numeracion fiscal configurable.
- [ ] Facturas rectificativas.
- [ ] Export contable.
- [ ] Pagos parciales.
- [ ] Reportes de cobranza.

### EPIC-004 Inventario y Compras

Objetivo: controlar stock, caducidad y proveedores.

Tareas:

- [ ] Movimientos de inventario.
- [ ] Alertas de stock minimo.
- [ ] Proveedores.
- [ ] Pedidos.
- [ ] Recepcion y control de calidad.

### EPIC-005 IA e Integraciones

Objetivo: integrar servicios externos de forma segura.

Tareas:

- [ ] Gateway de integraciones.
- [ ] Cola de eventos.
- [ ] Resumen asistido de notas clinicas.
- [ ] Recordatorios inteligentes.
- [ ] Deteccion de anomalias en agenda/facturacion.

---

## 5. Plantilla de Tarea

```markdown
## Contexto

## Objetivo

## Criterios de aceptacion
- [ ] 
- [ ] 
- [ ] 

## Impacto RGPD/seguridad

## Pruebas requeridas

## Dependencias

## Notas de despliegue
```

---

## 6. Indicadores de Proyecto

Seguimiento semanal:

- Lead time por tarea.
- Cycle time por PR.
- Defectos encontrados en QA.
- Bugs productivos por release.
- Cobertura automatizada.
- Tiempo medio de recuperacion ante incidente.
- Porcentaje de tareas con criterios de aceptacion claros.

---

## 7. Dashboard Operativo

Paneles recomendados:

- Release actual: avance por epica.
- Riesgo: tareas P0/P1 y bloqueadas.
- Calidad: pruebas, cobertura, bugs abiertos.
- Seguridad: issues con riesgo RGPD alto o critico.
- Operaciones: despliegues, incidentes y acciones postmortem.

