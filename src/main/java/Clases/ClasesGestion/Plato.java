package Clases.ClasesGestion;

public class Plato {
    private int idPlato;
    private String nombrePlato;
    private String precioPlato;
    private String estadoPlato;

    public Plato(String nombrePlato, String precioPlato, String estadoPlato) {
        this.nombrePlato = nombrePlato;
        this.precioPlato = precioPlato;
        this.estadoPlato = estadoPlato;
    }

    public int getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(int idPlato) {
        this.idPlato = idPlato;
    }

    public String getNombrePlato() {
        return nombrePlato;
    }

    public void setNombrePlato(String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    public String getPrecioPlato() {
        return precioPlato;
    }

    public void setPrecioPlato(String precioPlato) {
        this.precioPlato = precioPlato;
    }

    public String getEstadoPlato() {
        return estadoPlato;
    }

    public void setEstadoPlato(String estadoPlato) {
        this.estadoPlato = estadoPlato;
    }
}
