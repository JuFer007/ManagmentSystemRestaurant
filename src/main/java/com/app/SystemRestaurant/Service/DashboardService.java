package com.app.SystemRestaurant.Service;
import com.app.SystemRestaurant.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor

public class DashboardService {
    private final EmpleadoRepository empleadoRepository;
    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;
    private final PlatoRepository platoRepository;

    //Estadisticas generales
    public Map<String, Object> estadisticas() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("cantidadEmpleados", empleadoRepository.count());
        stats.put("numeroClientes", clienteRepository.count());
        stats.put("cantidadPedidos", pedidoRepository.count());
        stats.put("ingresosTotales", pedidoRepository.totalGanancias());
        return stats;
    }

    //Ingresos por mes
    public List<Object[]> getIngresosPorMes() {
        return pedidoRepository.ingresosPorMes();
    }

    //Platos más vendidos
    public List<Object[]> getPlatosMasVendidos() {
        return platoRepository.platosMayormentVendidos();
    }
}
