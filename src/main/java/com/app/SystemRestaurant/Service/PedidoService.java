package com.app.SystemRestaurant.Service;
import com.app.SystemRestaurant.DTO.PedidoDTO;
import com.app.SystemRestaurant.DTO.PedidoRequestDTO;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Cliente;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Empleado;
import com.app.SystemRestaurant.Model.ClasesGestion.Mesa;
import com.app.SystemRestaurant.Model.ClasesGestion.Pedido;
import com.app.SystemRestaurant.Model.ClasesGestion.DetallePedido;
import com.app.SystemRestaurant.Model.ClasesGestion.Plato;
import com.app.SystemRestaurant.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final ClienteRepository clienteRepository;
    private final EmpleadoRepository empleadoRepository;
    private final PlatoRepository platoRepository;
    private final MesaRepository mesaRepository;

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
                pedido.getIdEmpleado() != null ? pedido.getIdEmpleado().getIdEmpleado() : 0,
                pedido.getIdEmpleado() != null ? pedido.getIdEmpleado().getCodigoEmpleado() : "",
                pedido.getIdEmpleado() != null ? pedido.getIdEmpleado().getNombreEmpleado() : "",
                pedido.getIdEmpleado() != null ? pedido.getIdEmpleado().getApellidoPaternoEmpleado() : "",
                pedido.getIdEmpleado() != null ? pedido.getIdEmpleado().getApellidoMaternoEmpleado() : "",
                pedido.getIdMesa() != null ? pedido.getIdMesa().getNumeroMesa() : 0,
                pedido.getFecha()
        );
    }

    //Obtener pedido por id
    public PedidoDTO obtenerPedidoPorId(int id) {
        return pedidoRepository.findById(id)
                .map(this::convertirAPedidoDTO)
                .orElse(null);
    }

    //Cambiar estado del pedido
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

    //Crear un nuevo pedido
    @Transactional
    public PedidoDTO crearPedido(PedidoRequestDTO pedidoRequest) {
        Cliente cliente = clienteRepository.findById(pedidoRequest.getIdCliente()).orElseThrow(() ->
        new RuntimeException("Cliente no encontrado con id: " + pedidoRequest.getIdCliente()));

        Empleado empleado = empleadoRepository.findById(pedidoRequest.getIdEmpleado() != null ? pedidoRequest.getIdEmpleado() : 1)
        .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + pedidoRequest.getIdEmpleado()));

        Mesa mesa = null;
        if (pedidoRequest.getIdMesa() != null) {
            mesa = mesaRepository.findById(pedidoRequest.getIdMesa()).orElseThrow(() ->
            new RuntimeException("Mesa no encontrada con id: " + pedidoRequest.getIdMesa()));
        }

        Pedido pedido = new Pedido();
        pedido.setIdCliente(cliente);
        pedido.setIdEmpleado(empleado);
        pedido.setFecha(new java.sql.Date(new Date().getTime()));
        pedido.setEstadoPedido("En proceso");
        pedido.setIdMesa(mesa);
        pedido.setTotalPedido(pedidoRequest.getTotalPedido());

        long totalPedidos = pedidoRepository.count() + 1;
        String codigoGenerado = String.format("PED%03d", totalPedidos);
        pedido.setCodigoPedido(codigoGenerado);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        List<DetallePedido> detalles = new ArrayList<>();
        for (PedidoRequestDTO.DetalleRequestDTO detalleDTO : pedidoRequest.getDetalles()) {

            Plato plato = platoRepository.findById(detalleDTO.getIdPlato()).orElseThrow(() ->
            new RuntimeException("Plato no encontrado con id: " + detalleDTO.getIdPlato()));

            DetallePedido detalle = new DetallePedido();
            detalle.setIdPedido(pedidoGuardado);
            detalle.setIdPlato(plato);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setSubTotal(plato.getPrecioPlato() * detalleDTO.getCantidad());
            detalles.add(detalle);
        }
        detallePedidoRepository.saveAll(detalles);
        pedidoGuardado.setDetalles(detalles);
        return convertirAPedidoDTO(pedidoGuardado);
    }
}
