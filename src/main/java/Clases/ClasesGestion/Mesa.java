package Clases.ClasesGestion;

public class Mesa {
    private int idMesa;
    private int idMesero;
    private int numeroMesa;
    private int capacidad;
    private String estadoMesa;

    public Mesa(int idMesero, int numeroMesa, int capacidad, String estadoMesa) {
        this.idMesero = idMesero;
        this.numeroMesa = numeroMesa;
        this.capacidad = capacidad;
        this.estadoMesa = estadoMesa;
    }

    public Mesa() {
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

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getEstadoMesa() {
        return estadoMesa;
    }

    public void setEstadoMesa(String estadoMesa) {
        this.estadoMesa = estadoMesa;
    }
}
