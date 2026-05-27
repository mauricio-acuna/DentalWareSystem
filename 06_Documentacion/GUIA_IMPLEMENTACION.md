# Guía de Implementación del Sistema
## Clínica Odontológica v1.0

---

## FASE 1: INSTALACIÓN INICIAL

### 1.1 Requisitos Previos
- [ ] Servidor dedicado con mínimo 4 vCPU, 16GB RAM
- [ ] Base de datos PostgreSQL 13+ instalada
- [ ] Sistema operativo Linux Ubuntu 22.04 LTS
- [ ] Certificado SSL/TLS válido (dominio)
- [ ] Acceso a internet con IP pública
- [ ] Equipo de IT responsable de mantenimiento

### 1.2 Instalación de Dependencias

```bash
# Actualizar sistema
sudo apt update && sudo apt upgrade -y

# Instalar PostgreSQL
sudo apt install postgresql postgresql-contrib -y
sudo systemctl start postgresql
sudo systemctl enable postgresql

# Instalar Redis
sudo apt install redis-server -y
sudo systemctl start redis-server
sudo systemctl enable redis-server

# Instalar Nginx
sudo apt install nginx -y
sudo systemctl start nginx
sudo systemctl enable nginx

# Instalar Node.js (para frontend)
curl -fsSL https://deb.nodesource.com/setup_18.x | sudo -E bash -
sudo apt install nodejs -y

# Instalar Java (backend)
sudo apt install openjdk-17-jdk -y
```

### 1.3 Configuración de Base de Datos

```bash
# Conectar a PostgreSQL como root
sudo -u postgres psql

# Crear usuario de la aplicación
CREATE USER clinica_app WITH PASSWORD 'contraseña_segura_32_caracteres';

# Crear base de datos
CREATE DATABASE odonto_clinica OWNER clinica_app;

# Conectar a la base de datos
\c odonto_clinica

# Ejecutar script DDL (ver fichero DDL_PostgreSQL.sql)
\i DDL_PostgreSQL.sql

# Asignar permisos
GRANT USAGE ON SCHEMA clinica TO clinica_app;
GRANT USAGE ON SCHEMA public TO clinica_app;
GRANT CONNECT ON DATABASE odonto_clinica TO clinica_app;
```

### 1.4 Configuración del Sistema de Archivos

```bash
# Crear estructura de directorios
mkdir -p /var/odonto/{documentos,radiografias,backups,logs}
mkdir -p /var/odonto/documentos/{pacientes,facturas,radiografias}

# Asignar permisos
sudo chown -R www-data:www-data /var/odonto
sudo chmod 755 /var/odonto
sudo chmod 700 /var/odonto/backups
sudo chmod 600 /var/odonto/logs

# Configurar rotación de logs
sudo nano /etc/logrotate.d/odonto-clinica
```

---

## FASE 2: CONFIGURACIÓN DE SEGURIDAD

### 2.1 SSL/TLS

```bash
# Generar certificado con Let's Encrypt
sudo apt install certbot python3-certbot-nginx -y
sudo certbot certonly --standalone -d clinica-odonto.eu
sudo certbot renew --dry-run

# Configurar renovación automática
sudo systemctl enable certbot.timer
sudo systemctl start certbot.timer
```

### 2.2 Firewall

```bash
# Configurar UFW
sudo ufw default deny incoming
sudo ufw default allow outgoing

# Permitir servicios
sudo ufw allow 22/tcp    # SSH
sudo ufw allow 80/tcp    # HTTP (redirect)
sudo ufw allow 443/tcp   # HTTPS
sudo ufw allow 5432/tcp from 127.0.0.1  # PostgreSQL (local)
sudo ufw allow 6379/tcp from 127.0.0.1  # Redis (local)

sudo ufw enable
sudo ufw status
```

### 2.3 SSH Hardening

```bash
# Editar configuración SSH
sudo nano /etc/ssh/sshd_config

# Cambios recomendados:
Port 2222
PermitRootLogin no
PasswordAuthentication no
PubkeyAuthentication yes
X11Forwarding no
MaxAuthTries 3
ClientAliveInterval 300
ClientAliveCountMax 2

# Aplicar cambios
sudo systemctl restart ssh
```

### 2.4 Configuración Nginx

```nginx
# /etc/nginx/sites-available/odonto-clinica
server {
    listen 443 ssl http2;
    server_name clinica-odonto.eu;

    # Certificado SSL
    ssl_certificate /etc/letsencrypt/live/clinica-odonto.eu/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/clinica-odonto.eu/privkey.pem;

    # Configuración SSL
    ssl_protocols TLSv1.2 TLSv1.3;
    ssl_ciphers HIGH:!aNULL:!MD5;
    ssl_prefer_server_ciphers on;
    ssl_session_cache shared:SSL:10m;
    ssl_session_timeout 10m;

    # Headers de seguridad
    add_header Strict-Transport-Security "max-age=31536000; includeSubDomains" always;
    add_header X-Content-Type-Options "nosniff" always;
    add_header X-Frame-Options "SAMEORIGIN" always;
    add_header X-XSS-Protection "1; mode=block" always;
    add_header Referrer-Policy "strict-origin-when-cross-origin" always;
    add_header Content-Security-Policy "default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline'" always;

    # Proxy a aplicación backend
    location /api {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_read_timeout 90;
    }

    # Frontend
    location / {
        proxy_pass http://localhost:3000;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }

    # Denegar acceso a archivos sensibles
    location ~ /\. {
        deny all;
    }

    # Limite de tasa
    limit_req_zone $binary_remote_addr zone=api_limit:10m rate=10r/s;
    location /api {
        limit_req zone=api_limit burst=20 nodelay;
    }
}

# Redirigir HTTP a HTTPS
server {
    listen 80;
    server_name clinica-odonto.eu;
    return 301 https://$server_name$request_uri;
}
```

---

## FASE 3: DESPLIEGUE DE APLICACIÓN

### 3.1 Backend (Java/SpringBoot)

```bash
# Clonar repositorio
git clone https://github.com/clinica/odonto-backend.git /opt/odonto-backend
cd /opt/odonto-backend

# Configurar archivo .env
cat > .env << EOF
DATABASE_URL=jdbc:postgresql://localhost:5432/odonto_clinica
DATABASE_USER=clinica_app
DATABASE_PASSWORD=contraseña_segura_32_caracteres
REDIS_URL=redis://localhost:6379
JWT_SECRET=jwt_secret_muy_largo_y_aleatorio_64_caracteres
ENCRYPTION_KEY=encryption_key_muy_largo_y_aleatorio
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=noreply@clinica-odonto.eu
MAIL_PASSWORD=contraseña_aplicacion_gmail
AWS_ACCESS_KEY_ID=your_access_key
AWS_SECRET_ACCESS_KEY=your_secret_key
AWS_REGION=eu-west-1
EOF

# Compilar
mvn clean package -DskipTests

# Crear servicio systemd
sudo nano /etc/systemd/system/odonto-backend.service
```

```ini
[Unit]
Description=Odonto Clinica Backend Service
After=network.target postgresql.service redis-server.service

[Service]
Type=simple
User=odonto
WorkingDirectory=/opt/odonto-backend
ExecStart=/usr/bin/java -Xmx2g -jar target/odonto-backend-1.0.jar
Restart=on-failure
RestartSec=10
StandardOutput=journal
StandardError=journal

[Install]
WantedBy=multi-user.target
```

```bash
# Activar servicio
sudo systemctl daemon-reload
sudo systemctl enable odonto-backend
sudo systemctl start odonto-backend
sudo systemctl status odonto-backend
```

### 3.2 Frontend (React/Node)

```bash
# Clonar repositorio
git clone https://github.com/clinica/odonto-frontend.git /opt/odonto-frontend
cd /opt/odonto-frontend

# Instalar dependencias
npm install

# Build para producción
npm run build

# Crear servicio PM2
sudo npm install -g pm2
pm2 start npm --name "odonto-frontend" -- start
pm2 save
pm2 startup

# Configurar servicio
pm2 start ecosystem.config.js
pm2 save
```

### 3.3 Variables de Entorno

```bash
# Backend (.env)
DATABASE_URL=jdbc:postgresql://db.example.com:5432/odonto_clinica
DATABASE_USER=clinica_app
DATABASE_PASSWORD=[SECURED]
REDIS_URL=redis://redis.example.com:6379
JWT_SECRET=[SECURED - 64+ chars]
ENCRYPTION_KEY=[SECURED - 32+ chars]
LOG_LEVEL=INFO
ENABLE_AUDIT=true
AUDIT_LOG_RETENTION_DAYS=2555
MAX_FILE_UPLOAD_MB=100
SESSION_TIMEOUT_MINUTES=60
CORS_ALLOWED_ORIGINS=https://clinica-odonto.eu,https://app.clinica-odonto.eu
MAIL_FROM=noreply@clinica-odonto.eu
ENABLE_2FA=true

# Frontend (.env)
REACT_APP_API_URL=https://api.clinica-odonto.eu/v1
REACT_APP_ENVIRONMENT=production
REACT_APP_LOG_LEVEL=warn
REACT_APP_SESSION_TIMEOUT=3600
```

---

## FASE 4: BACKUPS Y RECUPERACIÓN

### 4.1 Script de Backup

```bash
#!/bin/bash
# /opt/odonto/scripts/backup.sh

BACKUP_DIR="/var/odonto/backups"
DB_NAME="odonto_clinica"
DB_USER="clinica_app"
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
BACKUP_FILE="$BACKUP_DIR/odonto_backup_$TIMESTAMP.sql.gz"

# Backup BD
pg_dump -U $DB_USER -h localhost $DB_NAME | gzip > $BACKUP_FILE

# Backup de documentos
tar -czf $BACKUP_DIR/documentos_$TIMESTAMP.tar.gz /var/odonto/documentos/

# Subir a AWS S3
aws s3 cp $BACKUP_FILE s3://odonto-backups/
aws s3 cp $BACKUP_DIR/documentos_$TIMESTAMP.tar.gz s3://odonto-backups/

# Eliminar backups locales > 30 días
find $BACKUP_DIR -name "odonto_backup_*.sql.gz" -mtime +30 -delete

echo "Backup completado: $BACKUP_FILE"
```

### 4.2 Cronograma de Backups

```bash
# Crontab
# Backup cada 6 horas
0 0,6,12,18 * * * /opt/odonto/scripts/backup.sh

# Backup completo semanal (domingo 2am)
0 2 * * 0 /opt/odonto/scripts/backup-completo.sh

# Verificación de integridad semanal
0 3 * * 0 /opt/odonto/scripts/verificar-backups.sh
```

### 4.3 Recuperación de Datos

```bash
# Restaurar base de datos
gunzip < odonto_backup_20240527_000000.sql.gz | psql -U clinica_app -h localhost odonto_clinica

# Restaurar documentos
tar -xzf documentos_20240527_000000.tar.gz -C /

# Verificar restauración
psql -U clinica_app -h localhost odonto_clinica -c "SELECT COUNT(*) FROM clinica.pacientes;"
```

---

## FASE 5: MONITOREO Y ALERTAS

### 5.1 Prometheus Configuration

```yaml
# /etc/prometheus/prometheus.yml
global:
  scrape_interval: 15s
  evaluation_interval: 15s

scrape_configs:
  - job_name: 'odonto-backend'
    static_configs:
      - targets: ['localhost:8080']
    metrics_path: '/actuator/prometheus'

  - job_name: 'postgresql'
    static_configs:
      - targets: ['localhost:9187']

  - job_name: 'redis'
    static_configs:
      - targets: ['localhost:9121']
```

### 5.2 Alertas (Alertmanager)

```yaml
groups:
  - name: odonto_alerts
    rules:
      - alert: DatabaseDown
        expr: pg_up == 0
        for: 2m
        annotations:
          summary: "Database server is down"

      - alert: HighCPUUsage
        expr: node_cpu_usage > 80
        for: 5m
        annotations:
          summary: "CPU usage above 80%"

      - alert: LowDiskSpace
        expr: node_filesystem_avail_percent < 10
        for: 10m
        annotations:
          summary: "Disk space below 10%"

      - alert: BackendErrorRate
        expr: rate(http_requests_total{status=~"5.."}[5m]) > 0.05
        for: 5m
        annotations:
          summary: "Backend error rate above 5%"
```

---

## FASE 6: TESTING

### 6.1 Checklist de Testing

```
[ ] Test de conectividad BD
[ ] Test de autenticación (login/logout)
[ ] Test de CRUD pacientes
[ ] Test de gestión de citas
[ ] Test de tratamientos
[ ] Test de facturación
[ ] Test de exportación de datos
[ ] Test de compras e inventario
[ ] Test de auditoría
[ ] Test de rendimiento (load test)
[ ] Test de seguridad (OWASP Top 10)
[ ] Test de recuperación de desastres
```

### 6.2 Prueba de Carga

```bash
# Usando Apache JMeter
jmeter -n -t test-plan.jmx -l results.jtl -j jmeter.log

# Usando k6
k6 run load-test.js --vus 100 --duration 30s
```

---

## FASE 7: CAPACITACIÓN

### 7.1 Sesiones de Entrenamiento
- [ ] Administradores: 16 horas
- [ ] Dentistas: 8 horas
- [ ] Personal administrativo: 4 horas
- [ ] Soporte técnico: 20 horas

### 7.2 Documentación de Usuario
- [ ] Manual de usuario (PDF)
- [ ] Videos de demostración
- [ ] FAQs
- [ ] Línea de soporte técnico

---

## FASE 8: GO-LIVE

### 8.1 Plan de Migración

```
Día 1: Importación de datos históricos (modo lectura)
Día 2: Testing en paralelo (nuevo + antiguo sistema)
Día 3: Cutover (cambio a nuevo sistema)
Día 4-7: Soporte intensivo
```

### 8.2 Checklist Final

```
[ ] Todas las pruebas completadas
[ ] Certificados SSL válidos
[ ] Backups funcionando
[ ] Monitoreo activo
[ ] Personal capacitado
[ ] Documentación actualizada
[ ] Incidentes de migración documentados
[ ] Aprobación de stakeholders
```

---

## MANTENIMIENTO CONTINUADO

### Tareas Mensuales
- [ ] Revisión de logs de auditoría
- [ ] Verificación de integridad de backups
- [ ] Actualización de certificados (si aplica)
- [ ] Revisión de desempeño

### Tareas Trimestrales
- [ ] Auditoría de seguridad
- [ ] Revisión de usuarios y permisos
- [ ] Validación de cumplimiento RGPD
- [ ] Test de disaster recovery

### Tareas Anuales
- [ ] Auditoría externa ISO 27001
- [ ] Auditoría ISO 9001
- [ ] Penetration testing
- [ ] Revisión de políticas de seguridad

---

**Versión**: 1.0  
**Fecha**: 2024-05-27  
**Mantenedor**: Equipo de IT  
**Próxima actualización**: 2024-11-27
