create database restauranteDB;

use restauranteDB;

-- ============================================
-- 1. INSERTAR EMPLEADOS (Tabla base)
-- ============================================
INSERT INTO empleado (dni_empleado, codigo_empleado, nombre_empleado, apellido_paterno_empleado, apellido_materno_empleado, estado_empleado, horas_trabajo, salario_empleado, cargo_empleado) VALUES
('45678901', 'EMP001', 'Juan', 'López', 'García', 'Activo', 160, 3500.00, 'Administrador'),
('45678902', 'EMP002', 'María', 'Sánchez', 'Martínez', 'Activo', 160, 2500.00, 'Cocinero'),
('45678903', 'EMP003', 'Carlos', 'Fernández', 'Rodríguez', 'Activo', 160, 2500.00, 'Cocinero'),
('45678904', 'EMP004', 'Ana', 'Díaz', 'Torres', 'Activo', 160, 1800.00, 'Mesero'),
('45678905', 'EMP005', 'Luis', 'Vargas', 'Ramírez', 'Activo', 160, 1800.00, 'Mesero'),
('45678906', 'EMP006', 'Sofía', 'Castro', 'Morales', 'Activo', 160, 1800.00, 'Mesero'),
('45678907', 'EMP007', 'Pedro', 'Flores', 'Gutiérrez', 'Activo', 160, 2500.00, 'Cocinero'),
('45678908', 'EMP008', 'Laura', 'Herrera', 'Mendoza', 'Activo', 160, 2000.00, 'Cajero'),
('45678909', 'EMP009', 'Miguel', 'Reyes', 'Silva', 'Activo', 160, 1800.00, 'Mesero'),
('45678910', 'EMP010', 'Carmen', 'Vega', 'Rojas', 'Activo', 160, 2500.00, 'Cocinero');

-- ============================================
-- 2. INSERTAR ADMINISTRADOR (OneToOne con Empleado)
-- ============================================
INSERT INTO administrador (id_empleado, telefono, correo_electronico) VALUES
(1, '987654321', 'juan.lopez@restaurant.com');

-- ============================================
-- 3. INSERTAR COCINEROS (OneToOne con Empleado)
-- ============================================
INSERT INTO cocinero (id_empleado, especialidad) VALUES
(2, 'Cocina Criolla'),
(3, 'Cocina Marina'),
(7, 'Parrilla y Carnes'),
(10, 'Repostería');

-- ============================================
-- 4. INSERTAR MESEROS (OneToOne con Empleado)
-- ============================================
INSERT INTO mesero (id_empleado, turno_trabajo) VALUES
(4, 'Mañana'),
(5, 'Tarde'),
(6, 'Noche'),
(9, 'Mañana');

-- ============================================
-- 5. INSERTAR USUARIOS (OneToOne con Empleado)
-- ============================================
INSERT INTO usuario (id_empleado, nombre_usuario, contraseña_usuario) VALUES
(1, 'juan.lopez', 'admin123'),
(2, 'maria.sanchez', 'cocina123'),
(3, 'carlos.fernandez', 'cocina123'),
(4, 'ana.diaz', 'mesero123'),
(5, 'luis.vargas', 'mesero123'),
(6, 'sofia.castro', 'mesero123'),
(7, 'pedro.flores', 'cocina123'),
(8, 'laura.herrera', 'cajero123');

-- ============================================
-- 6. INSERTAR MESAS (OneToOne con Mesero)
-- ============================================
INSERT INTO mesa (id_mesero, numero_mesa, capacidad, estado_mesa) VALUES
(1, 1, 4, 'Disponible'),
(2, 2, 6, 'Disponible'),
(3, 3, 8, 'Ocupada'),
(4, 4, 4, 'Disponible'),
(NULL, 5, 2, 'Disponible'),
(NULL, 6, 4, 'Reservada'),
(NULL, 7, 2, 'Disponible'),
(NULL, 8, 6, 'Disponible'),
(NULL, 9, 4, 'Disponible'),
(NULL, 10, 8, 'Disponible');

-- ============================================
-- 7. INSERTAR PLATOS
-- ============================================
INSERT INTO plato (nombre_plato, precio_plato, disponibilidad, imagenurl) VALUES
('Ceviche de Pescado', 35.00, 'Disponible', 'imagenesPlatos/ceviche_de_pescado.jpg'),
('Lomo Saltado', 38.00, 'Disponible', 'imagenesPlatos/lomo_saltado.jpg'),
('Arroz con Mariscos', 42.00, 'Disponible', 'imagenesPlatos/arroz_con_mariscos.jpg'),
('Ají de Gallina', 28.00, 'Disponible', 'imagenesPlatos/ají_de_gallina.jpg'),
('Causa Limeña', 22.00, 'Disponible', 'imagenesPlatos/causa_limeña.jpg'),
('Anticuchos', 25.00, 'Disponible', 'imagenesPlatos/anticuchos.jpg'),
('Tacu Tacu', 30.00, 'Disponible', 'imagenesPlatos/tacu_tacu.jpeg'),
('Chicharrón de Pescado', 36.00, 'Disponible', 'imagenesPlatos/chicharrón_pescado.jpeg');

-- ============================================
-- 8. INSERTAR CLIENTES
-- ============================================
INSERT INTO cliente (dni_cliente, nombre_cliente, apellidos_cliente) VALUES
('78901234', 'Roberto', 'Pérez Gómez'),
('78901235', 'Carmen', 'Vega Cruz'),
('78901236', 'Diego', 'Silva Rojas'),
('78901237', 'Patricia', 'Ramos Campos'),
('78901238', 'Fernando', 'Núñez Ortiz'),
('78901239', 'Gabriela', 'Chávez Luna'),
('78901240', 'Javier', 'Paredes Ríos'),
('78901241', 'Valeria', 'Aguilar Soto'),
('78901242', 'Ricardo', 'Mendoza Torres'),
('78901243', 'Daniela', 'Castro Flores'),
('78901244', 'Alberto', 'Ruiz Gómez'),
('78901245', 'Mónica', 'Salazar Díaz');

-- ============================================
-- 9. INSERTAR PEDIDOS (OneToOne con Mesa, ManyToOne con Mesero y Cliente)
-- ============================================
INSERT INTO pedido (id_mesa, id_mesero, id_cliente, fecha, estado_pedido, codigo_pedido, total_pedido) VALUES
(1, 1, 1, '2025-10-15', 'Completado', 'PED001', 70.00),
(2, 2, 2, '2025-10-16', 'Completado', 'PED002', 110.00),
(3, 3, 3, '2025-10-17', 'En Proceso', 'PED003', 156.00),
(4, 4, 4, '2025-10-17', 'En Proceso', 'PED004', 90.00),
(5, 4, 5, '2025-10-16', 'Completado', 'PED005', 84.00),
(6, 4, 6, '2025-10-17', 'En Proceso', 'PED006', 125.00),
(7, 3, 7, '2025-10-15', 'Completado', 'PED007', 82.00),
(8, 2, 8, '2025-10-16', 'Completado', 'PED008', 96.00),
(9, 1, 9, '2025-10-17', 'En Proceso', 'PED009', 134.00),
(10, 2, 10, '2025-10-15', 'Completado', 'PED010', 118.00);

-- ============================================
-- 10. INSERTAR DETALLES DE PEDIDO (CORREGIDO)
-- ============================================
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
-- Pedido 1 (PED001) - Total: 70.00
(1, 1, 2, 70.00),

-- Pedido 2 (PED002) - Total: 110.00
(2, 2, 2, 76.00),
(2, 5, 1, 22.00),
(2, 6, 1, 12.00),

-- Pedido 3 (PED003) - Total: 156.00
(3, 3, 2, 84.00),
(3, 7, 2, 72.00),

-- Pedido 4 (PED004) - Total: 90.00
(4, 6, 2, 50.00),
(4, 8, 1, 36.00),

-- Pedido 5 (PED005) - Total: 84.00
(5, 4, 3, 84.00),

-- Pedido 6 (PED006) - Total: 125.00
(6, 2, 2, 76.00),
(6, 7, 1, 30.00),

-- Pedido 7 (PED007) - Total: 82.00
(7, 5, 2, 44.00),
(7, 1, 1, 35.00),

-- Pedido 8 (PED008) - Total: 96.00
(8, 8, 2, 72.00),
(8, 5, 1, 22.00),

-- Pedido 9 (PED009) - Total: 134.00
(9, 3, 2, 84.00),
(9, 2, 1, 38.00),

-- Pedido 10 (PED010) - Total: 118.00
(10, 1, 2, 70.00),
(10, 4, 2, 48.00);

-- ============================================
-- 11. INSERTAR PAGOS (OneToOne con Pedido)
-- ============================================
INSERT INTO pago (id_pedido, monto_pago, metodo_pago, fecha_pago) VALUES
(1, 70.00, 'Efectivo', '2025-10-15'),
(2, 110.00, 'Tarjeta', '2025-10-16'),
(3, 156.00, 'Yape', '2025-10-17'),
(5, 84.00, 'Efectivo', '2025-10-16'),
(7, 82.00, 'Tarjeta', '2025-10-15'),
(8, 96.00, 'Plin', '2025-10-16'),
(10, 118.00, 'Efectivo', '2025-10-15');