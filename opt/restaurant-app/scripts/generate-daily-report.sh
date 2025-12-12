#!/bin/bash

# Reporte diario de sistema
REPORT_FILE="/opt/restaurant-app/reports/daily_report_$(date +%Y%m%d).txt"
mkdir -p /opt/restaurant-app/reports

{
    echo "=================================="
    echo "REPORTE DIARIO - SISTEMA RESTAURANTE"
    echo "Fecha: $(date '+%Y-%m-%d %H:%M:%S')"
    echo "=================================="
    echo ""

    echo "--- Estado de Servicios ---"
    systemctl status restaurant-app | grep Active
    systemctl status ticket-service | grep Active
    systemctl status mysql | grep Active
    echo ""

    echo "--- Uso de Recursos ---"
    echo "CPU:"
    top -bn1 | grep "Cpu(s)" | sed "s/.*, *\([0-9.]*\)%* id.*/\1/" | awk '{print 100 - $1"%"}'
    echo "Memoria:"
    free -h | awk 'NR==2{printf "Usado: %s / Total: %s (%.2f%%)\n", $3,$2,$3*100/$2 }'
    echo "Disco:"
    df -h / | awk 'NR==2{print "Usado: "$3" / Total: "$2" ("$5")"}'
    echo ""

    echo "--- Logs de Errores (últimas 24h) ---"
    grep -i "error" /opt/restaurant-app/logs/application.log | tail -20
    echo ""

    echo "--- Actividad de Usuarios ---"
    grep "login exitoso" /opt/restaurant-app/logs/application.log | wc -l | awk '{print "Logins exitosos: "$1}'
    grep "Pedido creado" /opt/restaurant-app/logs/application.log | wc -l | awk '{print "Pedidos creados: "$1}'
    echo ""

    echo "=================================="
    echo "Fin del reporte"
    echo "=================================="

} > $REPORT_FILE

# Enviar por email
cat $REPORT_FILE | mail -s "Reporte Diario - $(date +%Y-%m-%d)" admin@restaurant.com

echo "Reporte generado: $REPORT_FILE"
