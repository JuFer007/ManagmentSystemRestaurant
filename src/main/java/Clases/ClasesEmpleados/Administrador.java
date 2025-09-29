package Clases.ClasesEmpleados;

public class Administrador extends Empleado {
    private int idAdministrador;
    private int idEmpleado;
    private String telefono;
    private String correoElectronico;

    public Administrador(String dniEmpleado, String codigoEmpleado, String nombreEmpleado, String apellidoPaternoEmpleado, String apellidoMaternoEmpleado, String estadoEmpleado, int horasTrabajo, double salarioEmpelado, String cargoEmpleado, int idEmpleado, String telefono, String correoElectronico) {
        super(dniEmpleado, codigoEmpleado, nombreEmpleado, apellidoPaternoEmpleado, apellidoMaternoEmpleado, estadoEmpleado, horasTrabajo, salarioEmpelado, cargoEmpleado);
        this.idEmpleado = idEmpleado;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
    }

    public Administrador() {
    }

    public int getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(int idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
}
