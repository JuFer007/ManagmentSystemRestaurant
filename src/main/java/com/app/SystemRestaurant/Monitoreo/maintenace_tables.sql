-- Script de creación de tablas para el módulo de mantenimiento
-- Sistema de Restaurante - Módulo de Mantenimiento

-- Tabla de Registro de Mantenimiento
CREATE TABLE IF NOT EXISTS registro_mantenimiento (
    id_registro INT AUTO_INCREMENT PRIMARY KEY,
    tipo_mantenimiento VARCHAR(50) NOT NULL,
    descripcion TEXT NOT NULL,
    fecha_inicio DATETIME NOT NULL,
    fecha_fin DATETIME,
    estado VARCHAR(30) NOT NULL DEFAULT 'Pendiente',
    responsable VARCHAR(100),
    observaciones VARCHAR(1000),
    componente_afectado VARCHAR(100),
    prioridad VARCHAR(20),
    fecha_registro DATETIME NOT NULL,
    INDEX idx_tipo (tipo_mantenimiento),
    INDEX idx_estado (estado),
    INDEX idx_responsable (responsable),
    INDEX idx_fecha_registro (fecha_registro)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de Incidentes del Sistema
CREATE TABLE IF NOT EXISTS incidentes_sistema (
    id_incidente INT AUTO_INCREMENT PRIMARY KEY,
    codigo_incidente VARCHAR(50) UNIQUE NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    descripcion TEXT NOT NULL,
    severidad VARCHAR(20) NOT NULL,
    estado VARCHAR(30) NOT NULL DEFAULT 'Reportado',
    fecha_reporte DATETIME NOT NULL,
    fecha_resolucion DATETIME,
    reportado_por VARCHAR(100),
    asignado_a VARCHAR(100),
    modulo_afectado VARCHAR(100),
    solucion_aplicada TEXT,
    observaciones VARCHAR(1000),
    requiere_cambio BOOLEAN DEFAULT FALSE,
    INDEX idx_codigo (codigo_incidente),
    INDEX idx_estado (estado),
    INDEX idx_severidad (severidad),
    INDEX idx_asignado (asignado_a),
    INDEX idx_fecha_reporte (fecha_reporte)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de Solicitudes de Cambio
CREATE TABLE IF NOT EXISTS solicitud_cambio (
    id_solicitud INT AUTO_INCREMENT PRIMARY KEY,
    codigo_solicitud VARCHAR(50) UNIQUE NOT NULL,
    solicitante VARCHAR(100) NOT NULL,
    fecha_solicitud DATE NOT NULL,
    motivo VARCHAR(50) NOT NULL,
    descripcion_cambio TEXT NOT NULL,
    desarrolladores_acargo VARCHAR(200),
    fecha_despliегue_propuesta DATE,
    fecha_despliegue DATE,
    estado VARCHAR(30) NOT NULL DEFAULT 'Solicitado',
    prioridad VARCHAR(20),
    artefactos_afectados VARCHAR(1000),
    servidores_utilizados VARCHAR(500),
    operador_acargo VARCHAR(100),
    requiere_revertir BOOLEAN DEFAULT FALSE,
    incidentes_registrados_tras_depliegue INT DEFAULT 0,
    procedimiento_realizar TEXT,
    observaciones VARCHAR(1000),
    version_documento VARCHAR(20),
    fecha_registro DATETIME NOT NULL,
    INDEX idx_codigo (codigo_solicitud),
    INDEX idx_estado (estado),
    INDEX idx_prioridad (prioridad),
    INDEX idx_solicitante (solicitante),
    INDEX idx_fecha_solicitud (fecha_solicitud)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insertar datos de ejemplo para Registro de Mantenimiento
INSERT INTO registro_mantenimiento
(tipo_mantenimiento, descripcion, fecha_inicio, estado, responsable, componente_afectado, prioridad, fecha_registro)
VALUES
('Preventivo', 'Backup automático diario de base de datos', NOW(), 'Completado', 'Sistema', 'Base de Datos', 'Alta', NOW()),
('Preventivo', 'Limpieza de archivos temporales', NOW(), 'Completado', 'Sistema', 'Servidor', 'Media', NOW()),
('Correctivo', 'Reparación de índices de base de datos', NOW(), 'Pendiente', 'DBA', 'Base de Datos', 'Alta', NOW());

-- Insertar datos de ejemplo para Incidentes
INSERT INTO incidentes_sistema
(codigo_incidente, titulo, descripcion, severidad, estado, fecha_reporte, reportado_por, modulo_afectado, requiere_cambio)
VALUES
('INC-001', 'Lentitud en carga de pedidos', 'Los pedidos tardan más de 5 segundos en cargar', 'Media', 'Reportado', NOW(), 'Usuario Admin', 'Módulo de Pedidos', FALSE),
('INC-002', 'Error al generar ticket PDF', 'No se puede generar el PDF del ticket en algunos casos', 'Alta', 'En Análisis', NOW(), 'Usuario Mesero', 'Servicio de Tickets', TRUE);

-- Insertar datos de ejemplo para Solicitudes de Cambio
INSERT INTO solicitud_cambio
(codigo_solicitud, solicitante, fecha_solicitud, motivo, descripcion_cambio, estado, prioridad, version_documento, fecha_registro)
VALUES
('SOL-2025-001', 'Admin Sistema', CURDATE(), 'Mejora', 'Implementar módulo de mantenimiento con registro de incidentes y solicitudes de cambio', 'En Desarrollo', 'Alta', '1.0', NOW()),
('SOL-2025-002', 'Gerente TI', CURDATE(), 'Nueva Funcionalidad', 'Agregar reportes de ventas por periodo personalizado', 'Solicitado', 'Media', '1.0', NOW());

-- Vistas útiles para reportes

-- Vista: Resumen de Mantenimientos Pendientes
CREATE OR REPLACE VIEW v_mantenimientos_pendientes AS
SELECT
    id_registro,
    tipo_mantenimiento,
    descripcion,
    fecha_inicio,
    responsable,
    prioridad,
    DATEDIFF(NOW(), fecha_inicio) as dias_pendiente
FROM registro_mantenimiento
WHERE estado IN ('Pendiente', 'En Proceso')
ORDER BY prioridad DESC, fecha_inicio ASC;

-- Vista: Incidentes Abiertos Críticos
CREATE OR REPLACE VIEW v_incidentes_criticos_abiertos AS
SELECT
    codigo_incidente,
    titulo,
    severidad,
    estado,
    fecha_reporte,
    asignado_a,
    modulo_afectado,
    DATEDIFF(NOW(), fecha_reporte) as dias_abierto
FROM incidentes_sistema
WHERE estado NOT IN ('Resuelto', 'Cerrado')
  AND severidad IN ('Alta', 'Crítica')
ORDER BY severidad DESC, fecha_reporte ASC;

-- Vista: Solicitudes Pendientes de Aprobación
CREATE OR REPLACE VIEW v_solicitudes_pendientes_aprobacion AS
SELECT
    codigo_solicitud,
    solicitante,
    fecha_solicitud,
    motivo,
    descripcion_cambio,
    prioridad,
    DATEDIFF(NOW(), fecha_solicitud) as dias_esperando
FROM solicitud_cambio
WHERE estado = 'Solicitado'
ORDER BY prioridad DESC, fecha_solicitud ASC;

-- Procedimiento almacenado para cerrar incidentes automáticamente después de 30 días resueltos
DELIMITER //

CREATE PROCEDURE cerrar_incidentes_antiguos()
BEGIN
    UPDATE incidentes_sistema
    SET estado = 'Cerrado',
        observaciones = CONCAT(COALESCE(observaciones, ''), ' - Cerrado automáticamente después de 30 días de resolución')
    WHERE estado = 'Resuelto'
      AND DATEDIFF(NOW(), fecha_resolucion) >= 30;
END //

DELIMITER ;

-- Trigger para actualizar automáticamente la fecha de fin cuando se completa un mantenimiento
DELIMITER //

CREATE TRIGGER trg_completar_mantenimiento
BEFORE UPDATE ON registro_mantenimiento
FOR EACH ROW
BEGIN
    IF NEW.estado = 'Completado' AND OLD.estado != 'Completado' THEN
        SET NEW.fecha_fin = NOW();
    END IF;
END //

DELIMITER ;

-- Consultas útiles para monitoreo

-- Estadísticas de mantenimiento por tipo
SELECT
    tipo_mantenimiento,
    COUNT(*) as total,
    SUM(CASE WHEN estado = 'Completado' THEN 1 ELSE 0 END) as completados,
    SUM(CASE WHEN estado = 'Pendiente' THEN 1 ELSE 0 END) as pendientes,
    SUM(CASE WHEN estado = 'En Proceso' THEN 1 ELSE 0 END) as en_proceso
FROM registro_mantenimiento
GROUP BY tipo_mantenimiento;

-- Incidentes por severidad y estado
SELECT
    severidad,
    estado,
    COUNT(*) as cantidad,
    AVG(DATEDIFF(COALESCE(fecha_resolucion, NOW()), fecha_reporte)) as promedio_dias_resolucion
FROM incidentes_sistema
GROUP BY severidad, estado
ORDER BY severidad DESC, estado;

-- Solicitudes por estado
SELECT
    estado,
    COUNT(*) as cantidad,
    AVG(DATEDIFF(COALESCE(fecha_despliegue, NOW()), fecha_solicitud)) as promedio_dias_proceso
FROM solicitud_cambio
GROUP BY estado
ORDER BY FIELD(estado, 'Solicitado', 'Aprobado', 'En Desarrollo', 'En Testing', 'Desplegado', 'Rechazado');
