package Clases.ClasesFInanzas;
import java.sql.Date;

public class Pago {
    private int idPago;
    private double montoPago;
    private String metodoPago;
    private Date fechaPago;
    private int idPedido;

    public Pago(double montoPago, String metodoPago, Date fechaPago, int idPedido) {
        this.montoPago = montoPago;
        this.metodoPago = metodoPago;
        this.fechaPago = fechaPago;
        this.idPedido = idPedido;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public double getMontoPago() {
        return montoPago;
    }

    public void setMontoPago(double montoPago) {
        this.montoPago = montoPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }
}
