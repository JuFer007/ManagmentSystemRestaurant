-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: restauranteDB
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `restauranteDB`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `restauranteDB` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `restauranteDB`;

--
-- Table structure for table `administrador`
--

DROP TABLE IF EXISTS `administrador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administrador` (
  `id_administrador` int NOT NULL AUTO_INCREMENT,
  `correo_electronico` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `id_empleado` int DEFAULT NULL,
  PRIMARY KEY (`id_administrador`),
  UNIQUE KEY `UKc1d97gbrme34h9f31q7x0ww5b` (`correo_electronico`),
  UNIQUE KEY `UK9vpilthttx8q8n9gvd2bsib60` (`telefono`),
  UNIQUE KEY `UK58iohaw0l5otmkenw2i4eitcb` (`id_empleado`),
  CONSTRAINT `FK4dwgd59k5nloxruj39222wq83` FOREIGN KEY (`id_empleado`) REFERENCES `empleado` (`id_empleado`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrador`
--

LOCK TABLES `administrador` WRITE;
/*!40000 ALTER TABLE `administrador` DISABLE KEYS */;
INSERT INTO `administrador` VALUES (1,'juan.lopez@restaurant.com','987654321',1);
/*!40000 ALTER TABLE `administrador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `id_cliente` int NOT NULL AUTO_INCREMENT,
  `apellidos_cliente` varchar(255) DEFAULT NULL,
  `dni_cliente` varchar(255) DEFAULT NULL,
  `nombre_cliente` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_cliente`),
  UNIQUE KEY `UKa1q8w70tgphpy2q8dbwg1rs6b` (`dni_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'Alarcon Manay','78901234','Marcelo'),(2,'Vega Cruz','78901235','Carmen'),(3,'Silva Rojas','78901236','Diego'),(4,'Ramos Campos','78901237','Patricia'),(5,'Núñez Ortiz','78901238','Fernando'),(6,'Chávez Luna','78901239','Gabriela'),(7,'Paredes Ríos','78901240','Javier'),(8,'Aguilar Soto','78901241','Valeria'),(9,'Mendoza Torres','78901242','Ricardo'),(10,'Castro Flores','78901243','Daniela'),(11,'Ruiz Gómez','78901244','Alberto'),(12,'Vasquez','76296919','Esmeralda'),(13,'User','12345678\'; DROP TABLE pedido; --','Test'),(14,'Test','12345678','<script>alert(\'XSS\')</script>');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cocinero`
--

DROP TABLE IF EXISTS `cocinero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cocinero` (
  `id_cocinero` int NOT NULL AUTO_INCREMENT,
  `especialidad` varchar(255) DEFAULT NULL,
  `id_empleado` int DEFAULT NULL,
  PRIMARY KEY (`id_cocinero`),
  UNIQUE KEY `UKxia4kyrfhr1rs7ygt1fmfnny` (`id_empleado`),
  CONSTRAINT `FKgng89i0biskwn3rxtayxcmad4` FOREIGN KEY (`id_empleado`) REFERENCES `empleado` (`id_empleado`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cocinero`
--

LOCK TABLES `cocinero` WRITE;
/*!40000 ALTER TABLE `cocinero` DISABLE KEYS */;
INSERT INTO `cocinero` VALUES (1,'Cocina Criolla',2),(2,'Cocina Marina',3),(3,'Parrilla y Carnes',7),(4,'Repostería',10);
/*!40000 ALTER TABLE `cocinero` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_pedido`
--

DROP TABLE IF EXISTS `detalle_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_pedido` (
  `id_detalle_pedido` int NOT NULL AUTO_INCREMENT,
  `cantidad` int NOT NULL,
  `sub_total` double NOT NULL,
  `id_pedido` int DEFAULT NULL,
  `id_plato` int DEFAULT NULL,
  PRIMARY KEY (`id_detalle_pedido`),
  KEY `FK7n9hdifr08joboojejveby1vr` (`id_pedido`),
  KEY `FK52pyn0i7xk82ufeflkox92rxw` (`id_plato`),
  CONSTRAINT `FK52pyn0i7xk82ufeflkox92rxw` FOREIGN KEY (`id_plato`) REFERENCES `plato` (`id_plato`),
  CONSTRAINT `FK7n9hdifr08joboojejveby1vr` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id_pedido`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_pedido`
--

LOCK TABLES `detalle_pedido` WRITE;
/*!40000 ALTER TABLE `detalle_pedido` DISABLE KEYS */;
INSERT INTO `detalle_pedido` VALUES (1,2,84,1,3),(2,1,28,1,4),(3,1,25,1,6),(4,4,88,2,5),(5,2,84,3,3),(6,1,36,3,8),(7,1,28,3,4),(8,2,76,4,2),(9,1,25,4,6),(10,4,120,5,7),(11,2,84,6,3),(12,2,44,6,5),(13,2,72,7,8),(14,1,28,7,4),(15,2,76,8,2),(16,1,36,8,8),(17,1,35,9,1),(18,2,60,9,7),(19,4,144,10,8),(20,2,76,11,2),(21,1,30,11,7),(22,2,70,12,1),(23,1,22,12,5),(24,2,84,13,3),(25,1,30,13,7),(26,1,22,13,5),(27,2,76,14,2),(28,1,28,14,4),(29,2,84,15,3),(30,1,28,15,4),(31,3,126,16,3),(32,1,22,16,5),(33,2,76,17,2),(34,1,22,17,5),(35,3,126,18,3),(36,1,35,19,1),(37,2,50,19,6),(38,2,76,20,2),(39,1,28,20,4),(40,2,84,21,3),(41,2,84,22,3),(42,1,35,22,1),(43,1,25,22,6),(44,2,76,23,2),(45,3,90,24,7),(46,1,36,24,8),(47,2,70,25,1),(48,1,22,25,5),(49,2,70,26,1),(50,2,76,27,2),(51,1,22,27,5),(52,1,12,27,6),(53,2,84,28,3),(54,2,72,28,7),(55,2,50,29,6),(56,1,36,29,8),(57,3,84,30,4),(58,2,76,31,2),(59,1,30,31,7),(60,2,44,32,5),(61,1,35,32,1),(62,2,72,33,8),(63,1,22,33,5),(64,2,84,34,3),(65,1,38,34,2),(66,2,70,35,1),(67,2,48,35,4);
/*!40000 ALTER TABLE `detalle_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleado`
--

DROP TABLE IF EXISTS `empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleado` (
  `id_empleado` int NOT NULL AUTO_INCREMENT,
  `apellido_materno_empleado` varchar(255) DEFAULT NULL,
  `apellido_paterno_empleado` varchar(255) DEFAULT NULL,
  `cargo_empleado` varchar(255) DEFAULT NULL,
  `codigo_empleado` varchar(255) DEFAULT NULL,
  `dni_empleado` varchar(255) DEFAULT NULL,
  `estado_empleado` varchar(255) DEFAULT NULL,
  `horas_trabajo` int NOT NULL,
  `nombre_empleado` varchar(255) DEFAULT NULL,
  `salario_empleado` double NOT NULL,
  PRIMARY KEY (`id_empleado`),
  UNIQUE KEY `UKo1w88vcnl64tpcjtqmd0edypf` (`codigo_empleado`),
  UNIQUE KEY `UKl9pjg3cmmy42kll85nmo1vuq5` (`dni_empleado`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleado`
--

LOCK TABLES `empleado` WRITE;
/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
INSERT INTO `empleado` VALUES (1,'García','López','Administrador','EMP001','45678901','Activo',160,'Juan',3500),(2,'Martínez','Sánchez','Cocinero','EMP002','45678902','Activo',160,'María',2500),(3,'Rodríguez','Fernández','Cocinero','EMP003','45678903','Activo',160,'Carlos',2500),(4,'Torres','Díaz','Mesero','EMP004','45678904','Activo',160,'Ana',1800),(5,'Ramírez','Vargas','Mesero','EMP005','45678905','Activo',160,'Luis',1800),(6,'Morales','Castro','Mesero','EMP006','45678906','Activo',160,'Sofía',1800),(7,'Gutiérrez','Flores','Cocinero','EMP007','45678907','Activo',160,'Pedro',2500),(8,'Mendoza','Herrera','Cajero','EMP008','45678908','Activo',160,'Laura',2000),(9,'Silva','Reyes','Mesero','EMP009','45678909','Activo',160,'Miguel',1800),(10,'Rojas','Vega','Cocinero','EMP010','45678910','Activo',160,'Carmen',2500);
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incidentes_sistema`
--

DROP TABLE IF EXISTS `incidentes_sistema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `incidentes_sistema` (
  `id_incidente` int NOT NULL AUTO_INCREMENT,
  `codigo_incidente` varchar(50) NOT NULL,
  `titulo` varchar(200) NOT NULL,
  `descripcion` text NOT NULL,
  `severidad` varchar(20) NOT NULL,
  `estado` varchar(30) NOT NULL DEFAULT 'Reportado',
  `fecha_reporte` datetime NOT NULL,
  `fecha_resolucion` datetime DEFAULT NULL,
  `reportado_por` varchar(100) DEFAULT NULL,
  `asignado_a` varchar(100) DEFAULT NULL,
  `modulo_afectado` varchar(100) DEFAULT NULL,
  `solucion_aplicada` text,
  `observaciones` varchar(1000) DEFAULT NULL,
  `requiere_cambio` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id_incidente`),
  UNIQUE KEY `codigo_incidente` (`codigo_incidente`),
  KEY `idx_codigo` (`codigo_incidente`),
  KEY `idx_estado` (`estado`),
  KEY `idx_severidad` (`severidad`),
  KEY `idx_asignado` (`asignado_a`),
  KEY `idx_fecha_reporte` (`fecha_reporte`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incidentes_sistema`
--

LOCK TABLES `incidentes_sistema` WRITE;
/*!40000 ALTER TABLE `incidentes_sistema` DISABLE KEYS */;
INSERT INTO `incidentes_sistema` VALUES (1,'INC-001','Lentitud en carga de pedidos','Los pedidos tardan más de 5 segundos en cargar','Media','Reportado','2025-12-05 12:52:02',NULL,'Usuario Admin',NULL,'Módulo de Pedidos',NULL,NULL,0),(2,'INC-002','Error al generar ticket PDF','No se puede generar el PDF del ticket en algunos casos','Alta','En Análisis','2025-12-05 12:52:02',NULL,'Usuario Mesero',NULL,'Servicio de Tickets',NULL,NULL,1);
/*!40000 ALTER TABLE `incidentes_sistema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesa`
--

DROP TABLE IF EXISTS `mesa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mesa` (
  `id_mesa` int NOT NULL AUTO_INCREMENT,
  `capacidad` int NOT NULL,
  `numero_mesa` int NOT NULL,
  PRIMARY KEY (`id_mesa`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesa`
--

LOCK TABLES `mesa` WRITE;
/*!40000 ALTER TABLE `mesa` DISABLE KEYS */;
INSERT INTO `mesa` VALUES (1,4,1),(2,6,2),(3,8,3),(4,4,4),(5,2,5),(6,4,6),(7,2,7),(8,6,8),(9,4,9),(10,8,10);
/*!40000 ALTER TABLE `mesa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesero`
--

DROP TABLE IF EXISTS `mesero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mesero` (
  `id_mesero` int NOT NULL AUTO_INCREMENT,
  `turno_trabajo` varchar(255) DEFAULT NULL,
  `id_empleado` int DEFAULT NULL,
  PRIMARY KEY (`id_mesero`),
  UNIQUE KEY `UKf2voa6u7stwuusk6x96msbtxk` (`id_empleado`),
  CONSTRAINT `FKf214wnsmgk3c1vxiekvi5d1dx` FOREIGN KEY (`id_empleado`) REFERENCES `empleado` (`id_empleado`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesero`
--

LOCK TABLES `mesero` WRITE;
/*!40000 ALTER TABLE `mesero` DISABLE KEYS */;
INSERT INTO `mesero` VALUES (1,'Mañana',4),(2,'Tarde',5),(3,'Noche',6),(4,'Mañana',9);
/*!40000 ALTER TABLE `mesero` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pago`
--

DROP TABLE IF EXISTS `pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pago` (
  `id_pago` int NOT NULL AUTO_INCREMENT,
  `estado_pago` varchar(255) DEFAULT NULL,
  `fecha_pago` date DEFAULT NULL,
  `metodo_pago` varchar(255) DEFAULT NULL,
  `monto_pago` double NOT NULL,
  `id_pedido` int DEFAULT NULL,
  PRIMARY KEY (`id_pago`),
  UNIQUE KEY `UKsmd7sl0godm04hw83kdt6ebwf` (`id_pedido`),
  CONSTRAINT `FKpddca3nqitclyep51ognpka70` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id_pedido`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pago`
--

LOCK TABLES `pago` WRITE;
/*!40000 ALTER TABLE `pago` DISABLE KEYS */;
INSERT INTO `pago` VALUES (1,'Pagado','2025-05-04','Plin',132,1),(2,'Pagado','2025-05-11','Efectivo',88,2),(3,'Pagado','2025-05-17','Tarjeta',148,3),(4,'Pagado','2025-05-23','Yape',102,4),(5,'Pagado','2025-05-29','Efectivo',120,5),(6,'Pagado','2025-06-05','Tarjeta',128,6),(7,'Pagado','2025-06-12','Efectivo',104,7),(8,'Pagado','2025-06-18','Yape',116,8),(9,'Pagado','2025-06-24','Efectivo',95,9),(10,'Pagado','2025-07-02','Tarjeta',144,10),(11,'Pagado','2025-07-09','Efectivo',106,11),(12,'Pagado','2025-07-15','Yape',92,12),(13,'Pagado','2025-07-21','Efectivo',138,13),(14,'Pagado','2025-07-28','Plin',110,14),(15,'Pagado','2025-08-03','Efectivo',112,15),(16,'Pagado','2025-08-10','Yape',150,16),(17,'Pagado','2025-08-14','Tarjeta',98,17),(18,'Pagado','2025-08-20','Efectivo',126,18),(19,'Pagado','2025-08-25','Plin',85,19),(20,'Pagado','2025-09-05','Efectivo',105,20),(21,'Pagado','2025-09-08','Tarjeta',88,21),(22,'Pagado','2025-09-12','Yape',142,22),(23,'Pagado','2025-09-18','Efectivo',76,23),(24,'Pagado','2025-09-22','Plin',130,24),(25,'Pagado','2025-09-25','Tarjeta',94,25),(26,'Pagado','2025-10-15','Efectivo',70,26),(27,'Pagado','2025-10-16','Tarjeta',110,27),(28,'Pagado','2025-10-17','Yape',156,28),(29,'Pagado','2025-10-16','Efectivo',84,30),(30,'Pagado','2025-10-15','Tarjeta',82,32),(31,'Pagado','2025-10-16','Plin',96,33),(32,'Pagado','2025-10-15','Efectivo',118,35);
/*!40000 ALTER TABLE `pago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido` (
  `id_pedido` int NOT NULL AUTO_INCREMENT,
  `codigo_pedido` varchar(255) DEFAULT NULL,
  `estado_pedido` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `total_pedido` double NOT NULL,
  `id_cliente` int DEFAULT NULL,
  `id_empleado` int DEFAULT NULL,
  `id_mesa` int DEFAULT NULL,
  PRIMARY KEY (`id_pedido`),
  KEY `FK9y4jnyp1hxqa386cnly0ay9uw` (`id_cliente`),
  KEY `FKqbl4adl78gxdv0wbx3nq03awv` (`id_empleado`),
  KEY `FK6vxat0l7c1d0enyrlij0fiyks` (`id_mesa`),
  CONSTRAINT `FK6vxat0l7c1d0enyrlij0fiyks` FOREIGN KEY (`id_mesa`) REFERENCES `mesa` (`id_mesa`),
  CONSTRAINT `FK9y4jnyp1hxqa386cnly0ay9uw` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`),
  CONSTRAINT `FKqbl4adl78gxdv0wbx3nq03awv` FOREIGN KEY (`id_empleado`) REFERENCES `empleado` (`id_empleado`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES (1,'PED001','Completado','2025-05-04',132,1,4,1),(2,'PED002','Completado','2025-05-11',88,2,5,2),(3,'PED003','Completado','2025-05-17',148,3,6,3),(4,'PED004','Completado','2025-05-23',102,5,4,5),(5,'PED005','Completado','2025-05-29',120,7,5,7),(6,'PED006','Completado','2025-06-05',128,4,6,4),(7,'PED007','Completado','2025-06-12',104,6,4,6),(8,'PED008','Completado','2025-06-18',116,8,5,8),(9,'PED009','Completado','2025-06-24',95,10,6,10),(10,'PED010','Completado','2025-07-02',144,2,4,2),(11,'PED011','Completado','2025-07-09',106,3,5,3),(12,'PED012','Completado','2025-07-15',92,5,6,5),(13,'PED013','Completado','2025-07-21',138,7,4,7),(14,'PED014','Completado','2025-07-28',110,9,5,9),(15,'PED015','Completado','2025-08-03',112,4,6,4),(16,'PED016','Completado','2025-08-10',150,6,4,6),(17,'PED017','Completado','2025-08-14',98,8,5,8),(18,'PED018','Completado','2025-08-20',126,10,6,10),(19,'PED019','Completado','2025-08-25',85,11,4,1),(20,'PED020','Completado','2025-09-05',105,1,5,1),(21,'PED021','Completado','2025-09-08',88,3,6,3),(22,'PED022','Completado','2025-09-12',142,5,4,5),(23,'PED023','Completado','2025-09-18',76,7,5,7),(24,'PED024','Completado','2025-09-22',130,2,6,2),(25,'PED025','Completado','2025-09-25',94,9,4,9),(26,'PED026','Completado','2025-10-15',70,1,5,1),(27,'PED027','Completado','2025-10-16',110,2,6,2),(28,'PED028','En Proceso','2025-10-17',156,3,4,3),(29,'PED029','En Proceso','2025-10-17',90,4,5,4),(30,'PED030','Completado','2025-10-16',84,5,6,5),(31,'PED031','En Proceso','2025-10-17',125,6,4,6),(32,'PED032','Completado','2025-10-15',82,7,5,7),(33,'PED033','Completado','2025-10-16',96,8,6,8),(34,'PED034','En Proceso','2025-10-17',134,9,4,9),(35,'PED035','Completado','2025-10-15',118,10,5,10);
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plato`
--

DROP TABLE IF EXISTS `plato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plato` (
  `id_plato` int NOT NULL AUTO_INCREMENT,
  `disponibilidad` varchar(255) DEFAULT NULL,
  `imagenurl` varchar(255) DEFAULT NULL,
  `nombre_plato` varchar(255) NOT NULL,
  `precio_plato` double DEFAULT NULL,
  PRIMARY KEY (`id_plato`),
  UNIQUE KEY `UKt2fsfs83r56htnkigpo20kugt` (`nombre_plato`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plato`
--

LOCK TABLES `plato` WRITE;
/*!40000 ALTER TABLE `plato` DISABLE KEYS */;
INSERT INTO `plato` VALUES (1,'Disponible','imagenesPlatos/ceviche_de_pescado.jpg','Ceviche de Pescado',35),(2,'Disponible','imagenesPlatos/lomo_saltado.jpg','Lomo Saltado',38),(3,'Disponible','imagenesPlatos/arroz_con_mariscos.jpg','Arroz con Mariscos',42),(4,'Disponible','imagenesPlatos/ají_de_gallina.jpg','Ají de Gallina',28),(5,'Disponible','imagenesPlatos/causa_limeña.jpg','Causa Limeña',22),(6,'Disponible','imagenesPlatos/anticuchos.jpg','Anticuchos',25),(7,'Disponible','imagenesPlatos/tacu_tacu.jpeg','Tacu Tacu',30),(8,'Disponible','imagenesPlatos/chicharrón_pescado.jpeg','Chicharrón de Pescado',36);
/*!40000 ALTER TABLE `plato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registro_mantenimiento`
--

DROP TABLE IF EXISTS `registro_mantenimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registro_mantenimiento` (
  `id_registro` int NOT NULL AUTO_INCREMENT,
  `tipo_mantenimiento` varchar(50) NOT NULL,
  `descripcion` text NOT NULL,
  `fecha_inicio` datetime NOT NULL,
  `fecha_fin` datetime DEFAULT NULL,
  `estado` varchar(30) NOT NULL DEFAULT 'Pendiente',
  `responsable` varchar(100) DEFAULT NULL,
  `observaciones` varchar(1000) DEFAULT NULL,
  `componente_afectado` varchar(100) DEFAULT NULL,
  `prioridad` varchar(20) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  PRIMARY KEY (`id_registro`),
  KEY `idx_tipo` (`tipo_mantenimiento`),
  KEY `idx_estado` (`estado`),
  KEY `idx_responsable` (`responsable`),
  KEY `idx_fecha_registro` (`fecha_registro`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registro_mantenimiento`
--

LOCK TABLES `registro_mantenimiento` WRITE;
/*!40000 ALTER TABLE `registro_mantenimiento` DISABLE KEYS */;
INSERT INTO `registro_mantenimiento` VALUES (1,'Preventivo','Backup automático diario de base de datos','2025-12-05 12:52:02',NULL,'Completado','Sistema',NULL,'Base de Datos','Alta','2025-12-05 12:52:02'),(2,'Preventivo','Limpieza de archivos temporales','2025-12-05 12:52:02',NULL,'Completado','Sistema',NULL,'Servidor','Media','2025-12-05 12:52:02'),(3,'Correctivo','Reparación de índices de base de datos','2025-12-05 12:52:02',NULL,'Pendiente','DBA',NULL,'Base de Datos','Alta','2025-12-05 12:52:02');
/*!40000 ALTER TABLE `registro_mantenimiento` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_completar_mantenimiento` BEFORE UPDATE ON `registro_mantenimiento` FOR EACH ROW BEGIN
    IF NEW.estado = 'Completado' AND OLD.estado != 'Completado' THEN
        SET NEW.fecha_fin = NOW();
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `solicitud_cambio`
--

DROP TABLE IF EXISTS `solicitud_cambio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `solicitud_cambio` (
  `id_solicitud` int NOT NULL AUTO_INCREMENT,
  `codigo_solicitud` varchar(50) NOT NULL,
  `solicitante` varchar(100) NOT NULL,
  `fecha_solicitud` date NOT NULL,
  `motivo` varchar(50) NOT NULL,
  `descripcion_cambio` text NOT NULL,
  `desarrolladores_acargo` varchar(200) DEFAULT NULL,
  `fecha_despliегue_propuesta` date DEFAULT NULL,
  `fecha_despliegue` date DEFAULT NULL,
  `estado` varchar(30) NOT NULL DEFAULT 'Solicitado',
  `prioridad` varchar(20) DEFAULT NULL,
  `artefactos_afectados` varchar(1000) DEFAULT NULL,
  `servidores_utilizados` varchar(500) DEFAULT NULL,
  `operador_acargo` varchar(100) DEFAULT NULL,
  `requiere_revertir` tinyint(1) DEFAULT '0',
  `incidentes_registrados_tras_depliegue` int DEFAULT '0',
  `procedimiento_realizar` text,
  `observaciones` varchar(1000) DEFAULT NULL,
  `version_documento` varchar(20) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  PRIMARY KEY (`id_solicitud`),
  UNIQUE KEY `codigo_solicitud` (`codigo_solicitud`),
  KEY `idx_codigo` (`codigo_solicitud`),
  KEY `idx_estado` (`estado`),
  KEY `idx_prioridad` (`prioridad`),
  KEY `idx_solicitante` (`solicitante`),
  KEY `idx_fecha_solicitud` (`fecha_solicitud`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitud_cambio`
--

LOCK TABLES `solicitud_cambio` WRITE;
/*!40000 ALTER TABLE `solicitud_cambio` DISABLE KEYS */;
INSERT INTO `solicitud_cambio` VALUES (1,'SOL-2025-001','Admin Sistema','2025-12-05','Mejora','Implementar módulo de mantenimiento con registro de incidentes y solicitudes de cambio',NULL,NULL,NULL,'En Desarrollo','Alta',NULL,NULL,NULL,0,0,NULL,NULL,'1.0','2025-12-05 12:52:02'),(2,'SOL-2025-002','Gerente TI','2025-12-05','Nueva Funcionalidad','Agregar reportes de ventas por periodo personalizado',NULL,NULL,NULL,'Solicitado','Media',NULL,NULL,NULL,0,0,NULL,NULL,'1.0','2025-12-05 12:52:02');
/*!40000 ALTER TABLE `solicitud_cambio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `contraseña_usuario` varchar(255) DEFAULT NULL,
  `nombre_usuario` varchar(255) DEFAULT NULL,
  `id_empleado` int NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `UK9na7lny3owcia98799gx6c2af` (`id_empleado`),
  UNIQUE KEY `UKpuhr3k3l7bj71hb7hk7ktpxn0` (`nombre_usuario`),
  CONSTRAINT `FK8cp9xlcvihvrsry0pj7wrawfc` FOREIGN KEY (`id_empleado`) REFERENCES `empleado` (`id_empleado`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'admin123','juan.lopez',1),(2,'cocina123','maria.sanchez',2),(3,'cocina123','carlos.fernandez',3),(4,'mesero123','ana.diaz',4),(5,'mesero123','luis.vargas',5),(6,'mesero123','sofia.castro',6),(7,'cocina123','pedro.flores',7),(8,'cajero123','laura.herrera',8);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `v_incidentes_criticos_abiertos`
--

DROP TABLE IF EXISTS `v_incidentes_criticos_abiertos`;
/*!50001 DROP VIEW IF EXISTS `v_incidentes_criticos_abiertos`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_incidentes_criticos_abiertos` AS SELECT 
 1 AS `codigo_incidente`,
 1 AS `titulo`,
 1 AS `severidad`,
 1 AS `estado`,
 1 AS `fecha_reporte`,
 1 AS `asignado_a`,
 1 AS `modulo_afectado`,
 1 AS `dias_abierto`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_mantenimientos_pendientes`
--

DROP TABLE IF EXISTS `v_mantenimientos_pendientes`;
/*!50001 DROP VIEW IF EXISTS `v_mantenimientos_pendientes`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_mantenimientos_pendientes` AS SELECT 
 1 AS `id_registro`,
 1 AS `tipo_mantenimiento`,
 1 AS `descripcion`,
 1 AS `fecha_inicio`,
 1 AS `responsable`,
 1 AS `prioridad`,
 1 AS `dias_pendiente`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_solicitudes_pendientes_aprobacion`
--

DROP TABLE IF EXISTS `v_solicitudes_pendientes_aprobacion`;
/*!50001 DROP VIEW IF EXISTS `v_solicitudes_pendientes_aprobacion`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_solicitudes_pendientes_aprobacion` AS SELECT 
 1 AS `codigo_solicitud`,
 1 AS `solicitante`,
 1 AS `fecha_solicitud`,
 1 AS `motivo`,
 1 AS `descripcion_cambio`,
 1 AS `prioridad`,
 1 AS `dias_esperando`*/;
SET character_set_client = @saved_cs_client;

--
-- Current Database: `restauranteDB`
--

USE `restauranteDB`;

--
-- Final view structure for view `v_incidentes_criticos_abiertos`
--

/*!50001 DROP VIEW IF EXISTS `v_incidentes_criticos_abiertos`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_incidentes_criticos_abiertos` AS select `incidentes_sistema`.`codigo_incidente` AS `codigo_incidente`,`incidentes_sistema`.`titulo` AS `titulo`,`incidentes_sistema`.`severidad` AS `severidad`,`incidentes_sistema`.`estado` AS `estado`,`incidentes_sistema`.`fecha_reporte` AS `fecha_reporte`,`incidentes_sistema`.`asignado_a` AS `asignado_a`,`incidentes_sistema`.`modulo_afectado` AS `modulo_afectado`,(to_days(now()) - to_days(`incidentes_sistema`.`fecha_reporte`)) AS `dias_abierto` from `incidentes_sistema` where ((`incidentes_sistema`.`estado` not in ('Resuelto','Cerrado')) and (`incidentes_sistema`.`severidad` in ('Alta','Crítica'))) order by `incidentes_sistema`.`severidad` desc,`incidentes_sistema`.`fecha_reporte` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_mantenimientos_pendientes`
--

/*!50001 DROP VIEW IF EXISTS `v_mantenimientos_pendientes`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_mantenimientos_pendientes` AS select `registro_mantenimiento`.`id_registro` AS `id_registro`,`registro_mantenimiento`.`tipo_mantenimiento` AS `tipo_mantenimiento`,`registro_mantenimiento`.`descripcion` AS `descripcion`,`registro_mantenimiento`.`fecha_inicio` AS `fecha_inicio`,`registro_mantenimiento`.`responsable` AS `responsable`,`registro_mantenimiento`.`prioridad` AS `prioridad`,(to_days(now()) - to_days(`registro_mantenimiento`.`fecha_inicio`)) AS `dias_pendiente` from `registro_mantenimiento` where (`registro_mantenimiento`.`estado` in ('Pendiente','En Proceso')) order by `registro_mantenimiento`.`prioridad` desc,`registro_mantenimiento`.`fecha_inicio` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_solicitudes_pendientes_aprobacion`
--

/*!50001 DROP VIEW IF EXISTS `v_solicitudes_pendientes_aprobacion`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_solicitudes_pendientes_aprobacion` AS select `solicitud_cambio`.`codigo_solicitud` AS `codigo_solicitud`,`solicitud_cambio`.`solicitante` AS `solicitante`,`solicitud_cambio`.`fecha_solicitud` AS `fecha_solicitud`,`solicitud_cambio`.`motivo` AS `motivo`,`solicitud_cambio`.`descripcion_cambio` AS `descripcion_cambio`,`solicitud_cambio`.`prioridad` AS `prioridad`,(to_days(now()) - to_days(`solicitud_cambio`.`fecha_solicitud`)) AS `dias_esperando` from `solicitud_cambio` where (`solicitud_cambio`.`estado` = 'Solicitado') order by `solicitud_cambio`.`prioridad` desc,`solicitud_cambio`.`fecha_solicitud` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-12  0:10:15
