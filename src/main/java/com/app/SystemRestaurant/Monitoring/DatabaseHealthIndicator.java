package com.app.SystemRestaurant.Monitoring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    @Autowired
    private DataSource dataSource;

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            return Health.up().withDetail("database", "Disponible").build();
        } catch (Exception e) {
            return Health.down().withDetail("database", "Caída").build();
        }
    }
    //http://localhost:8080/actuator/health
}

