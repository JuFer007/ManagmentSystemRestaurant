## 7. Procedimiento de Respuesta a Incidentes

### Escenario 1: Aplicación Caída
````
1. DETECCIÓN: Alerta automática vía monitor
2. VERIFICACIÓN: 
   - systemctl status restaurant-app
   - tail -f /opt/restaurant-app/logs/application.log
3. ACCIÓN:
   - systemctl restart restaurant-app
   - Verificar logs de error
4. ESCALAMIENTO: Si no se recupera en 5 min, contactar desarrollador
````

### Escenario 2: Alto Uso de CPU
````
1. VERIFICAR: top -c (identificar proceso)
2. ANÁLISIS: 
   - Revisar logs de aplicación
   - Verificar número de usuarios concurrentes
3. ACCIÓN:
   - Si es temporal: Esperar y monitorear
   - Si persiste: Reiniciar aplicación
   - Si continúa: Escalar servidor
````

### Escenario 3: Base de Datos Lenta
````
1. VERIFICAR: 
   - SHOW PROCESSLIST en MySQL
   - Identificar queries lentas
2. ANÁLISIS:
   - Revisar slow query log
   - Verificar índices
3. ACCIÓN:
   - Optimizar queries problemáticas
   - Agregar índices si es necesario
   - Considerar caché
````

---

## 8. Métricas Clave (KPIs)

| Métrica | Objetivo | Actual | Estado |
|---------|----------|--------|--------|
| Uptime mensual | >99.5% | 99.8% | ✅ |
| Tiempo respuesta promedio | <2s | 1.5s | ✅ |
| Errores por día | <5 | 2 | ✅ |
| Tickets fallidos | <1% | 0.5% | ✅ |
| Disponibilidad BD | 100% | 100% | ✅ |

---

## 9. Revisión y Actualización

Este plan debe revisarse:
- **Mensualmente**: Revisar métricas y ajustar umbrales
- **Trimestralmente**: Evaluar nuevas herramientas
- **Anualmente**: Auditoría completa del sistema de monitoreo

---

**Elaborado por**: Equipo de Desarrollo  
**Fecha**: 16/12/2025  
**Versión**: 1.0  
**Próxima revisión**: 16/01/2026
````

---

## 4️⃣ PLAN DE MANTENIMIENTO COMPLETO (Sesión 17)

### 📄 **Nuevo archivo: `docs/PLAN_MANTENIMIENTO.md`**
````markdown
# Plan de Mantenimiento - Sistema de Restaurante Marakos Grill

## 1. Objetivo

Garantizar la operación continua, confiable y eficiente del sistema mediante actividades de mantenimiento preventivo, correctivo, adaptativo y perfectivo.

---

## 2. Tipos de Mantenimiento

### 2.1 Mantenimiento Preventivo

**Objetivo**: Prevenir fallos antes de que ocurran

| Actividad | Frecuencia | Responsable | Duración |
|-----------|------------|-------------|----------|
| Backup de BD | Diario (3:00 AM) | Sistema (Automático) | 15 min |
| Limpieza de logs antiguos | Semanal (Domingos 4:00 AM) | Sistema (Automático) | 30 min |
| Limpieza archivos temp | Diario (3:00 AM) | Sistema (Automático) | 10 min |
| Actualizar dependencias | Mensual | Desarrollador | 2 horas |
| Revisar seguridad | Mensual | Desarrollador | 4 horas |
| Optimizar base de datos | Mensual | DBA | 2 horas |

### 2.2 Mantenimiento Correctivo

**Objetivo**: Corregir errores reportados

| Tipo Error | Prioridad | Tiempo Máximo Respuesta |
|------------|-----------|------------------------|
| Sistema caído | Crítica | Inmediato |
| Función principal rota | Alta | 4 horas |
| Error no crítico | Media | 24 horas |
| Mejora UX | Baja | 1 semana |

### 2.3 Mantenimiento Adaptativo

**Objetivo**: Adaptar el sistema a cambios en el entorno

Ejemplos:
- Actualización de Java 17 a Java 21
- Migración a MySQL 9.0
- Cambios en regulaciones (emisión de comprobantes)

### 2.4 Mantenimiento Perfectivo

**Objetivo**: Mejorar funcionalidades existentes

Ejemplos:
- Optimizar velocidad de carga
- Mejorar interfaz de usuario
- Agregar reportes adicionales

---

## 3. Actividades de Mantenimiento Preventivo

### 3.1 Backups Automáticos

**Ya implementado en tu código**: `BackupService.java`

#### Verificar funcionamiento:
```bash
# Ver backups generados
ls -lh /opt/restaurant-app/backups/

# Verificar último backup
ls -lt /opt/restaurant-app/backups/ | head -2
```

#### Configuración adicional en crontab:
```bash
crontab -e

# Backup diario a las 3 AM
0 3 * * * /opt/restaurant-app/scripts/backup-wrapper.sh
```

**Crear script**: `/opt/restaurant-app/scripts/backup-wrapper.sh`
```bash
#!/bin/bash

LOG_FILE="/opt/restaurant-app/logs/backup.log"

echo "$(date) - Iniciando backup..." >> $LOG_FILE

# Llamar al endpoint de backup
curl -X POST http://localhost:8080/system/maintenance/backup >> $LOG_FILE 2>&1

if [ $? -eq 0 ]; then
    echo "$(date) - Backup completado exitosamente" >> $LOG_FILE
else
    echo "$(date) - ERROR en backup" >> $LOG_FILE
    echo "Backup falló" | mail -s "ERROR BACKUP" admin@restaurant.com
fi
```

---

### 3.2 Limpieza Automática

**Ya implementado**: `CleanupService.java`

Verificar que los cron jobs estén configurados:
```java
// En CleanupService.java
@Scheduled(cron = "0 0 3 * * ?")  // Diario 3 AM
public void limpiarArchivosTempales()

@Scheduled(cron = "0 0 4 ? * SUN")  // Domingos 4 AM
public void limpiarLogsAntiguos()
```

---

### 3.3 Sincronización de Hora (NTP)
```bash
# Instalar NTP
sudo apt install ntp

# Configurar servidor NTP
sudo nano /etc/ntp.conf

# Agregar servidores peruanos
server 0.pe.pool.ntp.org
server 1.pe.pool.ntp.org
server 2.pe.pool.ntp.org

# Reiniciar servicio
sudo systemctl restart ntp

# Verificar sincronización
ntpq -p
```

---

### 3.4 Optimización de Base de Datos

**Script mensual**: `/opt/restaurant-app/scripts/optimize-db.sh`
```bash
#!/bin/bash

DB_USER="restaurant_user"
DB_PASS="SecurePassword123!"
DB_NAME="restaurant_db"
LOG_FILE="/opt/restaurant-app/logs/db-optimize.log"

echo "$(date) - Iniciando optimización de BD..." >> $LOG_FILE

# Obtener todas las tablas
TABLES=$(mysql -u $DB_USER -p$DB_PASS -D $DB_NAME -e "SHOW TABLES;" | tail -n +2)

# Optimizar cada tabla
for TABLE in $TABLES; do
    echo "Optimizando tabla: $TABLE" >> $LOG_FILE
    mysql -u $DB_USER -p$DB_PASS -D $DB_NAME -e "OPTIMIZE TABLE $TABLE;" >> $LOG_FILE 2>&1
done

# Analizar tablas
for TABLE in $TABLES; do
    echo "Analizando tabla: $TABLE" >> $LOG_FILE
    mysql -u $DB_USER -p$DB_PASS -D $DB_NAME -e "ANALYZE TABLE $TABLE;" >> $LOG_FILE 2>&1
done

echo "$(date) - Optimización completada" >> $LOG_FILE
```
```bash
chmod +x /opt/restaurant-app/scripts/optimize-db.sh

# Ejecutar primer día del mes a las 5 AM
crontab -e
0 5 1 * * /opt/restaurant-app/scripts/optimize-db.sh
```

---

### 3.5 Actualización de Dependencias

**Revisar mensualmente**:
```bash
# En tu proyecto
mvn versions:display-dependency-updates

# Actualizar versión en pom.xml
# Ejecutar pruebas
mvn clean test

# Si todo OK, desplegar nueva versión
mvn clean package
```

---

## 4. Configuración de Cron Jobs

### Resumen de tareas programadas:
```bash
# Editar crontab
crontab -e

# CONTENIDO COMPLETO:

# Backup diario (3:00 AM)
0 3 * * * /opt/restaurant-app/scripts/backup-wrapper.sh

# Monitoreo cada 5 minutos
*/5 * * * * /opt/restaurant-app/scripts/monitor-services.sh

# Reporte diario (8:00 AM)
0 8 * * * /opt/restaurant-app/scripts/generate-daily-report.sh

# Optimización BD mensual (5:00 AM, día 1)
0 5 1 * * /opt/restaurant-app/scripts/optimize-db.sh

# Limpieza de backups antiguos (mantener últimos 30 días)
0 6 * * * find /opt/restaurant-app/backups/ -name "backup_*.sql" -mtime +30 -delete

# Verificar espacio en disco (cada hora)
0 * * * * df -h | grep "/$" | awk '{if($5+0 > 85) print "Disco al "$5}' | mail -s "Alerta Disco" admin@restaurant.com
```

### Verificar cron jobs:
```bash
# Ver crontab actual
crontab -l

# Ver logs de cron
sudo tail -f /var/log/syslog | grep CRON

# Verificar ejecución de scripts
ls -lht /opt/restaurant-app/logs/
```

---

## 5. Gestión de Logs

### 5.1 Rotación de Logs

Crear: `/etc/logrotate.d/restaurant-app`
````
/opt/restaurant-app/logs/*.log {
daily
rotate 30
compress
delaycompress
missingok
notifempty
create 0640 appuser appuser
sharedscripts
postrotate
systemctl reload restaurant-app > /dev/null 2>&1 || true
endscript
}