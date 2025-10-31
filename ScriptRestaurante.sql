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
INSERT INTO mesa (numero_mesa, capacidad) VALUES
(1, 4),
(2, 6),
(3, 8),
(4, 4),
(5, 2),
(6, 4),
(7, 2),
(8, 6),
(9, 4),
(10, 8);

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
-- 9. INSERTAR PEDIDOS (TODOS LOS MESES)
-- ============================================

-- PEDIDOS DE MAYO 2025
INSERT INTO pedido (id_mesa, id_empleado, id_cliente, fecha, estado_pedido, codigo_pedido, total_pedido) VALUES
(1, 4, 1, '2025-05-04', 'Completado', 'PED001', 132.00),
(2, 5, 2, '2025-05-11', 'Completado', 'PED002', 88.00),
(3, 6, 3, '2025-05-17', 'Completado', 'PED003', 148.00),
(5, 4, 5, '2025-05-23', 'Completado', 'PED004', 102.00),
(7, 5, 7, '2025-05-29', 'Completado', 'PED005', 120.00);

-- PEDIDOS DE JUNIO 2025
INSERT INTO pedido (id_mesa, id_empleado, id_cliente, fecha, estado_pedido, codigo_pedido, total_pedido) VALUES
(4, 6, 4, '2025-06-05', 'Completado', 'PED006', 128.00),
(6, 4, 6, '2025-06-12', 'Completado', 'PED007', 104.00),
(8, 5, 8, '2025-06-18', 'Completado', 'PED008', 116.00),
(10, 6, 10, '2025-06-24', 'Completado', 'PED009', 95.00);

-- PEDIDOS DE JULIO 2025
INSERT INTO pedido (id_mesa, id_empleado, id_cliente, fecha, estado_pedido, codigo_pedido, total_pedido) VALUES
(2, 4, 2, '2025-07-02', 'Completado', 'PED010', 144.00),
(3, 5, 3, '2025-07-09', 'Completado', 'PED011', 106.00),
(5, 6, 5, '2025-07-15', 'Completado', 'PED012', 92.00),
(7, 4, 7, '2025-07-21', 'Completado', 'PED013', 138.00),
(9, 5, 9, '2025-07-28', 'Completado', 'PED014', 110.00);

-- PEDIDOS DE AGOSTO 2025
INSERT INTO pedido (id_mesa, id_empleado, id_cliente, fecha, estado_pedido, codigo_pedido, total_pedido) VALUES
(4, 6, 4, '2025-08-03', 'Completado', 'PED015', 112.00),
(6, 4, 6, '2025-08-10', 'Completado', 'PED016', 150.00),
(8, 5, 8, '2025-08-14', 'Completado', 'PED017', 98.00),
(10, 6, 10, '2025-08-20', 'Completado', 'PED018', 126.00),
(1, 4, 11, '2025-08-25', 'Completado', 'PED019', 85.00);

-- PEDIDOS DE SEPTIEMBRE 2025
INSERT INTO pedido (id_mesa, id_empleado, id_cliente, fecha, estado_pedido, codigo_pedido, total_pedido) VALUES
(1, 5, 1, '2025-09-05', 'Completado', 'PED020', 105.00),
(3, 6, 3, '2025-09-08', 'Completado', 'PED021', 88.00),
(5, 4, 5, '2025-09-12', 'Completado', 'PED022', 142.00),
(7, 5, 7, '2025-09-18', 'Completado', 'PED023', 76.00),
(2, 6, 2, '2025-09-22', 'Completado', 'PED024', 130.00),
(9, 4, 9, '2025-09-25', 'Completado', 'PED025', 94.00);

-- PEDIDOS DE OCTUBRE 2025
INSERT INTO pedido (id_mesa, id_empleado, id_cliente, fecha, estado_pedido, codigo_pedido, total_pedido) VALUES
(1, 5, 1, '2025-10-15', 'Completado', 'PED026', 70.00),
(2, 6, 2, '2025-10-16', 'Completado', 'PED027', 110.00),
(3, 4, 3, '2025-10-17', 'En Proceso', 'PED028', 156.00),
(4, 5, 4, '2025-10-17', 'En Proceso', 'PED029', 90.00),
(5, 6, 5, '2025-10-16', 'Completado', 'PED030', 84.00),
(6, 4, 6, '2025-10-17', 'En Proceso', 'PED031', 125.00),
(7, 5, 7, '2025-10-15', 'Completado', 'PED032', 82.00),
(8, 6, 8, '2025-10-16', 'Completado', 'PED033', 96.00),
(9, 4, 9, '2025-10-17', 'En Proceso', 'PED034', 134.00),
(10, 5, 10, '2025-10-15', 'Completado', 'PED035', 118.00);

-- ============================================
-- 10. INSERTAR DETALLES DE PEDIDO
-- ============================================

-- DETALLES MAYO 2025
-- Pedido 1 (PED001) - Total: 132.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(1, 3, 2, 84.00),
(1, 4, 1, 28.00),
(1, 6, 1, 25.00);

-- Pedido 2 (PED002) - Total: 88.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(2, 5, 4, 88.00);

-- Pedido 3 (PED003) - Total: 148.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(3, 3, 2, 84.00),
(3, 8, 1, 36.00),
(3, 4, 1, 28.00);

-- Pedido 4 (PED004) - Total: 102.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(4, 2, 2, 76.00),
(4, 6, 1, 25.00);

-- Pedido 5 (PED005) - Total: 120.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(5, 7, 4, 120.00);

-- DETALLES JUNIO 2025
-- Pedido 6 (PED006) - Total: 128.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(6, 3, 2, 84.00),
(6, 5, 2, 44.00);

-- Pedido 7 (PED007) - Total: 104.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(7, 8, 2, 72.00),
(7, 4, 1, 28.00);

-- Pedido 8 (PED008) - Total: 116.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(8, 2, 2, 76.00),
(8, 8, 1, 36.00);

-- Pedido 9 (PED009) - Total: 95.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(9, 1, 1, 35.00),
(9, 7, 2, 60.00);

-- DETALLES JULIO 2025
-- Pedido 10 (PED010) - Total: 144.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(10, 8, 4, 144.00);

-- Pedido 11 (PED011) - Total: 106.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(11, 2, 2, 76.00),
(11, 7, 1, 30.00);

-- Pedido 12 (PED012) - Total: 92.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(12, 1, 2, 70.00),
(12, 5, 1, 22.00);

-- Pedido 13 (PED013) - Total: 138.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(13, 3, 2, 84.00),
(13, 7, 1, 30.00),
(13, 5, 1, 22.00);

-- Pedido 14 (PED014) - Total: 110.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(14, 2, 2, 76.00),
(14, 4, 1, 28.00);

-- DETALLES AGOSTO 2025
-- Pedido 15 (PED015) - Total: 112.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(15, 3, 2, 84.00),
(15, 4, 1, 28.00);

-- Pedido 16 (PED016) - Total: 150.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(16, 3, 3, 126.00),
(16, 5, 1, 22.00);

-- Pedido 17 (PED017) - Total: 98.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(17, 2, 2, 76.00),
(17, 5, 1, 22.00);

-- Pedido 18 (PED018) - Total: 126.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(18, 3, 3, 126.00);

-- Pedido 19 (PED019) - Total: 85.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(19, 1, 1, 35.00),
(19, 6, 2, 50.00);

-- DETALLES SEPTIEMBRE 2025
-- Pedido 20 (PED020) - Total: 105.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(20, 2, 2, 76.00),
(20, 4, 1, 28.00);

-- Pedido 21 (PED021) - Total: 88.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(21, 3, 2, 84.00);

-- Pedido 22 (PED022) - Total: 142.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(22, 3, 2, 84.00),
(22, 1, 1, 35.00),
(22, 6, 1, 25.00);

-- Pedido 23 (PED023) - Total: 76.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(23, 2, 2, 76.00);

-- Pedido 24 (PED024) - Total: 130.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(24, 7, 3, 90.00),
(24, 8, 1, 36.00);

-- Pedido 25 (PED025) - Total: 94.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(25, 1, 2, 70.00),
(25, 5, 1, 22.00);

-- DETALLES OCTUBRE 2025
-- Pedido 26 (PED026) - Total: 70.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(26, 1, 2, 70.00);

-- Pedido 27 (PED027) - Total: 110.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(27, 2, 2, 76.00),
(27, 5, 1, 22.00),
(27, 6, 1, 12.00);

-- Pedido 28 (PED028) - Total: 156.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(28, 3, 2, 84.00),
(28, 7, 2, 72.00);

-- Pedido 29 (PED029) - Total: 90.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(29, 6, 2, 50.00),
(29, 8, 1, 36.00);

-- Pedido 30 (PED030) - Total: 84.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(30, 4, 3, 84.00);

-- Pedido 31 (PED031) - Total: 125.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(31, 2, 2, 76.00),
(31, 7, 1, 30.00);

-- Pedido 32 (PED032) - Total: 82.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(32, 5, 2, 44.00),
(32, 1, 1, 35.00);

-- Pedido 33 (PED033) - Total: 96.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(33, 8, 2, 72.00),
(33, 5, 1, 22.00);

-- Pedido 34 (PED034) - Total: 134.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(34, 3, 2, 84.00),
(34, 2, 1, 38.00);

-- Pedido 35 (PED035) - Total: 118.00
INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, sub_total) VALUES
(35, 1, 2, 70.00),
(35, 4, 2, 48.00);

-- ============================================
-- 11. INSERTAR PAGOS
-- ============================================

-- PAGOS MAYO 2025
INSERT INTO pago (id_pedido, monto_pago, metodo_pago, fecha_pago,estado_pago) VALUES
(1, 132.00, 'Plin', '2025-05-04','Pagado'),
(2, 88.00, 'Efectivo', '2025-05-11','Pagado'),
(3, 148.00, 'Tarjeta', '2025-05-17','Pagado'),
(4, 102.00, 'Yape', '2025-05-23','Pagado'),
(5, 120.00, 'Efectivo', '2025-05-29','Pagado');

-- PAGOS JUNIO 2025
INSERT INTO pago (id_pedido, monto_pago, metodo_pago, fecha_pago,estado_pago) VALUES
(6, 128.00, 'Tarjeta', '2025-06-05','Pagado'),
(7, 104.00, 'Efectivo', '2025-06-12','Pagado'),
(8, 116.00, 'Yape', '2025-06-18','Pagado'),
(9, 95.00, 'Efectivo', '2025-06-24','Pagado');

-- PAGOS JULIO 2025
INSERT INTO pago (id_pedido, monto_pago, metodo_pago, fecha_pago,estado_pago) VALUES
(10, 144.00, 'Tarjeta', '2025-07-02','Pagado'),
(11, 106.00, 'Efectivo', '2025-07-09','Pagado'),
(12, 92.00, 'Yape', '2025-07-15','Pagado'),
(13, 138.00, 'Efectivo', '2025-07-21','Pagado'),
(14, 110.00, 'Plin', '2025-07-28','Pagado');

-- PAGOS AGOSTO 2025
INSERT INTO pago (id_pedido, monto_pago, metodo_pago, fecha_pago,estado_pago) VALUES
(15, 112.00, 'Efectivo', '2025-08-03','Pagado'),
(16, 150.00, 'Yape', '2025-08-10','Pagado'),
(17, 98.00, 'Tarjeta', '2025-08-14','Pagado'),
(18, 126.00, 'Efectivo', '2025-08-20','Pagado'),
(19, 85.00, 'Plin', '2025-08-25','Pagado');

-- PAGOS SEPTIEMBRE 2025
INSERT INTO pago (id_pedido, monto_pago, metodo_pago, fecha_pago,estado_pago) VALUES
(20, 105.00, 'Efectivo', '2025-09-05','Pagado'),
(21, 88.00, 'Tarjeta', '2025-09-08','Pagado'),
(22, 142.00, 'Yape', '2025-09-12','Pagado'),
(23, 76.00, 'Efectivo', '2025-09-18','Pagado'),
(24, 130.00, 'Plin', '2025-09-22','Pagado'),
(25, 94.00, 'Tarjeta', '2025-09-25','Pagado');

-- PAGOS OCTUBRE 2025
INSERT INTO pago (id_pedido, monto_pago, metodo_pago, fecha_pago, estado_pago) VALUES
(26, 70.00, 'Efectivo', '2025-10-15','Pagado'),
(27, 110.00, 'Tarjeta', '2025-10-16','Pagado'),
(28, 156.00, 'Yape', '2025-10-17','Pagado'),
(30, 84.00, 'Efectivo', '2025-10-16','Pagado'),
(32, 82.00, 'Tarjeta', '2025-10-15','Pagado'),
(33, 96.00, 'Plin', '2025-10-16','Pagado'),
(35, 118.00, 'Efectivo', '2025-10-15','Pagado');
