package com.app.SystemRestaurant.Monitoreo.Controller;
import com.app.SystemRestaurant.Monitoreo.Service.BackupService;
import com.app.SystemRestaurant.Monitoreo.Service.CleanupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/system/maintenance")
@CrossOrigin(origins = "*")
public class MaintenanceController {

    @Autowired
    private BackupService backupService;

    @Autowired
    private CleanupService cleanupService;

    /**
     * Ejecutar backup manual
     */
    @PostMapping("/backup")
    public String ejecutarBackup() throws Exception {
        return backupService.realizarBackup();
    }

    /**
     * Limpiar archivos temporales manualmente
     */
    @PostMapping("/cleanup/temp")
    public ResponseEntity<Map<String, String>> limpiarTemporales() {
        Map<String, String> response = new HashMap<>();

        try {
            cleanupService.limpiarArchivosTempales();
            response.put("status", "success");
            response.put("message", "Limpieza de archivos temporales completada");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error durante la limpieza: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Limpiar logs antiguos manualmente
     */
    @PostMapping("/cleanup/logs")
    public ResponseEntity<Map<String, String>> limpiarLogs() {
        Map<String, String> response = new HashMap<>();

        try {
            cleanupService.limpiarLogsAntiguos();
            response.put("status", "success");
            response.put("message", "Limpieza de logs completada");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error durante la limpieza: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Obtener información del sistema
     */
    @GetMapping("/system-info")
    public ResponseEntity<Map<String, Object>> obtenerInfoSistema() {
        Map<String, Object> info = new HashMap<>();

        Runtime runtime = Runtime.getRuntime();

        info.put("totalMemory", runtime.totalMemory() / (1024 * 1024) + " MB");
        info.put("freeMemory", runtime.freeMemory() / (1024 * 1024) + " MB");
        info.put("maxMemory", runtime.maxMemory() / (1024 * 1024) + " MB");
        info.put("availableProcessors", runtime.availableProcessors());
        info.put("javaVersion", System.getProperty("java.version"));
        info.put("osName", System.getProperty("os.name"));
        info.put("osVersion", System.getProperty("os.version"));

        return ResponseEntity.ok(info);
    }
}
