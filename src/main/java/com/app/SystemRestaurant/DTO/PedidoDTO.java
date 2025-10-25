package com.app.SystemRestaurant.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor

public class PedidoDTO {
    private int idPedido;
    private String codigoPedido;
    private String estadoPedido;
    private double totalPedido;
    private int idCliente;
    private String nombreCliente;
    private String apellidosCliente;
    private int idEmpleado;
    private String codigoEmpleado;
    private String nombreEmpleado;
    private String apellidoPaternoEmpleado;
    private String apellidoMaternoEmpleado;
    private int numeroMesa;
    private Date fecha;
}
