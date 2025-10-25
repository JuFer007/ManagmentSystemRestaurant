package com.app.SystemRestaurant.Controller;
import com.app.SystemRestaurant.Model.ClasesGestion.DetallePedido;
import com.app.SystemRestaurant.Service.DetallePedidoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/detallesPedido")
@AllArgsConstructor

public class DetallePedidoController {
    private final DetallePedidoService detallePedidoService;

    @GetMapping("/pedido/{idPedido}")
    public List<DetallePedido> listarPorPedido(@PathVariable int idPedido) {
        return detallePedidoService.listarDetallesPorPedido(idPedido);
    }
}
