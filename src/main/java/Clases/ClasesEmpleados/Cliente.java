package Clases.ClasesEmpleados;

public class Cliente {
    private int idCliente;
    private String dniCliente;
    private String nombreCliente;
    private String apellidosCliente;

    public Cliente(String dniCliente, String nombreCliente, String apellidosCliente) {
        this.dniCliente = dniCliente;
        this.nombreCliente = nombreCliente;
        this.apellidosCliente = apellidosCliente;
    }

    public Cliente() {
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidosCliente() {
        return apellidosCliente;
    }

    public void setApellidosCliente(String apellidosCliente) {
        this.apellidosCliente = apellidosCliente;
    }
}
