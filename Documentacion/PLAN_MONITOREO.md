# Plan de Monitoreo - Sistema de Restaurante Marakos Grill

## 1. Objetivo del Plan

Establecer un sistema de monitoreo integral que permita:
- Detectar problemas antes de que afecten a los usuarios
- Mantener la disponibilidad del sistema
- Optimizar el rendimiento
- Facilitar la toma de decisiones

---

## 2. Elementos a Monitorear

### 2.1 Hardware (Servidor)

| ID | Recurso | Métrica | Umbral Alerta | Periodicidad |
|----|---------|---------|---------------|--------------|
| HW-01 | CPU | Uso promedio | >80% por 5 min | Cada 5 min |
| HW-02 | Memoria RAM | Uso total | >85% | Cada 5 min |
| HW-03 | Disco Duro | Espacio libre | <15% libre | Cada 10 min |
| HW-04 | Red | Tráfico | >90% capacidad | Cada 5 min |
| HW-05 | Temperatura | °C del CPU | >75°C | Cada 10 min |

### 2.2 Software (Aplicaciones)

| ID | Componente | Métrica | Umbral Alerta | Acción |
|----|------------|---------|---------------|--------|
| SW-01 | App Spring Boot | Puerto 8080 activo | Caído | Reiniciar servicio |
| SW-02 | Servidor Node | Puerto 3000 activo | Caído | Reiniciar servicio |
| SW-03 | MySQL | Puerto 3306 activo | Caído | Notificar DBA |
| SW-04 | JVM Heap | Uso memoria | >80% | Registrar log |
| SW-05 | Threads | Número activo | >200 | Registrar log |
| SW-06 | Conexiones BD | Pool activo | >80 | Optimizar queries |

### 2.3 Actividad Operativa

| ID | Evento | Acción | Registro |
|----|--------|--------|----------|
| OP-01 | Inicio sesión exitoso | Registrar | /logs/access.log |
| OP-02 | Inicio sesión fallido | Alerta después de 3 intentos | /logs/security.log |
| OP-03 | Creación de pedido | Registrar | /logs/business.log |
| OP-04 | Generación ticket | Verificar éxito/fallo | /logs/ticket.log |
| OP-05 | Error aplicación | Enviar notificación | /logs/error.log |
| OP-06 | Backup ejecutado | Verificar éxito | /logs/maintenance.log |

---

## 3. Herramientas de Monitoreo

### 3.1 Spring Boot Actuator (Ya implementado)
```properties
# application.properties
management.endpoints.web.exposure.include=health,metrics,info
management.endpoint.health.show-details=always
management.metrics.enable.jvm=true
management.metrics.enable.system=true
```

**Endpoints disponibles**:
- `http://localhost:8080/actuator/health` - Estado general
- `http://localhost:8080/actuator/metrics` - Métricas del sistema
- `http://localhost:8080/actuator/info` - Información de la app
