package Clases.ClasesGestion;

public class DetallePedido {
    private int idDetallePedido;
    private int idPedido;
    private int idMenu;
    private int cantidad;
    private double subTotal;
    private boolean esDelivery;

    public DetallePedido(int idPedido, int idMenu, int cantidad, double subTotal, boolean esDelivery) {
        this.idPedido = idPedido;
        this.idMenu = idMenu;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
        this.esDelivery = esDelivery;
    }

    public int getIdDetallePedido() {
        return idDetallePedido;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public boolean isEsDelivery() {
        return esDelivery;
    }
}
