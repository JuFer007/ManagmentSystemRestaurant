package com.app.SystemRestaurant.Monitoreo.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.text.DecimalFormat;

@Service
public class SystemMonitoringService {

    private static final Logger logger = LoggerFactory.getLogger(SystemMonitoringService.class);
    private static final DecimalFormat df = new DecimalFormat("#.##");

    // Monitorear recursos cada hora
    @Scheduled(cron = "0 0 * * * ?")
    public void monitorearRecursos() {
        logger.info("=== Reporte de Monitoreo del Sistema ===");

        monitorearMemoria();
        monitorearCPU();
        monitorearDisco();

        logger.info("========================================");
    }

    private void monitorearMemoria() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

        long heapUsed = memoryBean.getHeapMemoryUsage().getUsed();
        long heapMax = memoryBean.getHeapMemoryUsage().getMax();
        long nonHeapUsed = memoryBean.getNonHeapMemoryUsage().getUsed();

        double heapUsedMB = heapUsed / (1024.0 * 1024.0);
        double heapMaxMB = heapMax / (1024.0 * 1024.0);
        double heapUsagePercent = (heapUsed * 100.0) / heapMax;
        double nonHeapUsedMB = nonHeapUsed / (1024.0 * 1024.0);

        logger.info("MEMORIA:");
        logger.info("  Heap usado: " + df.format(heapUsedMB) + " MB / " + df.format(heapMaxMB) + " MB (" + df.format(heapUsagePercent) + "%)");
        logger.info("  Non-Heap usado: " + df.format(nonHeapUsedMB) + " MB");

        // Alerta si el uso de memoria supera el 80%
        if (heapUsagePercent > 80) {
            logger.warn("⚠️ ALERTA: Uso de memoria heap superior al 80%");
        }
    }

    private void monitorearCPU() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

        double loadAverage = osBean.getSystemLoadAverage();
        int availableProcessors = osBean.getAvailableProcessors();

        logger.info("CPU:");
        logger.info("  Procesadores disponibles: " + availableProcessors);
        logger.info("  Carga promedio del sistema: " + df.format(loadAverage));

        // Alerta si la carga promedio es muy alta
        if (loadAverage > availableProcessors * 0.8) {
            logger.warn("ALERTA: Carga de CPU elevada");
        }
    }

    private void monitorearDisco() {
        File root = new File("/");

        long totalSpace = root.getTotalSpace();
        long freeSpace = root.getFreeSpace();
        long usedSpace = totalSpace - freeSpace;

        double totalSpaceGB = totalSpace / (1024.0 * 1024.0 * 1024.0);
        double freeSpaceGB = freeSpace / (1024.0 * 1024.0 * 1024.0);
        double usedSpaceGB = usedSpace / (1024.0 * 1024.0 * 1024.0);
        double usagePercent = (usedSpace * 100.0) / totalSpace;

        logger.info("DISCO:");
        logger.info("  Espacio total: " + df.format(totalSpaceGB) + " GB");
        logger.info("  Espacio usado: " + df.format(usedSpaceGB) + " GB (" + df.format(usagePercent) + "%)");
        logger.info("  Espacio libre: " + df.format(freeSpaceGB) + " GB");

        // Alerta si el uso del disco supera el 85%
        if (usagePercent > 85) {
            logger.warn("ALERTA: Espacio en disco bajo (menos del 15% disponible)");
        }
    }

    // Reporte diario completo a las 8:00 AM
    @Scheduled(cron = "0 0 8 * * ?")
    public void reporteDiario() {
        logger.info("╔═══════════════════════════════════════════════════════╗");
        logger.info("║        REPORTE DIARIO DEL SISTEMA                      ║");
        logger.info("╚═══════════════════════════════════════════════════════╝");

        monitorearRecursos();
        verificarSaludGeneral();
    }

    private void verificarSaludGeneral() {
        Runtime runtime = Runtime.getRuntime();

        long uptimeMillis = ManagementFactory.getRuntimeMXBean().getUptime();
        long uptimeDays = uptimeMillis / (1000 * 60 * 60 * 24);
        long uptimeHours = (uptimeMillis / (1000 * 60 * 60)) % 24;

        logger.info("ESTADO GENERAL:");
        logger.info("  Tiempo de actividad: " + uptimeDays + " días, " + uptimeHours + " horas");
        logger.info("  Threads activos: " + Thread.activeCount());
    }
}