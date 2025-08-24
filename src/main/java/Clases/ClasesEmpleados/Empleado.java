package Clases.ClasesEmpleados;

public class Empleado {
    private int idEmpelado;
    private String nombreEmpleado;
    private String apellidoPaternoEmpleado;
    private String apellidoMaternoEmpleado;
    private double salarioEmpleado;
    private String dniEmpleado;
    private String cargoEmpleado;
    private String correoElectronico;
    private String numeroTelefono;
    private String estadoEmpleado;

    public Empleado(String nombreEmpleado, String apellidoPaternoEmpleado, String apellidoMaternoEmpleado, double salarioEmpleado, String dniEmpleado,
    String cargoEmpleado, String correoElectronico, String numeroTelefono, String estadoEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoPaternoEmpleado = apellidoPaternoEmpleado;
        this.apellidoMaternoEmpleado = apellidoMaternoEmpleado;
        this.salarioEmpleado = salarioEmpleado;
        this.dniEmpleado = dniEmpleado;
        this.cargoEmpleado = cargoEmpleado;
        this.correoElectronico = correoElectronico;
        this.numeroTelefono = numeroTelefono;
        this.estadoEmpleado = estadoEmpleado;
    }

    public int getIdEmpelado() {
        return idEmpelado;
    }

    public void setIdEmpelado(int idEmpelado) {
        this.idEmpelado = idEmpelado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoPaternoEmpleado() {
        return apellidoPaternoEmpleado;
    }

    public void setApellidoPaternoEmpleado(String apellidoPaternoEmpleado) {
        this.apellidoPaternoEmpleado = apellidoPaternoEmpleado;
    }

    public String getApellidoMaternoEmpleado() {
        return apellidoMaternoEmpleado;
    }

    public void setApellidoMaternoEmpleado(String apellidoMaternoEmpleado) {
        this.apellidoMaternoEmpleado = apellidoMaternoEmpleado;
    }

    public double getSalarioEmpleado() {
        return salarioEmpleado;
    }

    public void setSalarioEmpleado(double salarioEmpleado) {
        this.salarioEmpleado = salarioEmpleado;
    }

    public String getDniEmpleado() {
        return dniEmpleado;
    }

    public void setDniEmpleado(String dniEmpleado) {
        this.dniEmpleado = dniEmpleado;
    }

    public String getCargoEmpleado() {
        return cargoEmpleado;
    }

    public void setCargoEmpleado(String cargoEmpleado) {
        this.cargoEmpleado = cargoEmpleado;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getEstadoEmpleado() {
        return estadoEmpleado;
    }

    public void setEstadoEmpleado(String estadoEmpleado) {
        this.estadoEmpleado = estadoEmpleado;
    }
}
