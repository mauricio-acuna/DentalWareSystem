# OdontoSystems Backend

Backend Spring Boot para el sistema OdontoSystems en desarrollo.

## Requisitos

- Java 17+
- Maven 3.9+

## Ejecutar

```powershell
mvn spring-boot:run
```

La API queda disponible en `http://localhost:8080/api/v1`.

Puntos utiles:

- Salud: `GET /api/v1/actuator/health`
- Swagger UI: `GET /api/v1/swagger-ui.html`
- Pacientes: `/api/v1/pacientes`
- Citas: `/api/v1/citas`
- Disponibilidad: `/api/v1/disponibilidad`
- Tratamientos: `/api/v1/tratamientos`
- Diagnosticos: `/api/v1/pacientes/{idPaciente}/diagnosticos`
- Inventario: `/api/v1/inventario`
- Facturas: `/api/v1/facturas`
- Reportes: `/api/v1/reportes`
- Auditoria: `/api/v1/auditoria/eventos`
- Usuarios: `POST /api/v1/usuarios`
- Login: `POST /api/v1/auth/login`

## Seguridad

Por defecto la autenticacion queda desactivada para desarrollo local:

```yaml
app.security.enabled: false
```

Para exigir JWT en los endpoints protegidos:

```powershell
$env:APP_SECURITY_ENABLED="true"
$env:JWT_SECRET="cambia-este-secreto-por-uno-de-32-bytes-minimo"
mvn spring-boot:run
```

`POST /api/v1/auth/login` devuelve un token `Bearer` para usuarios creados en `/api/v1/usuarios`.

## Calidad automatizada

```powershell
mvn test
mvn verify
```

`mvn test` ejecuta pruebas de integracion con H2. `mvn verify` genera informe JaCoCo y aplica un umbral inicial de cobertura de lineas del 35%.

## Base de datos

Por defecto usa H2 en memoria para desarrollo local. Para PostgreSQL:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/odonto"
$env:DB_USERNAME="odonto"
$env:DB_PASSWORD="odonto"
$env:DB_DRIVER="org.postgresql.Driver"
$env:JPA_DDL_AUTO="validate"
mvn spring-boot:run
```
