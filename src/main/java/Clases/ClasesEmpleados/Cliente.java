package Clases.ClasesEmpleados;

public class Cliente {
    private int idCliente;
    private String nombreCliente;
    private String apellidosCliente;

    public Cliente(String nombreCliente, String apellidosCliente) {
        this.nombreCliente = nombreCliente;
        this.apellidosCliente = apellidosCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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
