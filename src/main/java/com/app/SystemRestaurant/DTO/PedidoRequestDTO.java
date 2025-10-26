package com.app.SystemRestaurant.DTO;

import lombok.Data;
import java.util.List;

@Data
public class PedidoRequestDTO {
    private Integer idCliente;
    private Integer idMesero; // Asumimos un mesero por defecto, podrías añadir un selector en el frontend
    private Integer idMesa;   // Asumimos una mesa por defecto, podrías añadir un selector
    private double totalPedido;
    private List<DetalleRequestDTO> detalles;

    @Data
    public static class DetalleRequestDTO {
        private int idPlato;
        private int cantidad;
    }
}