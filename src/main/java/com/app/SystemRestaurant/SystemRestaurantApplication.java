package com.app.SystemRestaurant;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class SystemRestaurantApplication {
	public static void main(String[] args) {
		SpringApplication.run(SystemRestaurantApplication.class, args);
	}

    @PostConstruct
    public void iniciarServidorNode() {
        try {
            File serverNodeJs = new File("serverNode/server.js");
            ProcessBuilder pb = new ProcessBuilder("node", serverNodeJs.getAbsolutePath());
            pb.inheritIO();
            pb.start();
            System.out.println("Servidor Node iniciado automáticamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
