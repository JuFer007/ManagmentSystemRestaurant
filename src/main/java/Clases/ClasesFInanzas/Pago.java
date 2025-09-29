package Clases.ClasesFInanzas;
import java.sql.Date;

public class Pago {
    private int idPago;
    private int idPedido;
    private double montoPago;
    private String metodoPago;
    private Date fechaPago;

    public Pago(int idPedido, double montoPago, String metodoPago, Date fechaPago) {
        this.idPedido = idPedido;
        this.montoPago = montoPago;
        this.metodoPago = metodoPago;
        this.fechaPago = fechaPago;
    }

    public Pago() {
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
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
}
