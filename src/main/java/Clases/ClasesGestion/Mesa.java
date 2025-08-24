package Clases.ClasesGestion;

public class Mesa {
    private int idMesa;
    private int cantidad;
    private String estadoMesa;

    public Mesa(int cantidad, String estadoMesa) {
        this.cantidad = cantidad;
        this.estadoMesa = estadoMesa;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstadoMesa() {
        return estadoMesa;
    }

    public void setEstadoMesa(String estadoMesa) {
        this.estadoMesa = estadoMesa;
    }
}
