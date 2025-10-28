package com.app.SystemRestaurant.Controller;
import com.app.SystemRestaurant.Service.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin("*")
@AllArgsConstructor

public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        return ResponseEntity.ok(dashboardService.estadisticas());
    }

    @GetMapping("/ingresos-mes")
    public ResponseEntity<List<Object[]>> getIngresosPorMes() {
        return ResponseEntity.ok(dashboardService.getIngresosPorMes());
    }

    @GetMapping("/platos-mas-vendidos")
    public ResponseEntity<List<Object[]>> getPlatosMasVendidos() {
        return ResponseEntity.ok(dashboardService.getPlatosMasVendidos());
    }
}
