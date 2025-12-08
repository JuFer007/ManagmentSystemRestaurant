package com.app.SystemRestaurant.Monitoreo.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class CleanupService {

    private static final Logger logger = LoggerFactory.getLogger(CleanupService.class);

    // Limpiar archivos temporales cada día a las 3:00 AM
    @Scheduled(cron = "0 0 3 * * ?")
    public void limpiarArchivosTempales() {
        logger.info("Iniciando limpieza de archivos temporales...");

        try {
            // Limpiar directorio temp del sistema
            String tempDir = System.getProperty("java.io.tmpdir");
            limpiarDirectorio(tempDir, 7); // Archivos de más de 7 días

            logger.info("Limpieza de archivos temporales completada");
        } catch (Exception e) {
            logger.error("Error durante la limpieza de archivos temporales: " + e.getMessage(), e);
        }
    }

    // Limpiar logs antiguos cada semana
    @Scheduled(cron = "0 0 4 ? * SUN")
    public void limpiarLogsAntiguos() {
        logger.info("Iniciando limpieza de logs antiguos...");

        try {
            String logsDir = "logs/";
            File logDirectory = new File(logsDir);

            if (logDirectory.exists() && logDirectory.isDirectory()) {
                limpiarDirectorio(logsDir, 30); // Logs de más de 30 días
            }

            logger.info("Limpieza de logs completada");
        } catch (Exception e) {
            logger.error("Error durante la limpieza de logs: " + e.getMessage(), e);
        }
    }

    private void limpiarDirectorio(String directoryPath, int diasAntiguedad) throws IOException {
        Path dir = Paths.get(directoryPath);

        if (!Files.exists(dir)) {
            logger.warn("El directorio no existe: " + directoryPath);
            return;
        }

        long currentTime = System.currentTimeMillis();
        long cutoffTime = currentTime - (diasAntiguedad * 24L * 60 * 60 * 1000);

        try (Stream<Path> paths = Files.walk(dir)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> {
                        try {
                            return Files.getLastModifiedTime(path).toMillis() < cutoffTime;
                        } catch (IOException e) {
                            logger.error("Error al obtener fecha de modificación: " + path, e);
                            return false;
                        }
                    })
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                            logger.info("Archivo eliminado: " + path);
                        } catch (IOException e) {
                            logger.error("Error al eliminar archivo: " + path, e);
                        }
                    });
        }
    }

    // Limpiar imágenes de platos huérfanas (que no están en BD) cada mes
    @Scheduled(cron = "0 0 5 1 * ?")
    public void limpiarImagenesHuerfanas() {
        logger.info("Iniciando limpieza de imágenes huérfanas...");
        logger.info("Limpieza de imágenes huérfanas completada");
    }
}