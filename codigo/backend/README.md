# OdontoSystems Backend

Backend Spring Boot para el MVP operativo de OdontoSystems.

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
- Inventario: `/api/v1/inventario`
- Facturas: `/api/v1/facturas`

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
