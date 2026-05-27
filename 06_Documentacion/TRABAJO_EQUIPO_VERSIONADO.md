# Trabajo en Equipo y Versionado
## OdontoSystems / DentalWareSystem

**Version**: 1.0  
**Fecha**: 2026-05-28

---

## 1. Modelo de Ramas

Modelo recomendado: trunk-based development con ramas cortas.

| Rama | Uso | Proteccion |
|------|-----|------------|
| `main` | Codigo estable y desplegable | Obligatoria |
| `codex/*` | Cambios realizados por Codex | Pull request |
| `feature/*` | Funcionalidades del equipo | Pull request |
| `fix/*` | Correcciones | Pull request |
| `release/*` | Preparacion de version | Pull request + aprobacion |
| `hotfix/*` | Incidentes productivos | Pull request urgente |

Reglas:

- No hacer commits directos a `main`.
- Todo cambio debe pasar por PR.
- PR pequeno y revisable: idealmente menos de 400 lineas de diff funcional.
- Cada PR debe incluir pruebas o justificar por que no aplican.

---

## 2. Convencion de Commits

Formato:

```text
tipo(scope): descripcion corta
```

Tipos:

- `feat`: nueva funcionalidad.
- `fix`: correccion.
- `docs`: documentacion.
- `test`: pruebas.
- `refactor`: cambio interno sin cambio funcional.
- `chore`: tareas de mantenimiento.
- `ci`: pipeline.
- `security`: seguridad o cumplimiento.

Ejemplos:

```text
feat(pacientes): agrega alta con consentimiento rgpd
test(citas): cubre validacion de horarios
docs(prod): documenta despliegue y rollback
security(auth): endurece cabeceras http
```

---

## 3. Versionado Semantico

Usar SemVer:

```text
MAJOR.MINOR.PATCH
```

- `MAJOR`: cambios incompatibles de API o datos.
- `MINOR`: nuevas funcionalidades compatibles.
- `PATCH`: correcciones compatibles.

Ejemplo:

- `1.0.0`: primer MVP funcional.
- `1.1.0`: modulo de tratamientos.
- `1.1.1`: correccion de calculo de IVA.
- `2.0.0`: cambio incompatible en contratos API.

---

## 4. Versionado de API

La API publica debe versionarse en URL:

```text
/api/v1
/api/v2
```

Politica:

- Mantener versiones obsoletas durante 18 meses cuando haya clientes externos.
- Documentar deprecaciones en OpenAPI y changelog.
- No romper contratos dentro de la misma version mayor.

Cambios compatibles:

- Agregar campos opcionales.
- Agregar endpoints.
- Agregar valores enum si clientes los toleran.

Cambios incompatibles:

- Renombrar campos.
- Cambiar tipos.
- Eliminar campos o endpoints.
- Cambiar semantica de estados.

---

## 5. Pull Requests

Checklist minimo:

- [ ] Descripcion clara del cambio.
- [ ] Impacto funcional explicado.
- [ ] Impacto RGPD/seguridad evaluado.
- [ ] Pruebas ejecutadas.
- [ ] Migraciones documentadas si aplica.
- [ ] Rollback descrito si toca produccion.

Plantilla sugerida:

```markdown
## Que cambia

## Por que

## Como se probo

## Riesgos

## Rollback
```

---

## 6. Definition of Ready

Una tarea esta lista para desarrollo cuando tiene:

- Problema o necesidad definida.
- Criterios de aceptacion.
- Datos o pantallas afectadas.
- Dependencias identificadas.
- Impacto de seguridad/RGPD estimado.
- Prioridad de negocio.

---

## 7. Definition of Done

Una tarea esta terminada cuando:

- El codigo esta en `main`.
- CI esta en verde.
- Pruebas automatizadas relevantes existen.
- Documentacion actualizada.
- Logs y metricas necesarios incorporados.
- No quedan secretos ni datos reales en el repo.
- La funcionalidad fue validada por QA o responsable de producto.

---

## 8. Cadencia de Equipo

Ritmo recomendado:

- Sprint semanal o quincenal.
- Refinamiento: 1 vez por semana.
- Demo: al final de cada sprint.
- Retrospectiva: al final de cada sprint.
- Revision de seguridad: quincenal durante MVP.
- Revision de arquitectura: mensual.

---

## 9. Roles

| Rol | Responsabilidad |
|-----|-----------------|
| Product Owner | Prioriza valor clinico y administrativo |
| Tech Lead | Diseno tecnico y calidad |
| Backend | API, dominio, datos, integraciones |
| Frontend | Experiencia de usuario |
| QA | Pruebas funcionales y regresion |
| DevOps | CI/CD, despliegue, observabilidad |
| DPO/Seguridad | RGPD, auditoria y controles |

