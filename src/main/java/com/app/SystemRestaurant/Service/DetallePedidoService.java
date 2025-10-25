package com.app.SystemRestaurant.Service;
import com.app.SystemRestaurant.Model.ClasesGestion.DetallePedido;
import com.app.SystemRestaurant.Repository.DetallePedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class DetallePedidoService {
    private final DetallePedidoRepository detallePedidoRepository;

    public List<DetallePedido> listarDetallesPorPedido(int idPedido) {
        return detallePedidoRepository.findByIdPedido_IdPedido(idPedido);
    }
}
