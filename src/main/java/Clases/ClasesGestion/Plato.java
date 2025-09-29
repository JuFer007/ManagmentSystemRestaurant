package Clases.ClasesGestion;

public class Plato {
    private int idPlato;
    private String nombrePlato;
    private Double precioPlato;
    private String disponibilidad;
    private String rutaImagen;

    public Plato(String nombrePlato, Double precioPlato, String disponibilidad, String rutaImagen) {
        this.nombrePlato = nombrePlato;
        this.precioPlato = precioPlato;
        this.disponibilidad = disponibilidad;
        this.rutaImagen = rutaImagen;
    }

    public Plato(String nombrePlato, Double precioPlato, String disponibilidad) {
        this.nombrePlato = nombrePlato;
        this.precioPlato = precioPlato;
        this.disponibilidad = disponibilidad;
    }

    public Plato() {
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

    public Double getPrecioPlato() {
        return precioPlato;
    }

    public void setPrecioPlato(Double precioPlato) {
        this.precioPlato = precioPlato;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
}
