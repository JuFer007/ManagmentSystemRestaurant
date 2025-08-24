package Clases.ClasesGestion;

public class Delivery {
    private int idDelivery;
    private String direccionEntrega;
    private String estadoDelivery;
    private int idPedido;
    private int idRepartidor;

    public Delivery(String direccionEntrega, String estadoDelivery, int idPedido, int idRepartidor) {
        this.direccionEntrega = direccionEntrega;
        this.estadoDelivery = estadoDelivery;
        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
    }

    public int getIdDelivery() {
        return idDelivery;
    }

    public void setIdDelivery(int idDelivery) {
        this.idDelivery = idDelivery;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getEstadoDelivery() {
        return estadoDelivery;
    }

    public void setEstadoDelivery(String estadoDelivery) {
        this.estadoDelivery = estadoDelivery;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(int idRepartidor) {
        this.idRepartidor = idRepartidor;
    }
}
