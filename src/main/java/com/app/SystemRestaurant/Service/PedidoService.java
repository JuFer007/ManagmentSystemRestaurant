package com.app.SystemRestaurant.Service;
import com.app.SystemRestaurant.DTO.PedidoDTO;
import com.app.SystemRestaurant.DTO.PedidoRequestDTO;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Cliente;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Mesero;
import com.app.SystemRestaurant.Model.ClasesGestion.Pedido;
import com.app.SystemRestaurant.Model.ClasesGestion.DetallePedido;
import com.app.SystemRestaurant.Model.ClasesGestion.Mesa;
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
    private final MeseroRepository meseroRepository;
    private final MesaRepository mesaRepository; // Comentado temporalmente
    private final PlatoRepository platoRepository;

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

    //Crear un nuevo pedido
    @Transactional
    public PedidoDTO crearPedido(PedidoRequestDTO pedidoRequest) {
        // 1. Validar y obtener entidades relacionadas
        Cliente cliente = clienteRepository.findById(pedidoRequest.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + pedidoRequest.getIdCliente()));

        // Asumimos un mesero y mesa por defecto si no se envían. ¡Deberías mejorar esto!
        Mesero mesero = meseroRepository.findById(pedidoRequest.getIdMesero() != null ? pedidoRequest.getIdMesero() : 1)
                .orElseThrow(() -> new RuntimeException("Mesero no encontrado"));

        // Mesa mesa = mesaRepository.findById(pedidoRequest.getIdMesa() != null ? pedidoRequest.getIdMesa() : 1)
        //         .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));

        // 2. Crear y guardar la entidad Pedido
        Pedido pedido = new Pedido();
        pedido.setIdCliente(cliente);
        pedido.setIdMesero(mesero);
        // pedido.setIdMesa(mesa); // Comentado temporalmente
        pedido.setFecha(new java.sql.Date(new Date().getTime()));
        pedido.setEstadoPedido("En proceso");
        pedido.setCodigoPedido(UUID.randomUUID().toString().substring(0, 8).toUpperCase()); // Generar código único
        pedido.setTotalPedido(pedidoRequest.getTotalPedido()); // Confiar en el total del frontend por ahora

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // 3. Crear y guardar los detalles del pedido
        List<DetallePedido> detalles = new ArrayList<>();
        for (PedidoRequestDTO.DetalleRequestDTO detalleDTO : pedidoRequest.getDetalles()) {
            Plato plato = platoRepository.findById(detalleDTO.getIdPlato())
                    .orElseThrow(() -> new RuntimeException("Plato no encontrado con id: " + detalleDTO.getIdPlato()));

            DetallePedido detalle = new DetallePedido();
            detalle.setIdPedido(pedidoGuardado);
            detalle.setIdPlato(plato);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setSubTotal(plato.getPrecioPlato() * detalleDTO.getCantidad());
            detalles.add(detalle);
        }
        detallePedidoRepository.saveAll(detalles);

        // 4. Asignar detalles al pedido y devolver DTO
        pedidoGuardado.setDetalles(detalles);

        return convertirAPedidoDTO(pedidoGuardado);
    }
}
