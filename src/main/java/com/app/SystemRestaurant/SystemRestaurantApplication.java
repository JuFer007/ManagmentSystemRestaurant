package com.app.SystemRestaurant;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import java.io.File;
import java.io.IOException;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.app.SystemRestaurant",
        "com.app.SystemRestaurant.Monitoreo"
})

public class SystemRestaurantApplication {
	public static void main(String[] args) {
		SpringApplication.run(SystemRestaurantApplication.class, args);
	}
}
