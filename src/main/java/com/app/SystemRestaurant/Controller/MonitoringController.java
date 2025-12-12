package com.app.SystemRestaurant.Controller;

import com.app.SystemRestaurant.Repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.text.DecimalFormat;

@Controller
public class MonitoringController {

    @Autowired
    private PedidoRepository pedidoRepository;

    private static final DecimalFormat df = new DecimalFormat("#.##");

    @GetMapping("/monitoring")
    public String monitoringDashboard(Model model) {
        System.out.println("=== MONITORING EJECUTADO ===");

        try {
            // Uso de CPU
            OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
            double loadAverage = osBean.getSystemLoadAverage();
            int availableProcessors = osBean.getAvailableProcessors();

            // En Windows, loadAverage puede ser -1
            double cpuUsage = 0;
            if (loadAverage >= 0) {
                cpuUsage = (loadAverage / availableProcessors) * 100;
            } else {
                // Alternativa para Windows
                com.sun.management.OperatingSystemMXBean osBean2 =
                        (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                cpuUsage = osBean2.getSystemCpuLoad() * 100;
            }

            model.addAttribute("cpuUsage", df.format(Math.max(0, Math.min(100, cpuUsage))) + "%");

            // Uso de Memoria
            MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
            long heapUsed = memoryBean.getHeapMemoryUsage().getUsed();
            long heapMax = memoryBean.getHeapMemoryUsage().getMax();
            double memoryUsage = (heapUsed * 100.0) / heapMax;
            model.addAttribute("memoryUsage", df.format(memoryUsage) + "%");

            // Uso de Disco - Ajustado para Windows
            File[] roots = File.listRoots();
            File root = roots.length > 0 ? roots[0] : new File("C:\\");

            long totalSpace = root.getTotalSpace();
            long freeSpace = root.getFreeSpace();
            long usedSpace = totalSpace - freeSpace;
            double diskUsage = totalSpace > 0 ? (usedSpace * 100.0) / totalSpace : 0;
            model.addAttribute("diskUsage", df.format(diskUsage) + "%");

            // Estadísticas de actividad
            long pedidosHoy = pedidoRepository.count();
            model.addAttribute("pedidosHoy", pedidosHoy);
            model.addAttribute("ticketsHoy", pedidosHoy);
            model.addAttribute("erroresHoy", 0);

            // Estado de servicios
            model.addAttribute("springBootStatus", "ACTIVO");
            model.addAttribute("nodeStatus", verificarServicioNode());
            model.addAttribute("mysqlStatus", "ACTIVO");

            // Tiempo de actividad
            long uptimeMillis = ManagementFactory.getRuntimeMXBean().getUptime();
            long uptimeDays = uptimeMillis / (1000 * 60 * 60 * 24);
            long uptimeHours = (uptimeMillis / (1000 * 60 * 60)) % 24;
            model.addAttribute("uptime", uptimeDays + " días, " + uptimeHours + " horas");

            System.out.println("CPU: " + model.getAttribute("cpuUsage"));
            System.out.println("Memoria: " + model.getAttribute("memoryUsage"));
            System.out.println("Disco: " + model.getAttribute("diskUsage"));
            System.out.println("=== RETORNANDO monitoring ===");

            return "monitoring";

        } catch (Exception e) {
            System.err.println("ERROR en monitoring: " + e.getMessage());
            e.printStackTrace();

            // Valores por defecto
            model.addAttribute("cpuUsage", "N/A");
            model.addAttribute("memoryUsage", "N/A");
            model.addAttribute("diskUsage", "N/A");
            model.addAttribute("pedidosHoy", 0);
            model.addAttribute("ticketsHoy", 0);
            model.addAttribute("erroresHoy", 1);
            model.addAttribute("springBootStatus", "ERROR");
            model.addAttribute("nodeStatus", "ERROR");
            model.addAttribute("mysqlStatus", "ACTIVO");
            model.addAttribute("uptime", "0 días, 0 horas");

            return "monitoring";
        }
    }

    private String verificarServicioNode() {
        try {
            java.net.URL url = new java.net.URL("http://localhost:3000/health");
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(2000);
            int responseCode = conn.getResponseCode();
            conn.disconnect();
            return (responseCode == 200) ? "ACTIVO" : "INACTIVO";
        } catch (Exception e) {
            return "INACTIVO";
        }
    }
}