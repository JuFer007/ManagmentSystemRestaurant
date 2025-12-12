#!/bin/bash

# Script de monitoreo de servicios
# Ejecutar cada 5 minutos vía cron

LOG_FILE="/opt/restaurant-app/logs/monitor.log"
ALERT_EMAIL="admin@restaurant.com"

# Función para registrar logs
log_message() {
    echo "$(date '+%Y-%m-%d %H:%M:%S') - $1" >> $LOG_FILE
}

# Verificar aplicación Spring Boot
check_springboot() {
    if ! systemctl is-active --quiet restaurant-app; then
        log_message "CRÍTICO: Aplicación Spring Boot está caída"
        echo "La aplicación está caída" | mail -s "ALERTA CRÍTICA" $ALERT_EMAIL
        systemctl start restaurant-app
        return 1
    fi

    if ! curl -s http://localhost:8080/actuator/health | grep -q "UP"; then
        log_message "ADVERTENCIA: Health check falló"
        return 1
    fi

    log_message "OK: Aplicación Spring Boot funcionando"
    return 0
}

# Verificar servidor Node
check_node() {
    if ! systemctl is-active --quiet ticket-service; then
        log_message "CRÍTICO: Servidor Node está caído"
        systemctl start ticket-service
        return 1
    fi

    if ! curl -s http://localhost:3000/health | grep -q "OK"; then
        log_message "ADVERTENCIA: Servicio de tickets no responde"
        return 1
    fi

    log_message "OK: Servidor Node funcionando"
    return 0
}

# Verificar MySQL
check_mysql() {
    if ! systemctl is-active --quiet mysql; then
        log_message "CRÍTICO: MySQL está caído"
        echo "Base de datos caída" | mail -s "ALERTA CRÍTICA BD" $ALERT_EMAIL
        return 1
    fi

    log_message "OK: MySQL funcionando"
    return 0
}

# Verificar espacio en disco
check_disk() {
    USAGE=$(df -h / | awk 'NR==2 {print $5}' | sed 's/%//')

    if [ $USAGE -gt 85 ]; then
        log_message "ADVERTENCIA: Disco al ${USAGE}%"
        echo "Espacio en disco: ${USAGE}%" | mail -s "ALERTA DISCO" $ALERT_EMAIL
        return 1
    fi

    log_message "OK: Espacio en disco: ${USAGE}%"
    return 0
}

# Ejecutar verificaciones
log_message "=== Iniciando monitoreo ==="
check_springboot
check_node
check_mysql
check_disk
log_message "=== Monitoreo completado ==="
