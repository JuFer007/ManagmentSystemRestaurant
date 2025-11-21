package com.app.SystemRestaurant.Controller;
import com.app.SystemRestaurant.Model.ClasesGestion.Pedido;
import com.app.SystemRestaurant.Service.PedidoService;
import com.app.SystemRestaurant.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")

public class TicketController {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<byte[]> generarTicketPorPedido(@PathVariable int idPedido) {
        try {
            Pedido pedido = pedidoService.buscarPedidoPorId(idPedido);

            if (pedido == null) {
                return ResponseEntity.notFound().build();
            }

            byte[] pdfBytes = ticketService.generarTicketPedido(pedido);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.inline().filename("ticket_" + pedido.getCodigoPedido() + ".pdf").build()
            );

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/imprimir/{idPedido}")
    public ResponseEntity<byte[]> imprimirTicket(@PathVariable int idPedido) {
        try {
            Pedido pedido = pedidoService.buscarPedidoPorId(idPedido);

            if (pedido == null) {
                return ResponseEntity.notFound().build();
            }

            byte[] pdfBytes = ticketService.generarTicketPedido(pedido);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("X-Print-Action", "auto-print");
            headers.setContentDisposition(ContentDisposition.inline().filename("ticket_" + pedido.getCodigoPedido() + ".pdf").build()
            );

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> verificarServicio() {
        boolean disponible = ticketService.verificarServicio();

        if (disponible) {
            return ResponseEntity.ok("Servicio de tickets disponible");
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Servicio de tickets no disponible");
        }
    }
}
