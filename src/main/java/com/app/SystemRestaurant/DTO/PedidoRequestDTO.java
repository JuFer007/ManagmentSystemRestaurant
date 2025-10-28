package com.app.SystemRestaurant.DTO;

import lombok.Data;
import java.util.List;

@Data
public class PedidoRequestDTO {
    private Integer idCliente;
    private Integer idEmpleado;
    private Integer idMesa;
    private double totalPedido;
    private List<DetalleRequestDTO> detalles;

    @Data
    public static class DetalleRequestDTO {
        private int idPlato;
        private int cantidad;
    }
}