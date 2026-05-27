# Archivos de Configuración del Sistema
## Clínica Odontológica

---

## 1. VARIABLES DE ENTORNO (.env)

```env
# ============================================
# BASE DE DATOS
# ============================================
DATABASE_URL=jdbc:postgresql://localhost:5432/odonto_clinica
DATABASE_USER=clinica_app
DATABASE_PASSWORD=password_super_segura_32_caracteres_minimo
DATABASE_SSL_MODE=require
DATABASE_POOL_SIZE=20
DATABASE_MAX_LIFETIME=1800000

# ============================================
# REDIS (Cache/Session)
# ============================================
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_PASSWORD=redis_password_segura
REDIS_DATABASE=0
REDIS_SSL=true
REDIS_TIMEOUT=2000

# ============================================
# AUTENTICACIÓN
# ============================================
JWT_SECRET=jwt_secret_muy_largo_y_aleatorio_64_caracteres_minimo_SECRETO
JWT_EXPIRATION_MINUTES=60
JWT_REFRESH_EXPIRATION_DAYS=30
OAUTH2_GOOGLE_CLIENT_ID=xxxxx.apps.googleusercontent.com
OAUTH2_GOOGLE_CLIENT_SECRET=xxxxx
TWO_FACTOR_AUTH_ENABLED=true
TWO_FACTOR_ISSUER=OdontoClinica

# ============================================
# ENCRIPTACIÓN
# ============================================
ENCRYPTION_KEY=encryption_key_32_bytes_base64_encoded_muy_segura_SECRETO
ENCRYPTION_ALGORITHM=AES
ENCRYPTION_KEY_SIZE=256

# ============================================
# APLICACIÓN
# ============================================
APP_NAME=OdontoClinica
APP_VERSION=1.0.0
APP_ENVIRONMENT=production
APP_DEBUG=false
APP_LOG_LEVEL=INFO
APP_TIMEZONE=Europe/Madrid
APP_LOCALE=es_ES

# ============================================
# SEGURIDAD
# ============================================
CORS_ALLOWED_ORIGINS=https://clinica-odonto.eu,https://app.clinica-odonto.eu
CORS_ALLOWED_METHODS=GET,POST,PUT,PATCH,DELETE,OPTIONS
CORS_ALLOWED_CREDENTIALS=true
SESSION_TIMEOUT_MINUTES=60
SESSION_ABSOLUTE_TIMEOUT_HOURS=12
MAX_LOGIN_ATTEMPTS=5
LOGIN_LOCKOUT_DURATION_MINUTES=30
PASSWORD_MIN_LENGTH=12
PASSWORD_REQUIRE_UPPERCASE=true
PASSWORD_REQUIRE_LOWERCASE=true
PASSWORD_REQUIRE_NUMBERS=true
PASSWORD_REQUIRE_SYMBOLS=true
PASSWORD_EXPIRATION_DAYS=90

# ============================================
# ARCHIVOS
# ============================================
UPLOAD_DIR=/var/odonto/documentos
UPLOAD_MAX_SIZE_MB=100
UPLOAD_ALLOWED_TYPES=pdf,jpg,jpeg,png,tiff,dcm
UPLOAD_SCAN_VIRUS=true
UPLOAD_QUARANTINE_DIR=/var/odonto/quarantine

# ============================================
# EMAIL/NOTIFICACIONES
# ============================================
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=noreply@clinica-odonto.eu
MAIL_PASSWORD=app_password_gmail_SECRETO
MAIL_FROM=OdontoClinica <noreply@clinica-odonto.eu>
MAIL_FROM_NAME=OdontoClinica
MAIL_ENCRYPTION=tls
MAIL_TIMEOUT=10
NOTIFICATION_SMS_ENABLED=true
NOTIFICATION_SMS_PROVIDER=twilio
TWILIO_ACCOUNT_SID=AC_XXXXX_SECRETO
TWILIO_AUTH_TOKEN=XXXXX_SECRETO
TWILIO_PHONE_NUMBER=+34900000000

# ============================================
# AUDITORÍA Y LOGGING
# ============================================
AUDIT_LOG_ENABLED=true
AUDIT_LOG_RETENTION_DAYS=2555
AUDIT_LOG_ANONYMIZE_SENSITIVE=true
LOG_FORMAT=json
LOG_OUTPUT=file,console,syslog
LOG_FILE_PATH=/var/odonto/logs/app.log
LOG_FILE_MAX_SIZE_MB=100
LOG_FILE_MAX_BACKUPS=30
SENTRY_DSN=https://xxxxx@sentry.io/xxxxx
SENTRY_ENVIRONMENT=production
SENTRY_TRACES_SAMPLE_RATE=0.1

# ============================================
# ALMACENAMIENTO EN NUBE
# ============================================
AWS_REGION=eu-west-1
AWS_ACCESS_KEY_ID=AKIAIOSFODNN7EXAMPLE_SECRETO
AWS_SECRET_ACCESS_KEY=wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY_SECRETO
AWS_S3_BUCKET=odonto-clinica-backups
AWS_S3_BACKUP_PREFIX=backups/
AWS_BACKUP_RETENTION_DAYS=365
AWS_RESTORE_TIMEOUT_SECONDS=3600

# ============================================
# TRABAJOS EN BACKGROUND
# ============================================
JOBS_ENABLED=true
JOBS_BACKUP_CRON=0 0 * * *
JOBS_AUDIT_CLEANUP_CRON=0 3 * * 0
JOBS_PRESCRIPTION_EXPIRATION_CHECK_CRON=0 */4 * * *
JOBS_INVOICE_REMINDER_CRON=0 9 * * 1
JOBS_STOCK_ALERT_CRON=0 */2 * * *
JOBS_THREAD_POOL_SIZE=5

# ============================================
# INTEGRACIONES
# ============================================
INTEGRATION_LABORATORY_API_URL=https://api.laboratory.example.com
INTEGRATION_LABORATORY_API_KEY=api_key_SECRETO
INTEGRATION_PHARMA_API_URL=https://api.pharmacy.example.com
INTEGRATION_PHARMA_API_KEY=api_key_SECRETO
INTEGRATION_BANKING_API_URL=https://api.banking.example.com
INTEGRATION_BANKING_API_KEY=api_key_SECRETO
INTEGRATION_TIMEOUT_SECONDS=30

# ============================================
# REPORTES
# ============================================
REPORTS_TEMP_DIR=/var/odonto/reports-temp
REPORTS_OUTPUT_DIR=/var/odonto/reports
REPORTS_MAX_SIZE_MB=50
REPORTS_RETENTION_DAYS=90
REPORTS_EXPORT_PDF_ENABLED=true
REPORTS_EXPORT_EXCEL_ENABLED=true
REPORTS_EXPORT_CSV_ENABLED=true

# ============================================
# COMPLIANCE Y PRIVACIDAD
# ============================================
GDPR_DATA_RETENTION_YEARS=10
GDPR_DATA_RETENTION_YEARS_MINORS=5
GDPR_RIGHT_TO_FORGOTTEN_ENABLED=true
GDPR_DATA_PORTABILITY_ENABLED=true
CONSENT_VERSION=1.0
CONSENT_EXPIRATION_MONTHS=24
PRIVACY_POLICY_URL=https://clinica-odonto.eu/privacy
TERMS_AND_CONDITIONS_URL=https://clinica-odonto.eu/terms

# ============================================
# MONITOREO Y PERFORMANCE
# ============================================
METRICS_ENABLED=true
METRICS_PORT=9090
HEALTH_CHECK_ENABLED=true
HEALTH_CHECK_INTERVAL_SECONDS=30
PERFORMANCE_MONITORING_ENABLED=true
PERFORMANCE_LOG_SLOW_QUERIES=true
PERFORMANCE_SLOW_QUERY_THRESHOLD_MS=1000
PERFORMANCE_LOG_SLOW_HTTP_REQUESTS=true
PERFORMANCE_SLOW_HTTP_REQUEST_THRESHOLD_MS=5000

# ============================================
# RECURSOS CLÍNICOS
# ============================================
CLINIC_NAME=OdontoClinica España
CLINIC_ID=CLINIC_001
CLINIC_REGISTRATION_NUMBER=12345678
CLINIC_PHONE=+34-91-123-4567
CLINIC_EMAIL=info@clinica-odonto.eu
CLINIC_ADDRESS=Calle Principal 123, 28001 Madrid, España
CLINIC_TIMEZONE=Europe/Madrid
CLINIC_WORKING_HOURS_START=09:00
CLINIC_WORKING_HOURS_END=20:00
CLINIC_WORKING_DAYS=1,2,3,4,5
CLINIC_HOLIDAYS=2024-01-01,2024-12-25
APPOINTMENT_DURATION_DEFAULT_MINUTES=30
APPOINTMENT_BUFFER_BETWEEN_APPOINTMENTS_MINUTES=10
APPOINTMENT_REMINDER_ADVANCE_HOURS=24
APPOINTMENT_CANCELATION_ALLOWED_UNTIL_HOURS=2
CONSULTATION_PRICE_DEFAULT=50.00
CURRENCY=EUR
```

---

## 2. ARCHIVO docker-compose.yml

```yaml
version: '3.8'

services:
  # Base de Datos PostgreSQL
  postgres:
    image: postgres:15-alpine
    container_name: odonto-postgres
    environment:
      POSTGRES_DB: odonto_clinica
      POSTGRES_USER: clinica_app
      POSTGRES_PASSWORD: ${DATABASE_PASSWORD}
      POSTGRES_INITDB_ARGS: "-c ssl=on -c ssl_cert_file=/etc/postgresql/server.crt -c ssl_key_file=/etc/postgresql/server.key"
    volumes:
      - postgres_data:/var/lib/postgresql/data
      - ./docker/postgres-init.sql:/docker-entrypoint-initdb.d/init.sql
    ports:
      - "5432:5432"
    networks:
      - odonto-network
    restart: unless-stopped
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U clinica_app"]
      interval: 10s
      timeout: 5s
      retries: 5

  # Redis (Cache/Session)
  redis:
    image: redis:7-alpine
    container_name: odonto-redis
    command: redis-server --requirepass ${REDIS_PASSWORD} --appendonly yes
    volumes:
      - redis_data:/data
    ports:
      - "6379:6379"
    networks:
      - odonto-network
    restart: unless-stopped
    healthcheck:
      test: ["CMD", "redis-cli", "--raw", "incr", "ping"]
      interval: 10s
      timeout: 5s
      retries: 5

  # Backend Application (Java/SpringBoot)
  backend:
    build:
      context: ./backend
      dockerfile: Dockerfile
    container_name: odonto-backend
    environment:
      DATABASE_URL: jdbc:postgresql://postgres:5432/odonto_clinica
      DATABASE_USER: ${DATABASE_USER}
      DATABASE_PASSWORD: ${DATABASE_PASSWORD}
      REDIS_HOST: redis
      REDIS_PORT: 6379
      REDIS_PASSWORD: ${REDIS_PASSWORD}
      JWT_SECRET: ${JWT_SECRET}
      ENCRYPTION_KEY: ${ENCRYPTION_KEY}
      APP_ENVIRONMENT: production
    volumes:
      - ./logs:/app/logs
      - /var/odonto/documentos:/var/odonto/documentos
    ports:
      - "8080:8080"
    depends_on:
      postgres:
        condition: service_healthy
      redis:
        condition: service_healthy
    networks:
      - odonto-network
    restart: unless-stopped
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 3

  # Frontend Application (React/Node)
  frontend:
    build:
      context: ./frontend
      dockerfile: Dockerfile
    container_name: odonto-frontend
    environment:
      REACT_APP_API_URL: http://localhost:8080/api
      REACT_APP_ENVIRONMENT: production
    volumes:
      - ./frontend/build:/usr/share/nginx/html
    ports:
      - "3000:3000"
    depends_on:
      - backend
    networks:
      - odonto-network
    restart: unless-stopped

  # Nginx (Reverse Proxy)
  nginx:
    image: nginx:alpine
    container_name: odonto-nginx
    volumes:
      - ./docker/nginx.conf:/etc/nginx/nginx.conf:ro
      - ./docker/ssl:/etc/nginx/ssl:ro
    ports:
      - "80:80"
      - "443:443"
    depends_on:
      - backend
      - frontend
    networks:
      - odonto-network
    restart: unless-stopped

  # Prometheus (Monitoring)
  prometheus:
    image: prom/prometheus:latest
    container_name: odonto-prometheus
    volumes:
      - ./docker/prometheus.yml:/etc/prometheus/prometheus.yml:ro
      - prometheus_data:/prometheus
    ports:
      - "9090:9090"
    networks:
      - odonto-network
    restart: unless-stopped

  # Grafana (Dashboards)
  grafana:
    image: grafana/grafana:latest
    container_name: odonto-grafana
    environment:
      GF_SECURITY_ADMIN_PASSWORD: ${GRAFANA_ADMIN_PASSWORD:-admin}
      GF_INSTALL_PLUGINS: 'grafana-piechart-panel'
    volumes:
      - grafana_data:/var/lib/grafana
      - ./docker/grafana/provisioning:/etc/grafana/provisioning:ro
    ports:
      - "3001:3000"
    depends_on:
      - prometheus
    networks:
      - odonto-network
    restart: unless-stopped

volumes:
  postgres_data:
    driver: local
  redis_data:
    driver: local
  prometheus_data:
    driver: local
  grafana_data:
    driver: local

networks:
  odonto-network:
    driver: bridge
```

---

## 3. Kubernetes Deployment (helm/values.yaml)

```yaml
# Configuración para Kubernetes
replicaCount: 3

image:
  repository: clinica/odonto-backend
  tag: "1.0.0"
  pullPolicy: IfNotPresent

imagePullSecrets: []
nameOverride: ""
fullnameOverride: ""

podAnnotations:
  prometheus.io/scrape: "true"
  prometheus.io/port: "8080"
  prometheus.io/path: "/actuator/prometheus"

podSecurityContext:
  runAsNonRoot: true
  runAsUser: 1000
  fsGroup: 1000
  seccompProfile:
    type: RuntimeDefault

securityContext:
  allowPrivilegeEscalation: false
  capabilities:
    drop:
      - ALL
  readOnlyRootFilesystem: true

service:
  type: ClusterIP
  port: 80
  targetPort: 8080
  annotations: {}

ingress:
  enabled: true
  className: "nginx"
  annotations:
    cert-manager.io/cluster-issuer: "letsencrypt-prod"
    nginx.ingress.kubernetes.io/ssl-redirect: "true"
  hosts:
    - host: api.clinica-odonto.eu
      paths:
        - path: /
          pathType: Prefix
  tls:
    - secretName: odonto-api-tls
      hosts:
        - api.clinica-odonto.eu

resources:
  limits:
    cpu: 2000m
    memory: 2Gi
  requests:
    cpu: 500m
    memory: 512Mi

autoscaling:
  enabled: true
  minReplicas: 3
  maxReplicas: 10
  targetCPUUtilizationPercentage: 70
  targetMemoryUtilizationPercentage: 80

nodeSelector: {}

tolerations: []

affinity:
  podAntiAffinity:
    preferredDuringSchedulingIgnoredDuringExecution:
      - weight: 100
        podAffinityTerm:
          labelSelector:
            matchExpressions:
              - key: app
                operator: In
                values:
                  - odonto-backend
          topologyKey: kubernetes.io/hostname

livenessProbe:
  httpGet:
    path: /actuator/health/liveness
    port: 8080
  initialDelaySeconds: 30
  periodSeconds: 10

readinessProbe:
  httpGet:
    path: /actuator/health/readiness
    port: 8080
  initialDelaySeconds: 15
  periodSeconds: 5

# PostgreSQL Sub-chart
postgresql:
  enabled: true
  auth:
    username: clinica_app
    password: ${DATABASE_PASSWORD}
    database: odonto_clinica
  primary:
    persistence:
      size: 100Gi

# Redis Sub-chart
redis:
  enabled: true
  auth:
    enabled: true
    password: ${REDIS_PASSWORD}
```

---

## 4. archivo .gitignore

```
# Dependency directories
node_modules/
vendor/
/lib/
/bin/

# Environment variables
.env
.env.local
.env.*.local

# Build output
/build/
/dist/
*.o
*.a
*.so
target/
out/

# IDE
.vscode/
.idea/
*.swp
*.swo
*~
.DS_Store

# Logs
logs/
*.log
npm-debug.log*

# OS
.DS_Store
Thumbs.db

# Sensitive data
*.pem
*.key
*.crt
*.pfx
*.p12

# Temporary files
tmp/
temp/
*.tmp

# Docker
.dockerignore
docker/secrets/

# Coverage
coverage/
.coverage

# Security
.secrets
secrets/
```

---

**Versión**: 1.0  
**Fecha**: 2024-05-27  
**Próxima revisión**: 2024-11-27
