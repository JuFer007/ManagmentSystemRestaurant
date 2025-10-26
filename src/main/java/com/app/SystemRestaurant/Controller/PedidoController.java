package com.app.SystemRestaurant.Controller;
import com.app.SystemRestaurant.DTO.PedidoDTO;
import com.app.SystemRestaurant.DTO.PedidoRequestDTO;
import com.app.SystemRestaurant.Service.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@AllArgsConstructor

public class PedidoController {
    private final PedidoService pedidoService;

    @GetMapping
    public List<PedidoDTO>listarPedidos() {
        return pedidoService.listarPedidosDTO();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPedidoPorId(@PathVariable int id) {
        PedidoDTO pedidoDTO = pedidoService.obtenerPedidoPorId(id);
        if (pedidoDTO != null) {
            return ResponseEntity.ok(pedidoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> crearPedido(@RequestBody PedidoRequestDTO pedidoRequest) {
        try {
            PedidoDTO nuevoPedido = pedidoService.crearPedido(pedidoRequest);
            return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
        } catch (Exception e) {
            // Considera un manejo de errores más específico
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<String> actualizarEstadoPedido(@PathVariable int id, @RequestParam String nuevoEstado) {
        boolean actualizado = pedidoService.actualizarEstado(id, nuevoEstado);
        if (actualizado) {
            return ResponseEntity.ok("El estado del pedido actualizado a: " + nuevoEstado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido con id: " + id  + " no encontrador");
        }
    }
}
