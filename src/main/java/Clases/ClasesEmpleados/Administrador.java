package Clases.ClasesEmpleados;

public class Administrador extends Empleado {
    private int idAdministrador;
    private String codigoAdministrador;

    public Administrador(String nombreEmpleado, String apellidoPaternoEmpleado, String apellidoMaternoEmpleado, double salarioEmpleado, String dniEmpleado,
    String cargoEmpleado, String correoElectronico, String numeroTelefono, String estadoEmpleado, String codigoAdministrador) {
        super(nombreEmpleado, apellidoPaternoEmpleado, apellidoMaternoEmpleado, salarioEmpleado, dniEmpleado, cargoEmpleado, correoElectronico, numeroTelefono, estadoEmpleado);
        this.codigoAdministrador = codigoAdministrador;
    }

    public int getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(int idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getCodigoAdministrador() {
        return codigoAdministrador;
    }

    public void setCodigoAdministrador(String codigoAdministrador) {
        this.codigoAdministrador = codigoAdministrador;
    }
}
