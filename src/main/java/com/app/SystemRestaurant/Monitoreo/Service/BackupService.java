package com.app.SystemRestaurant.Monitoreo.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BackupService {

    private static final Logger logger = LoggerFactory.getLogger(BackupService.class);

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    private static final String MYSQLDUMP =
            "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe";

    private static final String BACKUP_DIR = "backups";

    public String realizarBackup() throws IOException {

        File dir = new File(BACKUP_DIR);
        if (!dir.exists()) dir.mkdirs();

        String ts = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File backupFile = new File(dir, "backup_" + ts + ".sql");

        String db = extractDatabaseName(dbUrl);

        List<String> command = List.of(
                MYSQLDUMP,
                "-u" + dbUsername,
                "-p" + dbPassword,
                "--databases",
                db,
                "-r",
                backupFile.getAbsolutePath()
        );

        logger.info("Ejecutando backup: {}", command);

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);

        try {
            int code = pb.start().waitFor();

            if (code != 0)
                throw new IOException("mysqldump terminó con código " + code);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException(e);
        }

        return backupFile.getAbsolutePath();
    }

    private String extractDatabaseName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
