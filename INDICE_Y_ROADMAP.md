# ÍNDICE MAESTRO Y ROADMAP DE IMPLEMENTACIÓN
## Sistema de Información - Clínica Odontológica

---

## ÍNDICE MAESTRO DE DOCUMENTACIÓN

### 📋 DOCUMENTOS PRINCIPALES

#### 1. ESTRATEGIA Y VISIÓN
- **RESUMEN_EJECUTIVO.md** - Descripción completa del proyecto, objetivos, presupuesto
  - Resumen ejecutivo para dirección
  - Objetivos estratégicos
  - Presupuesto y timeline
  - Análisis de riesgos

#### 2. ARQUITECTURA
- **01_Arquitectura/README.md** - Descripción de la arquitectura del sistema
  - Stack tecnológico recomendado
  - Principios de diseño
  - Módulos principales
  - Estándares de cumplimiento

#### 3. BASE DE DATOS
- **02_BaseDatos/ESQUEMA_LOGICO.md** - Definición de todas las entidades y relaciones
  - 25+ entidades principales
  - Descripción completa de campos
  - Restricciones y validaciones
  - Relaciones entre tablas
  
- **02_BaseDatos/DDL_PostgreSQL.sql** - Script SQL de creación
  - Tablas con constraints
  - Índices de rendimiento
  - Funciones de auditoría
  - Permisos y roles
  - Vistas útiles

#### 4. MODELOS DE DATOS
- **03_Modelos/MODELOS_DATOS.json** - Definición de modelos en formato JSON
  - Estructura de datos de cada entidad
  - Tipos de campos
  - Validaciones
  - Restricciones globales

#### 5. APIS Y SERVICIOS
- **04_API/ESPECIFICACION_REST_API.md** - Especificación completa de APIs REST
  - Base URL y autenticación
  - 40+ endpoints documentados
  - Ejemplos de request/response
  - Códigos de error
  - Rate limiting

#### 6. SEGURIDAD
- **05_Seguridad/POLITICA_SEGURIDAD_CUMPLIMIENTO.md** - Políticas de seguridad
  - Cumplimiento RGPD
  - ISO 27001 - Seguridad de Información
  - UNE-EN ISO 9001 - Calidad
  - Normativa sanitaria europea
  - Gestión de incidentes
  - Controles de seguridad implementados

#### 7. IMPLEMENTACIÓN
- **06_Documentacion/GUIA_IMPLEMENTACION.md** - Guía paso a paso
  - Requisitos previos
  - Instalación de dependencias
  - Configuración de BD
  - Despliegue de aplicación
  - Backups y recuperación
  - Monitoreo y alertas
  - Testing
  - Go-live

- **06_Documentacion/PROCESOS_NEGOCIO.md** - Workflows de negocio
  - Proceso de gestión de pacientes
  - Gestión de citas
  - Realización de tratamientos
  - Facturación y pagos
  - Gestión de inventario
  - Auditoría y cumplimiento

- **06_Documentacion/MANUAL_USUARIO.md** - Manual operativo de usuario
  - Alta de pacientes
  - Gestión de citas
  - Inventario
  - Facturación y pagos
  - Errores frecuentes

- **06_Documentacion/MANUAL_MANTENIMIENTO.md** - Manual para soporte y operación técnica
  - Rutinas de mantenimiento
  - Runbooks de incidentes
  - Backups y restauración
  - Actualización de dependencias

- **06_Documentacion/TRABAJO_EQUIPO_VERSIONADO.md** - Gobernanza del desarrollo
  - Ramas, commits y PRs
  - Versionado semántico
  - Versionado de API
  - Definition of Ready y Done

- **06_Documentacion/DASHBOARD_TAREAS.md** - Estructura para tablero de proyecto
  - Estados y campos
  - Épicas iniciales
  - Plantillas de tareas
  - Métricas de seguimiento

- **06_Documentacion/ROADMAP_TECNICO_MEJORAS.md** - Evolución técnica
  - Mejoras P0/P1/P2
  - Stack futuro
  - Calidad, observabilidad y seguridad

- **06_Documentacion/INTEGRACIONES_IA_APIS.md** - Integraciones externas e IA
  - Email, SMS, pagos, contabilidad, FHIR/DICOM
  - IA segura con humano en el circuito
  - Auditoría y evaluación de proveedores

- **06_Documentacion/ESTADO_IMPLEMENTACION_FUNCIONAL.md** - Estado real del codigo frente a la documentacion
  - Funcionalidades implementadas
  - Funcionalidades parciales
  - Funcionalidades pendientes
  - Riesgos antes de produccion

#### 8. CONFIGURACIÓN
- **07_Configuracion/ARCHIVOS_CONFIGURACION.md** - Archivos de configuración
  - Variables de entorno (.env)
  - docker-compose.yml
  - Kubernetes Helm values
  - .gitignore
  - Nginx configuration

- **07_Configuracion/DESPLIEGUE_PRODUCCION.md** - Guía de despliegue productivo
  - Entornos
  - Artefactos
  - Rolling, blue/green, canary
  - Smoke tests y rollback

- **07_Configuracion/CONFIGURACION_PRODUCCION.md** - Configuración productiva
  - Secretos
  - Base de datos
  - Logs
  - Backups
  - Hardening

---

## ROADMAP DE IMPLEMENTACIÓN

### FASE 0: PRE-PROYECTO (Semanas 1-2)

#### Hito 0.1: Constitución del Equipo
- [ ] Contratación de recursos clave
  - 1 Arquitecto de software
  - 1 DBA especialista
  - 1 Especialista seguridad
  - 1 Product Manager
  - 1 Gestor de proyecto
- [ ] Asignación de roles y responsabilidades
- [ ] Definición de governance
- [ ] Establecimiento de comunicación

**Entregables**: Organigrama de proyecto, actas de reunión

#### Hito 0.2: Evaluación de Proveedores
- [ ] Identificación de 3-5 proveedores potenciales
- [ ] Solicitud de propuestas (RFP)
- [ ] Evaluación técnica
- [ ] Negociación de contrato
- [ ] Selección final

**Entregables**: Matriz de decisión, contrato firmado

#### Hito 0.3: Aprobaciones y Financiación
- [ ] Aprobación de presupuesto (450K-700K€)
- [ ] Liberación de fondos
- [ ] Confirmación de recursos
- [ ] Definición del comité de supervisión

**Entregables**: Acuerdo de inversión, cronograma ejecutivo

---

### FASE 1: ANÁLISIS Y DISEÑO (Semanas 3-8 | 6 semanas)

#### Hito 1.1: Análisis de Requisitos Detallado
- [ ] Entrevistas con stakeholders
  - Dentistas (2-3 sesiones)
  - Personal administrativo (2 sesiones)
  - Dirección (1 sesión)
  - Pacientes (encuesta)
- [ ] Validación de requisitos
- [ ] Documentación de casos de uso
- [ ] Definición de criterios de aceptación

**Duración**: 2 semanas  
**Entregables**: Documento de requisitos (50+ páginas), casos de uso, matriz de trazabilidad

#### Hito 1.2: Diseño de Arquitectura
- [ ] Arquitectura técnica completa
- [ ] Diagrama de componentes
- [ ] Diagrama de flujo de datos
- [ ] Matriz de integraciones
- [ ] Decisiones de tecnología
- [ ] Opciones de deployment

**Duración**: 1.5 semanas  
**Entregables**: Documento de arquitectura, diagramas, justificaciones técnicas

#### Hito 1.3: Diseño de Base de Datos
- [ ] Modelo entidad-relación completo
- [ ] Normalización de BD (hasta 3FN)
- [ ] Estrategia de particionamiento
- [ ] Plan de indexación
- [ ] Estrategia de backup
- [ ] Estimación de capacidad

**Duración**: 1.5 semanas  
**Entregables**: Modelo lógico y físico, scripts DDL, documento de diseño

#### Hito 1.4: Diseño de Interfaces
- [ ] Wireframes de todas las pantallas
- [ ] Prototipos interactivos
- [ ] Guía de estilos/branding
- [ ] Flujo de navegación
- [ ] Accesibilidad (WCAG 2.1 AA)

**Duración**: 1 semana  
**Entregables**: Prototipos Figma, documento de diseño UI/UX

#### Hito 1.5: Plan de Seguridad
- [ ] Análisis de amenazas (STRIDE)
- [ ] Matriz de riesgos
- [ ] Especificación de controles
- [ ] Plan de cumplimiento RGPD
- [ ] Plan de auditoría de seguridad
- [ ] Política de gestión de secretos

**Duración**: 1 semana  
**Entregables**: Documento de seguridad, matriz de riesgos, plan de cumplimiento

**TOTAL FASE 1**: 6 semanas  
**Equipo**: 5-7 personas  
**Costo estimado**: 70K-120K€

---

### FASE 2: DESARROLLO (Semanas 9-24 | 16 semanas)

#### Hito 2.1: Configuración de Infraestructura (Semana 9)
- [ ] Provisionamiento de servidores
  - VM en AWS/Azure (Dev, Staging, Prod)
  - Configuración de red
  - Firewall y seguridad
- [ ] Instalación de herramientas
  - Git repository
  - CI/CD pipeline (GitLab CI / Jenkins / GitHub Actions)
  - Monitoreo (Prometheus + Grafana)
  - Logging (ELK Stack)
- [ ] Configuración de BD
  - PostgreSQL en Prod
  - Redis para cache
  - Backups automatizados

**Entregables**: Infraestructura lista, pipelines funcionando

#### Hito 2.2: Desarrollo Backend (Semanas 10-20)
- [ ] Sprint 1-3: API de Pacientes
  - CRUD completo
  - Búsqueda avanzada
  - Validaciones
  - Autenticación/Autorización
  
- [ ] Sprint 4-6: API de Citas
  - Disponibilidad en tiempo real
  - Confirmaciones
  - Cambios/Cancelaciones
  - Recordatorios

- [ ] Sprint 7-9: API de Tratamientos
  - Registro de sesiones
  - Documentación clínica
  - Presupuestos
  - Historial

- [ ] Sprint 10-12: API de Facturación
  - Generación automática
  - Pagos
  - Reportes financieros
  - Integraciones bancarias

**Duración**: 12 semanas  
**Entregables**: APIs completas, documentación, tests unitarios

#### Hito 2.3: Desarrollo Frontend (Semanas 10-22)
- [ ] Sprint 1-2: Infraestructura React
  - Setup proyecto
  - Routing
  - Estado global
  - Componentes base

- [ ] Sprint 3-5: Módulos principales
  - Gestión de pacientes
  - Agenda de citas
  - Tratamientos

- [ ] Sprint 6-8: Módulos secundarios
  - Inventario
  - Facturación
  - Reportes

- [ ] Sprint 9-10: Pulido UI/UX
  - Responsive design
  - Accesibilidad
  - Optimización performance

**Duración**: 10 semanas  
**Entregables**: Frontend funcional, tests, manual de usuario

#### Hito 2.4: Integración de Sistemas (Semanas 21-24)
- [ ] Integración Backend + Frontend
- [ ] Integración con servicios externos
  - Email (SendGrid/AWS SES)
  - SMS (Twilio)
  - Almacenamiento (AWS S3)
  - Análisis (Sentry)
- [ ] Testing de integración
- [ ] Fixes de defectos encontrados

**Duración**: 4 semanas  
**Entregables**: Sistema integrado, pruebas de integración

**TOTAL FASE 2**: 16 semanas  
**Equipo**: 6-8 desarrolladores, 1 DevOps  
**Costo estimado**: 200K-300K€

---

### FASE 3: TESTING Y QA (Semanas 25-28 | 4 semanas)

#### Hito 3.1: Testing Funcional (Semana 25)
- [ ] Casos de prueba exhaustivos
- [ ] Testing en navegadores/dispositivos
- [ ] Testing de flujos críticos
- [ ] Testing de errores/excepciones
- [ ] Testing de rendimiento

**Entregables**: Reporte de testing, lista de defectos

#### Hito 3.2: Testing de Seguridad (Semana 26)
- [ ] Penetration testing
- [ ] Escaneo de vulnerabilidades (OWASP Top 10)
- [ ] Testing de autenticación/autorización
- [ ] Testing de encriptación
- [ ] Auditoría de logs

**Entregables**: Reporte de seguridad, hallazgos, plan de remedial

#### Hito 3.3: Testing de Compatibilidad (Semana 27)
- [ ] Diferentes navegadores (Chrome, Firefox, Safari, Edge)
- [ ] Dispositivos (Desktop, Tablet, Mobile)
- [ ] Sistemas operativos (Windows, Mac, Linux)
- [ ] Bases de datos (PostgreSQL, MySQL)
- [ ] Versiones de software

**Entregables**: Matriz de compatibilidad, defectos encontrados

#### Hito 3.4: Testing de Carga (Semana 27-28)
- [ ] Load testing (1000+ usuarios simultáneos)
- [ ] Stress testing
- [ ] Endurance testing (24h+ de operación)
- [ ] Análisis de bottlenecks
- [ ] Optimización necesaria

**Entregables**: Reporte de rendimiento, optimizaciones aplicadas

#### Hito 3.5: Test de Recuperación de Desastres (Semana 28)
- [ ] Simulación de fallos
- [ ] Prueba de recuperación de backups
- [ ] Validación de RTO/RPO
- [ ] Documentación de procedimientos
- [ ] Actualización de runbooks

**Entregables**: Procedimientos validados, documentación actualizada

**TOTAL FASE 3**: 4 semanas  
**Equipo**: 2-3 QA, 1 Especialista Seguridad  
**Costo estimado**: 60K-100K€

---

### FASE 4: CAPACITACIÓN (Semanas 25-29 | 4-5 semanas)

#### Hito 4.1: Preparación de Materiales (Semanas 25-26)
- [ ] Manual de usuario (200+ páginas)
  - Gestión de pacientes
  - Programación de citas
  - Registro de tratamientos
  - Facturación
  - Reportes
  
- [ ] Videos de demostración (20+ videos)
- [ ] FAQs y troubleshooting
- [ ] Guía rápida (Quick reference)
- [ ] Glosario de términos

**Entregables**: Documentación completa, videos en YouTube

#### Hito 4.2: Sesiones de Capacitación (Semanas 27-29)
- [ ] Capacitación Administradores (16 horas)
  - Configuración del sistema
  - Gestión de usuarios
  - Copias de seguridad
  - Resolución de problemas
  
- [ ] Capacitación Dentistas (8 horas)
  - Flujo clínico
  - Documentación
  - Seguridad de datos
  - Reportes
  
- [ ] Capacitación Administrativo (4 horas)
  - Gestión de citas
  - Facturación
  - Reportes operativos
  
- [ ] Capacitación Dirección (2 horas)
  - Reportes ejecutivos
  - Análisis de negocio

**Formato**: Presencial + Online + En-diferido (grabado)

**Entregables**: Certificados de asistencia, registros de capacitación

#### Hito 4.3: Soporte Pre-lanzamiento (Semana 29)
- [ ] Disponibilidad de helpdesk (8am-8pm)
- [ ] Respuesta a preguntas
- [ ] Resolución de dudas de uso
- [ ] Feedback de usuarios
- [ ] Ajustes menores

**Entregables**: Log de issues, feedback consolidado

**TOTAL FASE 4**: 4-5 semanas (paralela a Fase 3)  
**Equipo**: 1 Trainer, 1 Technical Writer, Equipo Técnico  
**Costo estimado**: 30K-50K€

---

### FASE 5: GO-LIVE (Semana 30 | 1 semana)

#### Hito 5.1: Preparación Final (Días 1-3)
- [ ] Verificación de todos los sistemas
- [ ] Última ronda de backups
- [ ] Validación de datos migrados
- [ ] Ensayo del plan de rollback
- [ ] Briefing final de equipo
- [ ] Comunicación a usuarios

**Entregables**: Checklist de go-live, acta de pre-lanzamiento

#### Hito 5.2: Migración de Datos (Día 4)
- [ ] Export de datos del sistema antiguo
- [ ] Transformación a formato nuevo
- [ ] Importación a BD de producción
- [ ] Validación de integridad
- [ ] Comparativa de registros
- [ ] Backup post-migración

**Entregables**: Reporte de migración, validación completada

#### Hito 5.3: Cutover y Lanzamiento (Día 5)
- [ ] 6:00am - Cierre del sistema antiguo
- [ ] 6:00am-9:00am - Ventana de mantenimiento
- [ ] Validación final
- [ ] Inicio del nuevo sistema
- [ ] Pruebas de conectividad
- [ ] Comunicación a usuarios: "Sistema en vivo"
- [ ] Monitoreo intensivo

**Entregables**: Acta de lanzamiento, logs de sistema

#### Hito 5.4: Soporte Post-lanzamiento (Semana 30-31)
- [ ] Equipo técnico 24/7 durante 2 semanas
- [ ] Hotline para usuarios
- [ ] Monitoreo continuo
- [ ] Fixes de bugs críticos
- [ ] Validación de operaciones
- [ ] Reporte de issues

**Entregables**: Log de incidentes, análisis de lecciones aprendidas

**TOTAL FASE 5**: 1 semana + 1 semana soporte  
**Equipo**: Equipo completo (30+ personas)  
**Costo estimado**: 100K-150K€

---

## TIMELINE VISUAL

```
FASE 0: PRE-PROYECTO
├─ 0.1 Equipo (2 sem)
├─ 0.2 Proveedores (2 sem)
└─ 0.3 Aprobaciones (2 sem)
   TOTAL: Semanas 1-2

FASE 1: ANÁLISIS Y DISEÑO
├─ 1.1 Requisitos (2 sem)
├─ 1.2 Arquitectura (1.5 sem)
├─ 1.3 BD (1.5 sem)
├─ 1.4 UI/UX (1 sem)
└─ 1.5 Seguridad (1 sem)
   TOTAL: Semanas 3-8

FASE 2: DESARROLLO
├─ 2.1 Infraestructura (1 sem)
├─ 2.2 Backend (12 sem)
├─ 2.3 Frontend (10 sem)
└─ 2.4 Integración (4 sem)
   TOTAL: Semanas 9-24

FASE 3: TESTING
├─ 3.1 Funcional (1 sem)
├─ 3.2 Seguridad (1 sem)
├─ 3.3 Compatibilidad (1 sem)
├─ 3.4 Carga (1.5 sem)
└─ 3.5 DR (0.5 sem)
   TOTAL: Semanas 25-28

FASE 4: CAPACITACIÓN (PARALELA)
├─ 4.1 Materiales (2 sem)
├─ 4.2 Sesiones (3 sem)
└─ 4.3 Soporte (1 sem)
   TOTAL: Semanas 25-29

FASE 5: GO-LIVE
├─ 5.1 Preparación (3 días)
├─ 5.2 Migración (1 día)
├─ 5.3 Cutover (1 día)
└─ 5.4 Soporte (2 semanas)
   TOTAL: Semana 30-31

TIMELINE TOTAL: 30-31 SEMANAS (7-8 MESES)
```

---

## HITOS CRÍTICOS (Go/No-Go)

| Fase | Hito | Criterio | Responsable |
|------|------|----------|-------------|
| 1 | Req. aprobados | Stakeholders firman | PM |
| 1 | Arquitectura validada | Revisión arquitecto externo | Arquitecto |
| 2 | Infra lista | Todos los servicios en vivo | DevOps |
| 2 | APIs completadas | 95% funcionalidad completada | Lead Dev Backend |
| 2 | Frontend listo | 90% funcionalidad implementada | Lead Dev Frontend |
| 3 | Testing completo | Cero defectos críticos | QA Lead |
| 3 | Seguridad OK | Audit sin hallazgos críticos | InfoSec |
| 4 | Usuarios capacitados | 100% personal entrenado | Trainer |
| 5 | Datos migrados | 100% integridad validada | DBA |
| 5 | Go-live | Lanzamiento exitoso | Todos |

---

## RIESGOS Y PLAN DE CONTINGENCIA

### Alto Riesgo: Retrasos en desarrollo

```
Indicador de alerta: Sprint con 20%+ de tareas no completadas
Acción preventiva:
- Metodología ágil con sprints de 2 semanas
- Daily standup diario
- Code review riguroso
- Pair programming en tareas críticas

Plan B: Si se incumple Hito 2.2
- Reducir alcance (postponer "nice-to-have")
- Añadir 1-2 desarrolladores
- Extender timeline 2-3 semanas
- Aumentar presupuesto 50K€
```

### Alto Riesgo: Defectos críticos en testing

```
Indicador de alerta: >10 defectos críticos en semana 25
Acción preventiva:
- Testing continuo durante desarrollo
- Code coverage mínimo 70%
- Automated testing
- Staging idéntico a producción

Plan B: Si se encuentra defecto crítico en últimas 2 semanas
- Delay de go-live 1-2 semanas
- Fix urgente + testing exhaustivo
- Validación externa de correcciones
```

### Crítico: Incidente de seguridad

```
Acción inmediata:
- Pausa de desarrollo (si es necesario)
- Equipo especial de análisis
- Verificación de impacto
- Plan de remedial
- Retest de seguridad completo
- Delay de lanzamiento (2+ semanas)
```

---

## BUDGET DESGLOSADO

| Fase | Actividad | Horas | Personas | Costo |
|------|-----------|-------|----------|-------|
| 0 | Pre-proyecto | 200 | 5 | €30K |
| 1 | Análisis/Diseño | 800 | 7 | €90K |
| 2 | Desarrollo | 2000 | 8 | €280K |
| 3 | Testing | 600 | 3 | €70K |
| 4 | Capacitación | 300 | 3 | €30K |
| 5 | Go-live | 400 | 30 | €80K |
| - | Infraestructura | - | - | €60K |
| - | Herramientas/Licencias | - | - | €40K |
| - | Contingencia (15%) | - | - | €100K |
| **TOTAL** | | | | **€680K** |

---

## MATRIZ DE RESPONSABILIDADES (RACI)

```
             Exec  Sponsor  PM  Architect  DBA  Dev  QA  Security
Presupuesto   A      R      C     -       -    -   -     -
Requisitos    C      R      A     I       C    I   -     C
Arquitectura  C      C      I     R       C    I   -     I
Desarrollo    -      -      C     I       I    R   C     -
Testing       -      -      C     -       -    I   R     C
Seguridad     C      C      C     I       -    I   C     R
Go-live       A      R      R     I       R    R   R     R

R = Responsable | A = Accountable | C = Consultado | I = Informado
```

---

## PRÓXIMAS ACCIONES

```
ESTA SEMANA:
☐ Enviar resumen ejecutivo a dirección
☐ Solicitar aprobación de presupuesto
☐ Identificar sponsor ejecutivo
☐ Publicar ofertas de empleo (si necesario)

PRÓXIMAS 2 SEMANAS:
☐ Constitución de comité de supervisión
☐ Solicitud de propuestas (RFP) a proveedores
☐ Reserva de presupuesto fiscal
☐ Planning de FASE 1

PRÓXIMAS 4 SEMANAS:
☐ Selección de proveedor
☐ Firma de contrato
☐ Onboarding de equipo
☐ Inicio de FASE 1
```

---

**Versión**: 1.0  
**Fecha**: 27 de mayo de 2024  
**Próxima actualización**: Mensual durante proyecto  
**Mantenedor**: Project Manager  
**Aprobado por**: [Director Ejecutivo]

---

## REFERENCIAS Y DOCUMENTOS ASOCIADOS

- 📄 [RESUMEN_EJECUTIVO.md](./RESUMEN_EJECUTIVO.md)
- 📄 [01_Arquitectura/README.md](./01_Arquitectura/README.md)
- 📄 [02_BaseDatos/ESQUEMA_LOGICO.md](./02_BaseDatos/ESQUEMA_LOGICO.md)
- 📄 [02_BaseDatos/DDL_PostgreSQL.sql](./02_BaseDatos/DDL_PostgreSQL.sql)
- 📄 [04_API/ESPECIFICACION_REST_API.md](./04_API/ESPECIFICACION_REST_API.md)
- 📄 [05_Seguridad/POLITICA_SEGURIDAD_CUMPLIMIENTO.md](./05_Seguridad/POLITICA_SEGURIDAD_CUMPLIMIENTO.md)
- 📄 [06_Documentacion/GUIA_IMPLEMENTACION.md](./06_Documentacion/GUIA_IMPLEMENTACION.md)
- 📄 [06_Documentacion/PROCESOS_NEGOCIO.md](./06_Documentacion/PROCESOS_NEGOCIO.md)
- 📄 [06_Documentacion/MANUAL_USUARIO.md](./06_Documentacion/MANUAL_USUARIO.md)
- 📄 [06_Documentacion/MANUAL_MANTENIMIENTO.md](./06_Documentacion/MANUAL_MANTENIMIENTO.md)
- 📄 [06_Documentacion/TRABAJO_EQUIPO_VERSIONADO.md](./06_Documentacion/TRABAJO_EQUIPO_VERSIONADO.md)
- 📄 [06_Documentacion/DASHBOARD_TAREAS.md](./06_Documentacion/DASHBOARD_TAREAS.md)
- 📄 [06_Documentacion/ROADMAP_TECNICO_MEJORAS.md](./06_Documentacion/ROADMAP_TECNICO_MEJORAS.md)
- 📄 [06_Documentacion/INTEGRACIONES_IA_APIS.md](./06_Documentacion/INTEGRACIONES_IA_APIS.md)
- 📄 [06_Documentacion/ESTADO_IMPLEMENTACION_FUNCIONAL.md](./06_Documentacion/ESTADO_IMPLEMENTACION_FUNCIONAL.md)
- 📄 [07_Configuracion/ARCHIVOS_CONFIGURACION.md](./07_Configuracion/ARCHIVOS_CONFIGURACION.md)
- 📄 [07_Configuracion/DESPLIEGUE_PRODUCCION.md](./07_Configuracion/DESPLIEGUE_PRODUCCION.md)
- 📄 [07_Configuracion/CONFIGURACION_PRODUCCION.md](./07_Configuracion/CONFIGURACION_PRODUCCION.md)
