package com.app.SystemRestaurant.Service;
import com.app.SystemRestaurant.DTO.PedidoDTO;
import com.app.SystemRestaurant.Model.ClasesGestion.Pedido;
import com.app.SystemRestaurant.Repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class PedidoService {
    private final PedidoRepository pedidoRepository;

    //Listar los pedidos convertidos
    public List<PedidoDTO> listarPedidosDTO() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::convertirAPedidoDTO)
                .collect(Collectors.toList());
    }

    //Convertir pedido a DTO
    private PedidoDTO convertirAPedidoDTO(Pedido pedido) {
        return new PedidoDTO(
                pedido.getIdPedido(),
                pedido.getCodigoPedido(),
                pedido.getEstadoPedido(),
                pedido.getTotalPedido(),
                pedido.getIdCliente() != null ? pedido.getIdCliente().getIdCliente() : 0,
                pedido.getIdCliente() != null ? pedido.getIdCliente().getNombreCliente() : "",
                pedido.getIdCliente() != null ? pedido.getIdCliente().getApellidosCliente() : "",
                pedido.getIdMesero() != null ? pedido.getIdMesero().getIdMesero() : 0,
                pedido.getIdMesero() != null ? pedido.getIdMesero().getEmpleado().getCodigoEmpleado() : "",
                pedido.getIdMesero() != null ? pedido.getIdMesero().getEmpleado().getNombreEmpleado() : "",
                pedido.getIdMesero() != null ? pedido.getIdMesero().getEmpleado().getApellidoPaternoEmpleado() : "",
                pedido.getIdMesero() != null ? pedido.getIdMesero().getEmpleado().getApellidoMaternoEmpleado() : "",
                pedido.getIdMesa() != null ? pedido.getIdMesa().getNumeroMesa() : 0,
                pedido.getFecha()
        );
    }

    //Obtener datos de un pedido por id
    public PedidoDTO obtenerPedidoPorId(int id) {
        return pedidoRepository.findById(id).map(this::convertirAPedidoDTO).orElse(null);
    }

    //Cambiar estado de pedido
    public boolean actualizarEstado(int idPedido, String nuevoEstado) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(idPedido);

        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();
            pedido.setEstadoPedido(nuevoEstado);
            pedidoRepository.save(pedido);
            return true;
        }
        return false;
    }
}
