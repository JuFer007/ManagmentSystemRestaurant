# Plan de Despliegue - Sistema de Restaurante Marakos Grill

## 1. Información General

| Campo | Valor |
|-------|-------|
| **Nombre del Sistema** | Sistema de Gestión Restaurante |
| **Versión** | 1.0.0 |
| **Fecha de Despliegue** | 18/12/2025 |
| **Responsable** | Equipo de Desarrollo |
| **Aprobado por** | Gerencia TI |

---

## 2. Artefactos a Desplegar

### 2.1 Aplicación Principal (Backend)

| Artefacto | Archivo | Ubicación Destino |
|-----------|---------|-------------------|
| JAR ejecutable | `SystemRestaurant-1.0.0.jar` | `/opt/restaurant-app/` |
| Configuración | `application.properties` | `/opt/restaurant-app/config/` |
| Scripts inicio | `start-app.sh` | `/opt/restaurant-app/scripts/` |

### 2.2 Servidor de Tickets (Node.js)

| Artefacto | Directorio | Ubicación Destino |
|-----------|-----------|-------------------|
| Servidor Node | `serverNode/` | `/opt/ticket-service/` |
| Dependencias | `node_modules/` | `/opt/ticket-service/node_modules/` |
| Templates | `templates/` | `/opt/ticket-service/templates/` |

### 2.3 Base de Datos

| Componente | Archivo | Ubicación |
|------------|---------|-----------|
| Schema inicial | `schema.sql` | `/opt/db-scripts/` |
| Datos iniciales | `data.sql` | `/opt/db-scripts/` |
| Backup actual | `backup_pre_deploy.sql` | `/opt/backups/` |

---

## 3. Requisitos Previos

### 3.1 Software Requerido

#### Servidor de Aplicación
```bash
# Java JDK 17 o superior
sudo apt update
sudo apt install openjdk-17-jdk

# Verificar instalación
java --version
```

#### Servidor Node.js
```bash
# Node.js v18+
curl -fsSL https://deb.nodesource.com/setup_18.x | sudo -E bash -
sudo apt install -y nodejs

# Verificar
node --version
npm --version
```

#### Base de Datos MySQL
```bash
# MySQL 8.0
sudo apt install mysql-server-8.0

# Configurar
sudo mysql_secure_installation
```

### 3.2 Recursos del Servidor

| Componente | Mínimo | Recomendado |
|------------|--------|-------------|
| CPU | 2 cores | 4 cores |
| RAM | 4 GB | 8 GB |
| Disco | 20 GB | 50 GB |
| Red | 100 Mbps | 1 Gbps |

---

## 4. Procedimiento de Despliegue

### FASE 1: Preparación (30 min)

#### 4.1 Backup de Datos
```bash
# Crear backup de BD actual
mysqldump -u root -p restaurant_db > /opt/backups/backup_$(date +%Y%m%d_%H%M%S).sql

# Verificar backup
ls -lh /opt/backups/
```

#### 4.2 Detener Servicios
```bash
# Detener aplicación actual (si existe)
sudo systemctl stop restaurant-app

# Detener servidor de tickets
sudo systemctl stop ticket-service
```

---

### FASE 2: Despliegue de Base de Datos (15 min)

#### 4.3 Crear Base de Datos
```sql
-- Conectar a MySQL
mysql -u root -p

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS restaurant_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Crear usuario
CREATE USER IF NOT EXISTS 'restaurant_user'@'localhost' IDENTIFIED BY 'SecurePassword123!';

-- Otorgar permisos
GRANT ALL PRIVILEGES ON restaurant_db.* TO 'restaurant_user'@'localhost';
FLUSH PRIVILEGES;

-- Salir
EXIT;
```

#### 4.4 Ejecutar Scripts
```bash
# Ejecutar schema
mysql -u restaurant_user -p restaurant_db < /opt/db-scripts/schema.sql

# Cargar datos iniciales
mysql -u restaurant_user -p restaurant_db < /opt/db-scripts/data.sql
```

---

### FASE 3: Despliegue de Aplicación Backend (20 min)

#### 4.5 Copiar Artefactos
```bash
# Crear directorios
sudo mkdir -p /opt/restaurant-app/config
sudo mkdir -p /opt/restaurant-app/logs
sudo mkdir -p /opt/restaurant-app/imagenesPlatos

# Copiar JAR
sudo cp target/SystemRestaurant-1.0.0.jar /opt/restaurant-app/

# Copiar configuración
sudo cp src/main/resources/application.properties /opt/restaurant-app/config/
```

#### 4.6 Configurar application.properties
```properties
# /opt/restaurant-app/config/application.properties
server.port=8080

# Base de Datos
spring.datasource.url=jdbc:mysql://localhost:3306/restaurant_db
spring.datasource.username=restaurant_user
spring.datasource.password=SecurePassword123!

# JPA
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=false

# Logs
logging.file.name=/opt/restaurant-app/logs/application.log
logging.level.root=INFO
```

#### 4.7 Crear Servicio Systemd
```bash
# Crear archivo de servicio
sudo nano /etc/systemd/system/restaurant-app.service
```

Contenido:
```ini
[Unit]
Description=Restaurant Management System
After=mysql.service

[Service]
Type=simple
User=appuser
WorkingDirectory=/opt/restaurant-app
ExecStart=/usr/bin/java -jar /opt/restaurant-app/SystemRestaurant-1.0.0.jar --spring.config.location=/opt/restaurant-app/config/application.properties
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```
```bash
# Recargar systemd
sudo systemctl daemon-reload

# Habilitar servicio
sudo systemctl enable restaurant-app
```

---

### FASE 4: Despliegue de Servidor de Tickets (15 min)

#### 4.8 Instalar Dependencias Node
```bash
# Copiar código
sudo cp -r serverNode /opt/ticket-service

# Instalar dependencias
cd /opt/ticket-service
sudo npm install --production
```

#### 4.9 Crear Servicio para Node
```bash
sudo nano /etc/systemd/system/ticket-service.service
```

Contenido:
```ini
[Unit]
Description=PDF Ticket Generator Service
After=network.target

[Service]
Type=simple
User=appuser
WorkingDirectory=/opt/ticket-service
ExecStart=/usr/bin/node server.js
Restart=always
RestartSec=10
Environment=PORT=3000

[Install]
WantedBy=multi-user.target
```
```bash
sudo systemctl daemon-reload
sudo systemctl enable ticket-service
```

---

### FASE 5: Inicio de Servicios (10 min)

#### 4.10 Iniciar Servicios
```bash
# Iniciar servidor de tickets
sudo systemctl start ticket-service
sudo systemctl status ticket-service

# Iniciar aplicación
sudo systemctl start restaurant-app
sudo systemctl status restaurant-app
```

#### 4.11 Verificar Logs
```bash
# Logs aplicación
sudo tail -f /opt/restaurant-app/logs/application.log

# Logs servicio
sudo journalctl -u restaurant-app -f
```

---

### FASE 6: Pruebas Post-Despliegue (30 min)

#### 4.12 Pruebas de Conectividad
```bash
# Verificar puerto 8080
curl http://localhost:8080/login

# Verificar servicio de tickets
curl http://localhost:3000/health
```

#### 4.13 Pruebas Funcionales

**Test 1: Login**
```bash
URL: http://localhost:8080/login
Usuario: admin
Contraseña: 1234
Resultado Esperado: Acceso concedido
```

**Test 2: Crear Pedido**
```bash
1. Navegar a "Nuevo Pedido"
2. Agregar cliente
3. Seleccionar mesa
4. Agregar platos
5. Finalizar pedido
Resultado Esperado: Pedido creado exitosamente
```

**Test 3: Generar Ticket**
```bash
1. Ir a "Gestión de Pagos"
2. Seleccionar pedido completado
3. Click en "Ver Ticket"
Resultado Esperado: PDF generado y mostrado
```

#### 4.14 Pruebas de Carga (Opcional)
```bash
# Instalar Apache Bench
sudo apt install apache2-utils

# Prueba de carga simple
ab -n 1000 -c 10 http://localhost:8080/system/platos/listar
```

---

## 5. Diagrama de Arquitectura de Despliegue
```
┌─────────────────────────────────────────────────────┐
│                   SERVIDOR LINUX                     │
│  ┌───────────────────────────────────────────────┐  │
│  │         Aplicación Spring Boot                │  │
│  │            Puerto: 8080                       │  │
│  │    /opt/restaurant-app/                       │  │
│  └───────────────┬───────────────────────────────┘  │
│                  │                                   │
│  ┌───────────────▼───────────────────────────────┐  │
│  │      Servidor Node.js (Tickets)               │  │
│  │            Puerto: 3000                       │  │
│  │    /opt/ticket-service/                       │  │
│  └───────────────┬───────────────────────────────┘  │
│                  │                                   │
│  ┌───────────────▼───────────────────────────────┐  │
│  │         Base de Datos MySQL                   │  │
│  │            Puerto: 3306                       │  │
│  │         restaurant_db                         │  │
│  └───────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────┘
           ▲
           │ HTTPS (443)
           │
    ┌──────┴──────┐
    │   Cliente   │
    │ (Navegador) │
    └─────────────┘
```

---

## 6. Plan de Rollback

### Si ocurre un error crítico:

#### 6.1 Detener Servicios
```bash
sudo systemctl stop restaurant-app
sudo systemctl stop ticket-service
```

#### 6.2 Restaurar Base de Datos
```bash
mysql -u root -p restaurant_db < /opt/backups/backup_pre_deploy.sql
```

#### 6.3 Restaurar Versión Anterior
```bash
sudo cp /opt/restaurant-app/backup/SystemRestaurant-0.9.0.jar /opt/restaurant-app/SystemRestaurant-1.0.0.jar
```

#### 6.4 Reiniciar Servicios
```bash
sudo systemctl start ticket-service
sudo systemctl start restaurant-app
```

---

## 7. Checklist de Despliegue

### Pre-Despliegue
- [ ] Backup de base de datos realizado
- [ ] Backup de aplicación actual
- [ ] Notificación a usuarios del mantenimiento
- [ ] Verificación de espacio en disco
- [ ] Verificación de dependencias instaladas

### Durante Despliegue
- [ ] Base de datos creada
- [ ] Scripts ejecutados correctamente
- [ ] Aplicación desplegada
- [ ] Servidor Node desplegado
- [ ] Servicios iniciados

### Post-Despliegue
- [ ] Prueba de login exitosa
- [ ] Creación de pedido funcional
- [ ] Generación de ticket funcional
- [ ] Logs sin errores críticos
- [ ] Monitoreo activado
- [ ] Notificación de finalización

---

## 8. Contactos de Soporte

| Rol | Nombre | Contacto |
|-----|--------|----------|
| Desarrollador Lead | Cristian Huaman | cristian.huaman@utp.edu.pe |
| Desarrollador | Junior Zumaeta | junior.zumaeta@utp.edu.pe |
| DBA | [Nombre] | dba@restaurant.com |
| Sysadmin | [Nombre] | sysadmin@restaurant.com |

---

**Fecha de Elaboración**: 15/12/2025  
**Versión del Documento**: 1.0  
**Próxima Revisión**: 15/01/2026