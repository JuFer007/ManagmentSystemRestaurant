package Clases.ClasesGestion;
import java.sql.Date;

public class Pedido {
    private int idPedido;
    private int idMesa;
    private int idMesero;
    private int idCliente;
    private Date fecha;
    private String estadoPedido;
    private String codigoPedido;
    private double totalPedido;

    public Pedido(int idMesa, int idMesero, int idCliente, Date fecha, String estadoPedido, String codigoPedido, double totalPedido) {
        this.idMesa = idMesa;
        this.idMesero = idMesero;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.estadoPedido = estadoPedido;
        this.codigoPedido = codigoPedido;
        this.totalPedido = totalPedido;
    }

    public Pedido() {
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getIdMesero() {
        return idMesero;
    }

    public void setIdMesero(int idMesero) {
        this.idMesero = idMesero;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(double totalPedido) {
        this.totalPedido = totalPedido;
    }
}
