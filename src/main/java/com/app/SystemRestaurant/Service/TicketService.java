package com.app.SystemRestaurant.Service;
import com.app.SystemRestaurant.Model.ClasesGestion.DetallePedido;
import com.app.SystemRestaurant.Model.ClasesGestion.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.text.SimpleDateFormat;
import java.util.*;

@Service

public class TicketService {
    @Value("${ticket.service.url:http://localhost:3000}")
    private String ticketServiceUrl;

    private final RestTemplate restTemplate;

    public TicketService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public byte[] generarTicketPedido(Pedido pedido) {

        if (pedido.getDetalles() == null || pedido.getDetalles().isEmpty()) {
            throw new RuntimeException("El pedido no contiene platos");
        }

        Map<String, Object> request = new HashMap<>();

        request.put("cliente", (pedido.getIdCliente() != null) ? pedido.getIdCliente().getNombreCliente() + " " + pedido.getIdCliente().getApellidosCliente(): "Cliente General"
        );

        request.put("dni", (pedido.getIdCliente() != null) ? pedido.getIdCliente().getDniCliente() : "N/A"
        );

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");

        request.put("fecha", formatoFecha.format(pedido.getFecha()));

        Date fechaPedido = pedido.getFecha();
        String hora;

        if (fechaPedido != null) {
            hora = formatoHora.format(fechaPedido);
        } else {
            hora = formatoHora.format(new Date());
        }

        request.put("hora", hora);
        request.put("mesero", (pedido.getIdEmpleado() != null) ? pedido.getIdEmpleado().getNombreEmpleado() + " " +
                pedido.getIdEmpleado().getApellidoPaternoEmpleado() + " " +
                pedido.getIdEmpleado().getApellidoMaternoEmpleado(): "N/A"
        );

        request.put("ticketNumero",(pedido.getCodigoPedido() != null) ? pedido.getCodigoPedido() : String.format("%06d", pedido.getIdPedido())
        );

        request.put("numeroMesa",(pedido.getIdMesa() != null) ? pedido.getIdMesa().getNumeroMesa() : "N/A"
        );

        List<Map<String, Object>> listaPlatos = new ArrayList<>();
        for (DetallePedido det : pedido.getDetalles()) {
            Map<String, Object> platoJson = new HashMap<>();
            platoJson.put("nombre", det.getIdPlato().getNombrePlato());
            platoJson.put("cantidad", det.getCantidad());
            platoJson.put("precio", det.getIdPlato().getPrecioPlato());
            listaPlatos.add(platoJson);
        }

        request.put("platos", listaPlatos);
        double subtotal = pedido.getTotalPedido();
        double descuento = 0.00;
        double total = subtotal - descuento;

        request.put("subtotal", subtotal);
        request.put("descuento", descuento);
        request.put("total", total);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    ticketServiceUrl + "/generar-ticket",
                    HttpMethod.POST,
                    entity,
                    byte[].class
            );
            return response.getBody();

        } catch (Exception e) {
            throw new RuntimeException("Error al comunicarse con el servicio de tickets: " + e.getMessage());
        }
    }

    public boolean verificarServicio() {
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(ticketServiceUrl + "/health", Map.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            System.err.println("Servicio de tickets no disponible: " + e.getMessage());
            return false;
        }
    }
}
