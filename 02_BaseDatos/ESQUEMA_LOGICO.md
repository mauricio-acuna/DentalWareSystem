# Esquema Lógico de Base de Datos
## Clínica Odontológica

### ENTIDADES CORE

#### 1. PACIENTES
```sql
Paciente {
  id_paciente (PK)
  numero_identificacion (UNICO) -- DNI/NIE/Pasaporte
  nombre_completo
  apellidos
  fecha_nacimiento
  genero
  estado_civil
  direccion
  codigo_postal
  ciudad
  pais
  telefono_movil
  telefono_fijo
  email (UNICO)
  fecha_alta
  fecha_baja
  estado (ACTIVO/INACTIVO/SUSPENDIDO)
  consentimiento_rgpd (BOOL)
  historial_alergias
  patologias_base
  medicamentos_actuales
  nota_clínica_general
  fecha_modificacion
  usuario_modificacion
}
```

#### 2. EMPLEADOS
```sql
Empleado {
  id_empleado (PK)
  numero_colegiado (UNICO) -- Colegio profesional
  nombre_completo
  apellidos
  fecha_nacimiento
  genero
  numero_identificacion (UNICO)
  especialidad (DENTISTA/HIGIENISTA/AUXILIAR/ADMINISTRATIVO/GESTOR)
  nivel_cualificacion
  numero_colegio_profesional
  pais_registro
  fecha_registro_profesional
  licencias_activas
  formacion_continua_actualizada (BOOL)
  direccion
  email_corporativo (UNICO)
  telefono
  fecha_contratacion
  tipo_contrato (PERMANENTE/TEMPORAL/AUTONOMO)
  estado_laboral (ACTIVO/BAJA/LICENCIA)
  departamento
  turno
  horario
  usuario_sistema_login (UNICO)
  rol_sistema
  permiso_firmar_documentos (BOOL)
  firma_digital
  fecha_fin_contrato
  fecha_creacion
  fecha_modificacion
}
```

#### 3. CITAS
```sql
Cita {
  id_cita (PK)
  id_paciente (FK)
  id_empleado_dentista (FK)
  id_empleado_higienista (FK)
  fecha_cita
  hora_inicio
  hora_fin
  duracion_estimada_minutos
  tipo_cita (CONSULTA/TRATAMIENTO/URGENCIA/SEGUIMIENTO)
  estado (PROGRAMADA/CONFIRMADA/COMPLETADA/CANCELADA/NO_PRESENTADO)
  motivo_consulta
  notas_previa
  resultado_cita
  procedimientos_realizados
  proximo_seguimiento
  fecha_confirmacion_paciente
  metodo_recordatorio (SMS/EMAIL/LLAMADA)
  sala_consulta
  prioridad (BAJA/MEDIA/ALTA/URGENCIA)
  fecha_creacion
  fecha_modificacion
  usuario_creacion
}
```

#### 4. TRATAMIENTOS
```sql
Tratamiento {
  id_tratamiento (PK)
  id_cita (FK)
  id_paciente (FK)
  codigo_tratamiento (UNICO)
  nombre_tratamiento
  descripcion_detallada
  clasificacion (PREVENTIVA/RESTAURADORA/ENDODONCIA/PERIODONTAL/QUIRURGICA/ORTODONCIA/ESTETICA)
  diente_afectado (CODIGO_FDI) -- Numeración FDI 1-32
  superficie_diente
  diagnostico_asociado
  plan_tratamiento
  presupuesto_inicial
  coste_real
  cobertura_seguro
  copago_paciente
  materiales_utilizados
  duracion_estimada_minutos
  duracion_real_minutos
  fecha_inicio
  fecha_completacion
  estado (PENDIENTE/EN_CURSO/COMPLETADO/SUSPENDIDO/CANCELADO)
  empleado_responsable (FK)
  complicaciones
  resultado_clinico
  observaciones
  fecha_proximo_control
  fecha_creacion
  fecha_modificacion
}
```

#### 5. DIAGNOSTICOS
```sql
Diagnostico {
  id_diagnostico (PK)
  id_cita (FK)
  id_paciente (FK)
  codigo_CIE10 (UNICO) -- Clasificación Internacional Enfermedades
  descripcion_diagnostico
  diente_afectado
  fecha_diagnostico
  empleado_diagnosticador (FK)
  severidad (LEVE/MODERADA/SEVERA)
  confirmado_laboratorio (BOOL)
  hallazgos_radiologicos
  plan_tratamiento_recomendado
  urgencia_tratamiento
  fecha_resolucion
  diagnostico_relacionado (FK)
  fecha_creacion
}
```

#### 6. PROVEEDORES
```sql
Proveedor {
  id_proveedor (PK)
  numero_identificacion (UNICO) -- CIF/NIF
  nombre_razon_social (UNICO)
  tipo_proveedor (MATERIALES/EQUIPOS/SERVICIOS/MIXTO)
  nombre_contacto
  telefono
  email
  direccion
  codigo_postal
  ciudad
  pais
  web
  cuenta_bancaria
  iban (UNICO)
  forma_pago
  dias_pago_medio
  terminos_pago
  calificacion_calidad (0-10)
  certificaciones (CE/ISO/OTROS)
  fecha_certificacion_valida_hasta
  estado (ACTIVO/INACTIVO/SUSPENDIDO)
  notas
  fecha_alta
  fecha_baja
  fecha_creacion
  fecha_modificacion
}
```

#### 7. INSUMOS/MATERIALES
```sql
Insumo {
  id_insumo (PK)
  codigo_insumo (UNICO)
  nombre_producto
  descripcion
  categoria (INSTRUMENTOS/MATERIALES_OBTURACION/MEDICAMENTOS/EQUIPOS_CONSUMIBLES/OTROS)
  fabricante
  codigo_lote
  fecha_fabricacion
  fecha_caducidad
  numero_serie (SI APLICA)
  precio_unitario_compra
  precio_unitario_venta
  margen_beneficio_percent
  unidad_medida (UNIDAD/KG/L/METRO/CAJA)
  cantidad_minima_stock
  cantidad_optima_stock
  cantidad_actual_stock
  localizacion_almacen
  id_proveedor_habitual (FK)
  proveedores_alternativos
  normativa_almacenamiento
  temperatura_optima
  humedad_optima
  necesita_refrigeracion (BOOL)
  condicion_conservacion
  certificacion_ce (BOOL)
  biocompatibilidad_certificada (BOOL)
  esteril_compra (BOOL)
  requiere_esterilizacion (BOOL)
  instrucciones_uso
  efectos_adversos_conocidos
  contraindicaciones
  proveedores_certificados
  licitacion_publica (BOOL)
  precio_acuerdo_marco
  estado (DISPONIBLE/DESCONTINUADO/RETIRADO)
  fecha_creacion
  fecha_modificacion
}
```

#### 8. MOVIMIENTOS_INVENTARIO
```sql
MovimientoInventario {
  id_movimiento (PK)
  id_insumo (FK)
  tipo_movimiento (ENTRADA/SALIDA/AJUSTE/DEVOLUCION/PERDIDA)
  cantidad
  fecha_movimiento
  numero_compra (FK)
  numero_salida_clinica
  id_empleado_registra (FK)
  observaciones
  documento_referencia
  ubicacion_origen
  ubicacion_destino
  precio_unitario_momento
  fecha_creacion
}
```

#### 9. COMPRAS
```sql
Compra {
  id_compra (PK)
  numero_pedido (UNICO)
  id_proveedor (FK)
  fecha_pedido
  fecha_entrega_prevista
  fecha_entrega_real
  estado (SOLICITADA/CONFIRMADA/PARCIAL/ENTREGADA/CANCELADA)
  lineas_pedido (RELACION)
  cantidad_total_items
  importe_total_sin_iva
  iva_aplicable_percent
  importe_iva
  importe_total_con_iva
  descuento_percent
  importe_descuento
  gastos_envio
  otros_gastos
  importe_final
  forma_pago (CONTADO/CREDITO/TRANSFERENCIA/DOMICILIACION)
  dias_pago
  referencia_pago
  moneda
  tasa_cambio_eur
  numero_factura_proveedor (UNICO)
  fecha_factura
  empleado_autorizacion (FK)
  empleado_recepcion (FK)
  control_calidad_realizado (BOOL)
  observaciones
  documento_adjunto
  fecha_creacion
  fecha_modificacion
}
```

#### 10. LINEAS_PEDIDO
```sql
LineaPedido {
  id_linea (PK)
  id_compra (FK)
  id_insumo (FK)
  cantidad_solicitada
  cantidad_recibida
  precio_unitario
  descuento_linea_percent
  importe_linea
  estado_linea (PENDIENTE/PARCIAL/COMPLETA)
  fecha_entrega_estimada
  fecha_entrega_real
  observaciones
}
```

#### 11. FACTURACION
```sql
Factura {
  id_factura (PK)
  numero_factura (UNICO)
  numero_factura_simplificada (SI APLICA)
  id_paciente (FK)
  fecha_factura
  fecha_vencimiento
  tipo_factura (ORDINARIA/SIMPLIFICADA/RECTIFICATIVA/COMPLEMENTARIA)
  estado (EMITIDA/PAGADA/PARCIAL/VENCIDA/CANCELADA/ANULADA)
  lineas_factura (RELACION)
  base_imponible
  iva_percent
  importe_iva
  otros_impuestos
  importe_total_sin_descuento
  descuento_global_percent
  importe_descuento
  importe_total_factura
  forma_pago (CONTADO/CREDITO/TRANSFERENCIA/TARJETA)
  numero_referencia_pago
  fecha_pago_efectivo
  dias_credito
  cuenta_contable
  entidad_bancaria_recepcion
  numero_comprobante_pago
  observaciones_factura
  empleado_emisor (FK)
  empleado_autorizacion (FK)
  copia_papel_entregada (BOOL)
  envio_electronico_paciente (BOOL)
  fecha_envio
  aceptacion_electronica_paciente (BOOL)
  firma_digital_factura
  documento_adjunto
  fecha_creacion
  fecha_modificacion
}
```

#### 12. LINEAS_FACTURA
```sql
LineaFactura {
  id_linea (PK)
  id_factura (FK)
  id_tratamiento (FK)
  id_insumo (FK)
  descripcion_concepto
  cantidad
  precio_unitario
  importe_linea
  iva_linea_percent
  descuento_linea_percent
  concepto_tipo (TRATAMIENTO/MATERIAL/SERVICIO/PRESTAMO)
  observaciones
}
```

#### 13. PAGOS
```sql
Pago {
  id_pago (PK)
  id_factura (FK)
  numero_recibo (UNICO)
  fecha_pago
  forma_pago (EFECTIVO/TRANSFERENCIA/TARJETA/CHEQUE/DOMICILIACION)
  importe_pagado
  moneda
  numero_referencia_transaccion
  entidad_bancaria
  numero_cuenta_origen
  comprobante_pago
  empleado_recepcion (FK)
  notas
  reembolso_paciente (BOOL)
  numero_devolucion (FK)
  fecha_creacion
}
```

#### 14. PRESUPUESTOS
```sql
Presupuesto {
  id_presupuesto (PK)
  numero_presupuesto (UNICO)
  id_paciente (FK)
  fecha_presupuesto
  fecha_validez_hasta
  estado (VIGENTE/ACEPTADO/RECHAZADO/PARCIAL/CADUCADO)
  lineas_presupuesto (RELACION)
  base_imponible
  iva_percent
  importe_iva
  importe_total
  descuento_especial
  observaciones_clinicas
  plan_tratamiento_detallado
  duracion_tratamiento_estimada
  empleado_elaboracion (FK)
  empleado_aprobacion (FK)
  firmado_paciente (BOOL)
  fecha_firma_paciente
  aceptacion_electronica (BOOL)
  seguro_responsabilidad_civíl (BOOL)
  cobertura_seguro_disponible
  fecha_creacion
}
```

#### 15. LINEAS_PRESUPUESTO
```sql
LineaPresupuesto {
  id_linea (PK)
  id_presupuesto (FK)
  descripcion_concepto
  unidades
  precio_unitario
  importe_linea
  iva_linea_percent
  prioridad_tratamiento (1-5)
  urgencia (SI/NO)
  observaciones
}
```

#### 16. HISTORIALES_CLINICOS
```sql
HistorialClinico {
  id_historial (PK)
  id_paciente (FK)
  fecha_creacion_historial
  estado_sanitario_general
  patologias_base_paciente
  alergias_medicamentosas
  medicamentos_actuales
  intervenciones_previas
  enfermedades_infecciosas
  habitos_riesgo (TABACO/ALCOHOL/DROGAS)
  dieta
  higiene_bucal_estado
  fecha_actualizacion_historial
  empleado_responsable (FK)
  notas_generales
  restricciones_clinicas
  contacto_emergencia
  telefono_emergencia
  relacion_emergencia
  fecha_revision_informacion
  paciente_confirma_exactitud (BOOL)
  consentimiento_tratamiento (BOOL)
  consentimiento_fotos_clinicas (BOOL)
  consentimiento_enseñanza (BOOL)
  restricciones_comunicacion
  datos_seguros (BOOL)
  fecha_modificacion
}
```

#### 17. RADIOGRAFIAS
```sql
Radiografia {
  id_radiografia (PK)
  id_paciente (FK)
  id_cita (FK)
  tipo_radiografia (PERIAPICAL/INTERPROXIMAL/OCLUSAL/PANORAMICA/TC)
  fecha_toma
  diente_afectado (CODIGO_FDI)
  tecnica_utilized
  voltaje_kv
  miliamperaje_ma
  tiempo_exposicion
  archivo_imagen (RUTA)
  formato_imagen (DICOM/JPG/PNG)
  tamaño_archivo_mb
  compresion_aplicada
  interpretacion_radiografica
  diagnostico_asociado (FK)
  empleado_toma (FK)
  empleado_interpretacion (FK)
  hallazgos_patologicos
  caries_presentes (BOOL)
  fractura_osea (BOOL)
  lesion_periapical (BOOL)
  reabsorcion_raiz (BOOL)
  anomalias_desarrollo (BOOL)
  comparativa_anterior_disponible (BOOL)
  id_radiografia_anterior (FK)
  cambios_desde_anterior
  archivo_copia_seguridad (RUTA)
  proteccion_datos (ENCRIPTADA/SIN_ENCRIPTAR)
  autorizado_investigacion (BOOL)
  fecha_creacion
  fecha_modificacion
}
```

#### 18. PRESCRIPCIONES
```sql
Prescripcion {
  id_prescripcion (PK)
  numero_prescripcion (UNICO)
  id_paciente (FK)
  id_empleado_prescriptor (FK)
  id_cita (FK)
  fecha_prescripcion
  fecha_validez_hasta
  farmaco_prescrito
  concentracion_dosis
  via_administracion (ORAL/INYECTABLE/TOPICA/INHALADA)
  frecuencia_administracion
  duracion_tratamiento
  cantidad_dosis_total
  cantidad_dispensable_receta
  numero_recetas_repetibles
  ingrediente_activo
  codigo_ATC (ANATOMICA_TERAPEUTICA_QUIMICA)
  posologia
  contraindicaciones
  advertencias_especiales
  interacciones_medicamentosas
  efectos_secundarios_potenciales
  instrucciones_paciente
  recomendaciones_seguimiento
  estado (EMITIDA/DISPENSADA/VENCIDA/PARCIAL)
  dispensada_farmacia (BOOL)
  fecha_dispensacion
  numero_colegio_farmacia
  necesita_autorizacion_especial (BOOL)
  autorizada_por (FK)
  firma_digital_prescriptor
  codigo_qr_prescripcion
  sustituto_generico_permitido (BOOL)
  soporte_papel_generado (BOOL)
  soporte_electronico_enviado (BOOL)
  fecha_creacion
}
```

#### 19. DOCUMENTOS_CLINICOS
```sql
DocumentoClinico {
  id_documento (PK)
  id_paciente (FK)
  id_cita (FK)
  tipo_documento (CONSENTIMIENTO/INFORME/NOTA_CLINICA/PLAN_TRATAMIENTO/EPICRISIS/CERTIFICADO/REFERENCIA/OTRO)
  titulo_documento
  contenido_documento
  fecha_creacion_documento
  empleado_creador (FK)
  empleado_firma (FK)
  fecha_firma
  firma_electronica (BOOL)
  version_documento
  documento_anterior_relacionado (FK)
  clasificacion_confidencialidad (PUBLICO/INTERNO/CONFIDENCIAL/SECRETO)
  requiere_consentimiento_paciente (BOOL)
  consentimiento_obtenido (BOOL)
  paciente_copia_entregada (BOOL)
  archivo_almacenado (RUTA)
  formato_archivo (PDF/DOCX/TXT/OTRO)
  tamaño_archivo_mb
  encriptacion_aplicada (BOOL)
  hash_verificacion
  copia_seguridad_realizada (BOOL)
  acceso_permitido (JSON_USUARIOS)
  auditar_accesos (BOOL)
  fecha_expiracion_documento
  documento_caducado (BOOL)
  motivo_cese (SI APLICA)
  fecha_creacion
  fecha_modificacion
}
```

#### 20. AUDITORÍA
```sql
EventoAuditoria {
  id_evento (PK)
  id_usuario (FK)
  tipo_evento (ACCESO/LECTURA/MODIFICACION/ELIMINACION/CREACION/EXPORTACION/ACCESO_DENEGADO)
  modulo_afectado
  entidad_afectada
  id_registro_afectado
  valor_anterior
  valor_nuevo
  descripcion_evento
  ip_origen
  navegador_user_agent
  fecha_evento
  resultado_evento (EXITO/ERROR)
  codigo_error
  mensaje_error
  id_referencia_transaccion
  objeto_json_cambio
  nivel_severidad (INFO/ADVERTENCIA/CRITICO)
  investigado (BOOL)
  conclusiones_investigacion
  fecha_creacion
}
```

#### 21. USUARIOS_SISTEMA
```sql
UsuarioSistema {
  id_usuario (PK)
  nombre_usuario (UNICO)
  id_empleado (FK)
  email_usuario (UNICO)
  contraseña_hash
  salt_contraseña
  metodo_hash (ARGON2/BCRYPT)
  email_verificado (BOOL)
  telefono_verificado (BOOL)
  autenticacion_dos_factores (BOOL)
  tipo_segundo_factor (SMS/APP/EMAIL/HARDWARE_TOKEN)
  dispositivo_registrado
  ultimo_acceso
  numero_intentos_fallidos
  bloqueado (BOOL)
  fecha_bloqueo
  motivo_bloqueo
  fecha_desbloqueo_prevista
  roles_asignados (JSON)
  permisos_especiales (JSON)
  estado (ACTIVO/INACTIVO/SUSPENDIDO)
  fecha_ultima_modificacion_contraseña
  cambio_contraseña_requerido (BOOL)
  acepta_terminos_servicio (BOOL)
  fecha_aceptacion
  sesiones_activas
  token_refresh
  fecha_creacion
  fecha_modificacion
}
```

#### 22. ROLES_Y_PERMISOS
```sql
Rol {
  id_rol (PK)
  nombre_rol (UNICO)
  descripcion_rol
  permisos_incluidos (JSON)
  modulos_acceso (JSON)
  nivel_acceso (1-5)
  restringido_datos_sensibles (BOOL)
  puede_auditar (BOOL)
  puede_crear_usuarios (BOOL)
  puede_reportes_financieros (BOOL)
  puede_reportes_clinicos (BOOL)
  puede_exportar_datos (BOOL)
  puede_eliminar_datos (BOOL)
  vigente (BOOL)
  fecha_creacion
}

Permiso {
  id_permiso (PK)
  codigo_permiso (UNICO)
  descripcion_permiso
  modulo
  recurso
  operacion (CREAR/LEER/MODIFICAR/ELIMINAR/EXPORTAR)
  requiere_autorizacion_especial (BOOL)
  nivel_sensibilidad (BAJO/MEDIO/ALTO/CRITICO)
}
```

#### 23. EQUIPOS_CLINICOS
```sql
EquipoClinico {
  id_equipo (PK)
  codigo_inventario (UNICO)
  nombre_equipo
  fabricante
  modelo
  numero_serie (UNICO)
  tipo_equipo (SILLON/COMPRESOR/SUCCION/RADIOLOGIA/ESTERILIZADOR/OTRO)
  fecha_adquisicion
  precio_adquisicion
  vida_util_estimada_anos
  fechas_mantenimiento_recomendado
  proxima_revision_mantenimiento
  ubicacion_clinica
  estado_operativo (OPERATIVO/MANTENIMIENTO/ROTO/DESCONTINUADO)
  usuario_responsable (FK)
  registro_uso (JSON)
  horas_funcionamiento_acumuladas
  contador_ciclos (SI APLICA)
  documentacion_equipamiento (RUTA)
  manual_operacion (RUTA)
  manual_mantenimiento (RUTA)
  certificado_calibracion (RUTA)
  certificado_seguridad_electrica
  fecha_certificacion_valida
  piezas_repuesto_disponibles
  proveedor_mantenimiento (FK)
  contrato_mantenimiento (BOOL)
  numero_contrato_mantenimiento
  fecha_proximo_mantenimiento
  costo_mantenimiento_anual
  consumibles_asociados (FK)
  observaciones
  fecha_creacion
  fecha_modificacion
}
```

#### 24. MANTENIMIENTO_EQUIPOS
```sql
MantenimientoEquipo {
  id_mantenimiento (PK)
  id_equipo (FK)
  tipo_mantenimiento (PREVENTIVO/CORRECTIVO/EMERGENCIA)
  fecha_mantenimiento
  empleado_tecnico (FK)
  empleado_supervisor (FK)
  horas_trabajadas
  descripcion_tareas_realizadas
  piezas_reemplazadas
  consumibles_utilizados
  diagnostico_problemas
  solucion_aplicada
  estado_equipo_tras_mantenimiento (OPERATIVO/PARCIAL/NO_OPERATIVO)
  pruebas_realizadas
  resultados_pruebas
  documento_certificacion (RUTA)
  proxima_revision_recomendada
  costo_mantenimiento
  piezas_costo
  mano_obra_costo
  proveedor_responsable (FK)
  numero_order_servicio
  observaciones
  foto_antes (RUTA)
  foto_despues (RUTA)
  fecha_creacion
}
```

#### 25. ESTERILIZACION
```sql
CicloEsterilizacion {
  id_ciclo (PK)
  id_equipo_esterilizador (FK)
  fecha_ciclo
  hora_inicio
  hora_fin
  duracion_ciclo_minutos
  temperatura_ciclo
  presion_ciclo
  tipo_ciclo (VAPOR/QUIMICO/RADIACION)
  carga_num_instrumentos
  lote_esterilizacion
  empleado_responsable (FK)
  empleado_revision (FK)
  resultado_ciclo (EXITOSO/FALLIDO/PARCIAL)
  parametros_alcanzados (BOOL)
  indicador_quimico_usado (BOOL)
  indicador_biologico_usado (BOOL)
  resultado_indicador_biologico
  fecha_resultado_indicador_biologico
  numero_certificado_esterilizacion
  instrumentos_esterilizados (LISTA_IDS)
  documento_certificacion (RUTA)
  desviaciones_detectadas
  acciones_correctivas
  proxima_validacion_recomendada
  archivo_registro_ciclo (RUTA)
  fecha_creacion
}

InstrumentoEsterilizado {
  id_instrumento_esteril (PK)
  id_instrumento_base (FK)
  numero_ciclo_esterilizacion (FK)
  numero_lote_esterilizacion
  fecha_esterilizacion
  fecha_expiracion_esterilidad
  estado_esterilidad (ESTÉRIL/CONTAMINADO/EXPIRADO/DESCARTADO)
  ubicacion_almacenamiento
  empleado_almacenamiento (FK)
  fecha_ultimo_uso
  numero_usos_acumulados
  condiciones_almacenamiento_verificadas (BOOL)
  temperatura_almacenamiento
  humedad_almacenamiento
  paquete_integro (BOOL)
  envoltorio_esteril_integro (BOOL)
}
```

### RELACIONES PRINCIPALES
- Paciente 1:N Cita
- Paciente 1:N Tratamiento
- Paciente 1:N Historiales_Clinicos
- Empleado 1:N Cita (como dentista/higienista)
- Cita 1:N Tratamiento
- Cita 1:N Diagnosticos
- Cita 1:N Radiografias
- Cita 1:N Documentos_Clinicos
- Tratamiento 1:N Lineas_Factura
- Paciente 1:N Factura
- Factura 1:N Lineas_Factura
- Factura 1:N Pagos
- Paciente 1:N Presupuesto
- Presupuesto 1:N Lineas_Presupuesto
- Proveedor 1:N Compra
- Compra 1:N Lineas_Pedido
- Insumo 1:N Lineas_Pedido
- Insumo 1:N Movimientos_Inventario
- Insumo 1:N Lineas_Factura
- Empleado 1:N Usuarios_Sistema
- Usuario_Sistema 1:N Eventos_Auditoria
- Equipo_Clinico 1:N Mantenimiento_Equipo
- Equipo_Clinico 1:N Ciclos_Esterilizacion
