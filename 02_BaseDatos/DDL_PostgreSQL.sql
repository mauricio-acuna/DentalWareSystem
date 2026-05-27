-- ===================================
-- SCRIPT DDL - BASE DE DATOS CLÍNICA ODONTOLÓGICA
-- Compatible con PostgreSQL 13+
-- Estándar: RGPD, ISO 27001
-- ===================================

-- CREAR ESQUEMA
CREATE SCHEMA IF NOT EXISTS clinica AUTHORIZATION clinica_user;

-- EXTENSIONES NECESARIAS
CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS uuid-ossp;

-- ===================================
-- TABLA: PACIENTES
-- ===================================
CREATE TABLE clinica.pacientes (
    id_paciente UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    numero_identificacion VARCHAR(50) NOT NULL UNIQUE,
    nombre_completo VARCHAR(150) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    genero CHAR(1) CHECK (genero IN ('M', 'F', 'O')),
    estado_civil VARCHAR(20),
    direccion VARCHAR(300),
    codigo_postal VARCHAR(10),
    ciudad VARCHAR(100),
    pais VARCHAR(100) DEFAULT 'España',
    telefono_movil VARCHAR(20),
    telefono_fijo VARCHAR(20),
    email VARCHAR(255) NOT NULL UNIQUE,
    fecha_alta TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    fecha_baja TIMESTAMP WITH TIME ZONE,
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO' CHECK (estado IN ('ACTIVO', 'INACTIVO', 'SUSPENDIDO')),
    consentimiento_rgpd BOOLEAN NOT NULL DEFAULT FALSE,
    historial_alergias TEXT,
    patologias_base TEXT,
    medicamentos_actuales TEXT,
    nota_clínica_general TEXT,
    fecha_modificacion TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    usuario_modificacion VARCHAR(100),
    
    CONSTRAINT ck_email_format CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$'),
    CONSTRAINT ck_edad_paciente CHECK (EXTRACT(YEAR FROM AGE(fecha_nacimiento)) >= 0)
);

CREATE INDEX idx_pacientes_numero_id ON clinica.pacientes(numero_identificacion);
CREATE INDEX idx_pacientes_email ON clinica.pacientes(email);
CREATE INDEX idx_pacientes_estado ON clinica.pacientes(estado);

-- ===================================
-- TABLA: EMPLEADOS
-- ===================================
CREATE TABLE clinica.empleados (
    id_empleado UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    numero_colegiado VARCHAR(50) NOT NULL UNIQUE,
    nombre_completo VARCHAR(150) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    genero CHAR(1) CHECK (genero IN ('M', 'F', 'O')),
    numero_identificacion VARCHAR(50) NOT NULL UNIQUE,
    especialidad VARCHAR(50) NOT NULL CHECK (especialidad IN ('DENTISTA', 'HIGIENISTA', 'AUXILIAR', 'ADMINISTRATIVO', 'GESTOR')),
    nivel_cualificacion VARCHAR(100),
    numero_colegio_profesional VARCHAR(100),
    pais_registro VARCHAR(100),
    fecha_registro_profesional DATE,
    licencias_activas TEXT,
    formacion_continua_actualizada BOOLEAN DEFAULT FALSE,
    direccion VARCHAR(300),
    email_corporativo VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    fecha_contratacion DATE NOT NULL,
    tipo_contrato VARCHAR(20) CHECK (tipo_contrato IN ('PERMANENTE', 'TEMPORAL', 'AUTONOMO')),
    estado_laboral VARCHAR(20) NOT NULL DEFAULT 'ACTIVO' CHECK (estado_laboral IN ('ACTIVO', 'BAJA', 'LICENCIA')),
    departamento VARCHAR(100),
    turno VARCHAR(50),
    horario VARCHAR(100),
    usuario_sistema_login VARCHAR(100) UNIQUE,
    rol_sistema VARCHAR(50),
    permiso_firmar_documentos BOOLEAN DEFAULT FALSE,
    firma_digital BYTEA,
    fecha_fin_contrato DATE,
    fecha_creacion TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    fecha_modificacion TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    
    CONSTRAINT ck_email_corp_format CHECK (email_corporativo ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$')
);

CREATE INDEX idx_empleados_numero_colegiado ON clinica.empleados(numero_colegiado);
CREATE INDEX idx_empleados_especialidad ON clinica.empleados(especialidad);
CREATE INDEX idx_empleados_estado_laboral ON clinica.empleados(estado_laboral);

-- ===================================
-- TABLA: CITAS
-- ===================================
CREATE TABLE clinica.citas (
    id_cita UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_paciente UUID NOT NULL REFERENCES clinica.pacientes(id_paciente) ON DELETE RESTRICT,
    id_empleado_dentista UUID REFERENCES clinica.empleados(id_empleado) ON DELETE RESTRICT,
    id_empleado_higienista UUID REFERENCES clinica.empleados(id_empleado) ON DELETE RESTRICT,
    fecha_cita DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    duracion_estimada_minutos INTEGER,
    tipo_cita VARCHAR(50) NOT NULL CHECK (tipo_cita IN ('CONSULTA', 'TRATAMIENTO', 'URGENCIA', 'SEGUIMIENTO')),
    estado VARCHAR(50) NOT NULL DEFAULT 'PROGRAMADA' CHECK (estado IN ('PROGRAMADA', 'CONFIRMADA', 'COMPLETADA', 'CANCELADA', 'NO_PRESENTADO')),
    motivo_consulta TEXT,
    notas_previa TEXT,
    resultado_cita TEXT,
    procedimientos_realizados TEXT,
    proximo_seguimiento DATE,
    fecha_confirmacion_paciente TIMESTAMP WITH TIME ZONE,
    metodo_recordatorio VARCHAR(50),
    sala_consulta VARCHAR(50),
    prioridad VARCHAR(20) CHECK (prioridad IN ('BAJA', 'MEDIA', 'ALTA', 'URGENCIA')),
    fecha_creacion TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    fecha_modificacion TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    usuario_creacion VARCHAR(100),
    
    CONSTRAINT ck_hora_coherencia CHECK (hora_inicio < hora_fin),
    CONSTRAINT ck_duracion_positiva CHECK (duracion_estimada_minutos > 0)
);

CREATE INDEX idx_citas_paciente ON clinica.citas(id_paciente);
CREATE INDEX idx_citas_fecha ON clinica.citas(fecha_cita);
CREATE INDEX idx_citas_dentista ON clinica.citas(id_empleado_dentista);
CREATE INDEX idx_citas_estado ON clinica.citas(estado);

-- ===================================
-- TABLA: DIAGNOSTICOS
-- ===================================
CREATE TABLE clinica.diagnosticos (
    id_diagnostico UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_cita UUID NOT NULL REFERENCES clinica.citas(id_cita) ON DELETE RESTRICT,
    id_paciente UUID NOT NULL REFERENCES clinica.pacientes(id_paciente) ON DELETE RESTRICT,
    codigo_cie10 VARCHAR(10) NOT NULL,
    descripcion_diagnostico TEXT NOT NULL,
    diente_afectado VARCHAR(10),
    fecha_diagnostico TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    id_empleado_diagnosticador UUID NOT NULL REFERENCES clinica.empleados(id_empleado),
    severidad VARCHAR(20) CHECK (severidad IN ('LEVE', 'MODERADA', 'SEVERA')),
    confirmado_laboratorio BOOLEAN DEFAULT FALSE,
    hallazgos_radiologicos TEXT,
    plan_tratamiento_recomendado TEXT,
    urgencia_tratamiento BOOLEAN DEFAULT FALSE,
    fecha_resolucion DATE,
    id_diagnostico_relacionado UUID REFERENCES clinica.diagnosticos(id_diagnostico),
    fecha_creacion TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    
    CONSTRAINT ck_codigo_cie10 CHECK (codigo_cie10 ~ '^[A-Z][0-9]{2}(\.[0-9]{1,2})?$')
);

CREATE INDEX idx_diagnosticos_paciente ON clinica.diagnosticos(id_paciente);
CREATE INDEX idx_diagnosticos_codigo_cie ON clinica.diagnosticos(codigo_cie10);

-- ===================================
-- TABLA: TRATAMIENTOS
-- ===================================
CREATE TABLE clinica.tratamientos (
    id_tratamiento UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_cita UUID REFERENCES clinica.citas(id_cita) ON DELETE RESTRICT,
    id_paciente UUID NOT NULL REFERENCES clinica.pacientes(id_paciente) ON DELETE RESTRICT,
    codigo_tratamiento VARCHAR(50) NOT NULL UNIQUE,
    nombre_tratamiento VARCHAR(200) NOT NULL,
    descripcion_detallada TEXT,
    clasificacion VARCHAR(50) NOT NULL CHECK (clasificacion IN ('PREVENTIVA', 'RESTAURADORA', 'ENDODONCIA', 'PERIODONTAL', 'QUIRURGICA', 'ORTODONCIA', 'ESTETICA')),
    diente_afectado VARCHAR(10),
    superficie_diente VARCHAR(50),
    diagnostico_asociado TEXT,
    plan_tratamiento TEXT,
    presupuesto_inicial NUMERIC(10,2),
    coste_real NUMERIC(10,2),
    cobertura_seguro NUMERIC(10,2),
    copago_paciente NUMERIC(10,2),
    materiales_utilizados TEXT,
    duracion_estimada_minutos INTEGER,
    duracion_real_minutos INTEGER,
    fecha_inicio DATE NOT NULL,
    fecha_completacion DATE,
    estado VARCHAR(50) NOT NULL DEFAULT 'PENDIENTE' CHECK (estado IN ('PENDIENTE', 'EN_CURSO', 'COMPLETADO', 'SUSPENDIDO', 'CANCELADO')),
    id_empleado_responsable UUID REFERENCES clinica.empleados(id_empleado),
    complicaciones TEXT,
    resultado_clinico TEXT,
    observaciones TEXT,
    fecha_proximo_control DATE,
    fecha_creacion TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    fecha_modificacion TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    
    CONSTRAINT ck_fechas_tratamiento CHECK (fecha_completacion IS NULL OR fecha_completacion >= fecha_inicio),
    CONSTRAINT ck_costes_positivos CHECK (presupuesto_inicial IS NULL OR presupuesto_inicial >= 0),
    CONSTRAINT ck_duracion_positiva CHECK (duracion_estimada_minutos > 0 OR duracion_estimada_minutos IS NULL)
);

CREATE INDEX idx_tratamientos_paciente ON clinica.tratamientos(id_paciente);
CREATE INDEX idx_tratamientos_fecha ON clinica.tratamientos(fecha_inicio);
CREATE INDEX idx_tratamientos_estado ON clinica.tratamientos(estado);

-- ===================================
-- TABLA: PROVEEDORES
-- ===================================
CREATE TABLE clinica.proveedores (
    id_proveedor UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    numero_identificacion VARCHAR(50) NOT NULL UNIQUE,
    nombre_razon_social VARCHAR(255) NOT NULL UNIQUE,
    tipo_proveedor VARCHAR(50) NOT NULL CHECK (tipo_proveedor IN ('MATERIALES', 'EQUIPOS', 'SERVICIOS', 'MIXTO')),
    nombre_contacto VARCHAR(150),
    telefono VARCHAR(20),
    email VARCHAR(255),
    direccion VARCHAR(300),
    codigo_postal VARCHAR(10),
    ciudad VARCHAR(100),
    pais VARCHAR(100),
    web VARCHAR(255),
    cuenta_bancaria VARCHAR(50),
    iban VARCHAR(34) NOT NULL UNIQUE,
    forma_pago VARCHAR(50),
    dias_pago_medio INTEGER,
    terminos_pago TEXT,
    calificacion_calidad NUMERIC(3,1) CHECK (calificacion_calidad BETWEEN 0 AND 10),
    certificaciones TEXT,
    fecha_certificacion_valida_hasta DATE,
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO' CHECK (estado IN ('ACTIVO', 'INACTIVO', 'SUSPENDIDO')),
    notas TEXT,
    fecha_alta TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    fecha_baja TIMESTAMP WITH TIME ZONE,
    fecha_creacion TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    fecha_modificacion TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    
    CONSTRAINT ck_email_proveedor CHECK (email IS NULL OR email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$'),
    CONSTRAINT ck_iban_format CHECK (iban ~ '^[A-Z]{2}[0-9]{2}[A-Z0-9]{1,30}$')
);

CREATE INDEX idx_proveedores_numero_id ON clinica.proveedores(numero_identificacion);
CREATE INDEX idx_proveedores_tipo ON clinica.proveedores(tipo_proveedor);

-- ===================================
-- TABLA: INSUMOS
-- ===================================
CREATE TABLE clinica.insumos (
    id_insumo UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    codigo_insumo VARCHAR(50) NOT NULL UNIQUE,
    nombre_producto VARCHAR(255) NOT NULL,
    descripcion TEXT,
    categoria VARCHAR(50) NOT NULL CHECK (categoria IN ('INSTRUMENTOS', 'MATERIALES_OBTURACION', 'MEDICAMENTOS', 'EQUIPOS_CONSUMIBLES', 'OTROS')),
    fabricante VARCHAR(200),
    codigo_lote VARCHAR(100),
    fecha_fabricacion DATE,
    fecha_caducidad DATE,
    numero_serie VARCHAR(100),
    precio_unitario_compra NUMERIC(10,2) NOT NULL,
    precio_unitario_venta NUMERIC(10,2),
    margen_beneficio_percent NUMERIC(5,2),
    unidad_medida VARCHAR(20) NOT NULL,
    cantidad_minima_stock INTEGER DEFAULT 0,
    cantidad_optima_stock INTEGER DEFAULT 0,
    cantidad_actual_stock INTEGER NOT NULL DEFAULT 0,
    localizacion_almacen VARCHAR(100),
    id_proveedor_habitual UUID REFERENCES clinica.proveedores(id_proveedor),
    proveedores_alternativos TEXT,
    normativa_almacenamiento TEXT,
    temperatura_optima NUMERIC(5,1),
    humedad_optima NUMERIC(5,1),
    necesita_refrigeracion BOOLEAN DEFAULT FALSE,
    condicion_conservacion TEXT,
    certificacion_ce BOOLEAN DEFAULT FALSE,
    biocompatibilidad_certificada BOOLEAN DEFAULT FALSE,
    esteril_compra BOOLEAN DEFAULT FALSE,
    requiere_esterilizacion BOOLEAN DEFAULT FALSE,
    instrucciones_uso TEXT,
    efectos_adversos_conocidos TEXT,
    contraindicaciones TEXT,
    estado VARCHAR(20) NOT NULL DEFAULT 'DISPONIBLE' CHECK (estado IN ('DISPONIBLE', 'DESCONTINUADO', 'RETIRADO')),
    fecha_creacion TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    fecha_modificacion TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    
    CONSTRAINT ck_precios_positivos CHECK (precio_unitario_compra > 0 AND precio_unitario_venta > 0)
);

CREATE INDEX idx_insumos_codigo ON clinica.insumos(codigo_insumo);
CREATE INDEX idx_insumos_categoria ON clinica.insumos(categoria);
CREATE INDEX idx_insumos_caducidad ON clinica.insumos(fecha_caducidad);

-- ===================================
-- TABLA: COMPRAS
-- ===================================
CREATE TABLE clinica.compras (
    id_compra UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    numero_pedido VARCHAR(50) NOT NULL UNIQUE,
    id_proveedor UUID NOT NULL REFERENCES clinica.proveedores(id_proveedor),
    fecha_pedido DATE NOT NULL,
    fecha_entrega_prevista DATE,
    fecha_entrega_real DATE,
    estado VARCHAR(50) NOT NULL DEFAULT 'SOLICITADA' CHECK (estado IN ('SOLICITADA', 'CONFIRMADA', 'PARCIAL', 'ENTREGADA', 'CANCELADA')),
    importe_total_sin_iva NUMERIC(12,2) NOT NULL,
    iva_aplicable_percent NUMERIC(5,2) DEFAULT 21,
    importe_iva NUMERIC(12,2),
    descuento_percent NUMERIC(5,2) DEFAULT 0,
    importe_descuento NUMERIC(12,2),
    gastos_envio NUMERIC(10,2) DEFAULT 0,
    otros_gastos NUMERIC(10,2) DEFAULT 0,
    importe_final NUMERIC(12,2) NOT NULL,
    forma_pago VARCHAR(50),
    dias_pago INTEGER,
    numero_factura_proveedor VARCHAR(100) UNIQUE,
    fecha_factura DATE,
    id_empleado_autorizacion UUID REFERENCES clinica.empleados(id_empleado),
    id_empleado_recepcion UUID REFERENCES clinica.empleados(id_empleado),
    control_calidad_realizado BOOLEAN DEFAULT FALSE,
    observaciones TEXT,
    documento_adjunto BYTEA,
    fecha_creacion TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    fecha_modificacion TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    
    CONSTRAINT ck_fechas_compra CHECK (fecha_entrega_real IS NULL OR fecha_entrega_real >= fecha_pedido),
    CONSTRAINT ck_importes_compra CHECK (importe_total_sin_iva > 0 AND importe_final > 0)
);

CREATE INDEX idx_compras_proveedor ON clinica.compras(id_proveedor);
CREATE INDEX idx_compras_fecha ON clinica.compras(fecha_pedido);
CREATE INDEX idx_compras_estado ON clinica.compras(estado);

-- ===================================
-- TABLA: FACTURAS
-- ===================================
CREATE TABLE clinica.facturas (
    id_factura UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    numero_factura VARCHAR(50) NOT NULL UNIQUE,
    id_paciente UUID NOT NULL REFERENCES clinica.pacientes(id_paciente),
    fecha_factura DATE NOT NULL,
    fecha_vencimiento DATE NOT NULL,
    tipo_factura VARCHAR(50) NOT NULL DEFAULT 'ORDINARIA',
    estado VARCHAR(50) NOT NULL DEFAULT 'EMITIDA' CHECK (estado IN ('EMITIDA', 'PAGADA', 'PARCIAL', 'VENCIDA', 'CANCELADA', 'ANULADA')),
    base_imponible NUMERIC(12,2) NOT NULL,
    iva_percent NUMERIC(5,2) DEFAULT 21,
    importe_iva NUMERIC(12,2),
    otros_impuestos NUMERIC(10,2) DEFAULT 0,
    descuento_global_percent NUMERIC(5,2) DEFAULT 0,
    importe_descuento NUMERIC(12,2),
    importe_total_factura NUMERIC(12,2) NOT NULL,
    forma_pago VARCHAR(50),
    numero_referencia_pago VARCHAR(100),
    dias_credito INTEGER DEFAULT 30,
    cuenta_contable VARCHAR(50),
    observaciones TEXT,
    id_empleado_emisor UUID REFERENCES clinica.empleados(id_empleado),
    id_empleado_autorizacion UUID REFERENCES clinica.empleados(id_empleado),
    firma_digital BYTEA,
    fecha_creacion TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    fecha_modificacion TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    
    CONSTRAINT ck_importes_factura CHECK (base_imponible > 0 AND importe_total_factura > 0),
    CONSTRAINT ck_fecha_vencimiento CHECK (fecha_vencimiento >= fecha_factura)
);

CREATE INDEX idx_facturas_paciente ON clinica.facturas(id_paciente);
CREATE INDEX idx_facturas_fecha ON clinica.facturas(fecha_factura);
CREATE INDEX idx_facturas_estado ON clinica.facturas(estado);

-- ===================================
-- TABLA: USUARIOS_SISTEMA
-- ===================================
CREATE TABLE clinica.usuarios_sistema (
    id_usuario UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nombre_usuario VARCHAR(100) NOT NULL UNIQUE,
    id_empleado UUID UNIQUE REFERENCES clinica.empleados(id_empleado),
    email_usuario VARCHAR(255) NOT NULL UNIQUE,
    contraseña_hash VARCHAR(255) NOT NULL,
    salt_contraseña VARCHAR(255) NOT NULL,
    metodo_hash VARCHAR(50) DEFAULT 'ARGON2',
    email_verificado BOOLEAN DEFAULT FALSE,
    autenticacion_dos_factores BOOLEAN DEFAULT FALSE,
    tipo_segundo_factor VARCHAR(50),
    ultimo_acceso TIMESTAMP WITH TIME ZONE,
    numero_intentos_fallidos INTEGER DEFAULT 0,
    bloqueado BOOLEAN DEFAULT FALSE,
    fecha_bloqueo TIMESTAMP WITH TIME ZONE,
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO' CHECK (estado IN ('ACTIVO', 'INACTIVO', 'SUSPENDIDO')),
    fecha_ultima_modificacion_contraseña TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    cambio_contraseña_requerido BOOLEAN DEFAULT FALSE,
    acepta_terminos_servicio BOOLEAN NOT NULL DEFAULT FALSE,
    fecha_aceptacion DATE,
    fecha_creacion TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    fecha_modificacion TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    
    CONSTRAINT ck_email_usuario CHECK (email_usuario ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$')
);

CREATE INDEX idx_usuarios_nombre ON clinica.usuarios_sistema(nombre_usuario);
CREATE INDEX idx_usuarios_email ON clinica.usuarios_sistema(email_usuario);
CREATE INDEX idx_usuarios_estado ON clinica.usuarios_sistema(estado);

-- ===================================
-- TABLA: AUDITORIA
-- ===================================
CREATE TABLE clinica.auditoria (
    id_evento UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_usuario UUID REFERENCES clinica.usuarios_sistema(id_usuario),
    tipo_evento VARCHAR(100) NOT NULL,
    modulo_afectado VARCHAR(100),
    entidad_afectada VARCHAR(100),
    id_registro_afectado UUID,
    valor_anterior TEXT,
    valor_nuevo TEXT,
    descripcion_evento TEXT,
    ip_origen VARCHAR(45),
    fecha_evento TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    resultado_evento VARCHAR(20) DEFAULT 'EXITO' CHECK (resultado_evento IN ('EXITO', 'ERROR')),
    codigo_error VARCHAR(50),
    mensaje_error TEXT,
    nivel_severidad VARCHAR(20) CHECK (nivel_severidad IN ('INFO', 'ADVERTENCIA', 'CRITICO')),
    
    CONSTRAINT ck_ipv4_ipv6 CHECK (ip_origen ~ '^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$' OR ip_origen ~ '^[0-9a-fA-F:]+$')
);

CREATE INDEX idx_auditoria_usuario ON clinica.auditoria(id_usuario);
CREATE INDEX idx_auditoria_fecha ON clinica.auditoria(fecha_evento);
CREATE INDEX idx_auditoria_tipo ON clinica.auditoria(tipo_evento);

-- ===================================
-- FUNCIONES ÚTILES
-- ===================================

-- Función para calcular edad
CREATE OR REPLACE FUNCTION clinica.calcular_edad(fecha_nac DATE)
RETURNS INTEGER AS $$
BEGIN
    RETURN EXTRACT(YEAR FROM AGE(fecha_nac));
END;
$$ LANGUAGE plpgsql IMMUTABLE;

-- Función para auditar cambios
CREATE OR REPLACE FUNCTION clinica.auditar_cambios()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO clinica.auditoria (
        id_usuario, tipo_evento, modulo_afectado, entidad_afectada,
        id_registro_afectado, valor_anterior, valor_nuevo, descripcion_evento,
        nivel_severidad
    ) VALUES (
        COALESCE(current_setting('app.user_id')::UUID, NULL),
        TG_OP,
        TG_TABLE_SCHEMA,
        TG_TABLE_NAME,
        NEW.id_paciente,
        row_to_json(OLD),
        row_to_json(NEW),
        'Cambio en tabla ' || TG_TABLE_NAME,
        'INFO'
    );
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Triggers de auditoría
CREATE TRIGGER trigger_auditoria_pacientes
AFTER UPDATE ON clinica.pacientes
FOR EACH ROW EXECUTE FUNCTION clinica.auditar_cambios();

CREATE TRIGGER trigger_auditoria_empleados
AFTER UPDATE ON clinica.empleados
FOR EACH ROW EXECUTE FUNCTION clinica.auditar_cambios();

-- ===================================
-- PERMISOS Y ROLES
-- ===================================

-- Crear roles
CREATE ROLE clinica_dentista WITH LOGIN;
CREATE ROLE clinica_higienista WITH LOGIN;
CREATE ROLE clinica_administrativo WITH LOGIN;
CREATE ROLE clinica_gestor WITH LOGIN;
CREATE ROLE clinica_admin WITH LOGIN SUPERUSER;

-- Permisos básicos para dentista (acceso a pacientes, citas, tratamientos)
GRANT USAGE ON SCHEMA clinica TO clinica_dentista;
GRANT SELECT, INSERT, UPDATE ON clinica.pacientes TO clinica_dentista;
GRANT SELECT, INSERT, UPDATE ON clinica.citas TO clinica_dentista;
GRANT SELECT, INSERT, UPDATE ON clinica.tratamientos TO clinica_dentista;
GRANT SELECT, INSERT ON clinica.diagnosticos TO clinica_dentista;

-- Permisos administrativo
GRANT USAGE ON SCHEMA clinica TO clinica_administrativo;
GRANT SELECT ON clinica.pacientes TO clinica_administrativo;
GRANT SELECT, INSERT, UPDATE ON clinica.citas TO clinica_administrativo;
GRANT SELECT ON clinica.empleados TO clinica_administrativo;
GRANT SELECT, INSERT, UPDATE ON clinica.facturas TO clinica_administrativo;

-- ===================================
-- VISTAS ÚTILES
-- ===================================

CREATE VIEW clinica.v_citas_proximas AS
SELECT 
    c.id_cita,
    p.nombre_completo AS paciente,
    e.nombre_completo AS dentista,
    c.fecha_cita,
    c.hora_inicio,
    c.tipo_cita,
    c.estado
FROM clinica.citas c
JOIN clinica.pacientes p ON c.id_paciente = p.id_paciente
LEFT JOIN clinica.empleados e ON c.id_empleado_dentista = e.id_empleado
WHERE c.fecha_cita >= CURRENT_DATE
ORDER BY c.fecha_cita, c.hora_inicio;

CREATE VIEW clinica.v_facturacion_pendiente AS
SELECT 
    f.numero_factura,
    p.nombre_completo AS paciente,
    f.importe_total_factura,
    f.fecha_vencimiento,
    (CURRENT_DATE - f.fecha_vencimiento) AS dias_vencimiento
FROM clinica.facturas f
JOIN clinica.pacientes p ON f.id_paciente = p.id_paciente
WHERE f.estado IN ('EMITIDA', 'PARCIAL', 'VENCIDA')
ORDER BY f.fecha_vencimiento;

CREATE VIEW clinica.v_inventario_bajo_stock AS
SELECT 
    i.codigo_insumo,
    i.nombre_producto,
    i.cantidad_actual_stock,
    i.cantidad_minima_stock,
    pr.nombre_razon_social AS proveedor
FROM clinica.insumos i
LEFT JOIN clinica.proveedores pr ON i.id_proveedor_habitual = pr.id_proveedor
WHERE i.cantidad_actual_stock <= i.cantidad_minima_stock
    AND i.estado = 'DISPONIBLE'
ORDER BY i.cantidad_actual_stock;

-- ===================================
-- COMENTARIOS EN TABLAS Y COLUMNAS
-- ===================================

COMMENT ON TABLE clinica.pacientes IS 'Almacena la información demográfica y clínica de los pacientes. Cumple con RGPD.';
COMMENT ON COLUMN clinica.pacientes.numero_identificacion IS 'DNI, NIE o Pasaporte - Único por paciente';
COMMENT ON COLUMN clinica.pacientes.consentimiento_rgpd IS 'Consentimiento explícito para tratamiento de datos personales';

COMMENT ON TABLE clinica.empleados IS 'Información profesional de dentistas, higienistas y staff. Contiene datos de colegiación.';
COMMENT ON COLUMN clinica.empleados.numero_colegiado IS 'Número de colegio profesional, único a nivel nacional';
COMMENT ON COLUMN clinica.empleados.permiso_firmar_documentos IS 'Autorización para firmar digitalmente documentos clínicos';

COMMENT ON TABLE clinica.tratamientos IS 'Historial de tratamientos realizados. Vinculado a diagnósticos y facturación.';
COMMENT ON COLUMN clinica.tratamientos.clasificacion IS 'Clasificación según tipo de tratamiento odontológico';
COMMENT ON COLUMN clinica.tratamientos.diente_afectado IS 'Numeración FDI (1-8 superior derecha, 9-16 superior izquierda, etc.)';

COMMENT ON TABLE clinica.auditoria IS 'Registro de todos los cambios en datos sensibles. Cumple con ISO 27001.';

GRANT USAGE ON SCHEMA clinica TO PUBLIC;
