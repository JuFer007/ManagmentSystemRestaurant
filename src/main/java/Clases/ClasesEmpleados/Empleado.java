package Clases.ClasesEmpleados;

import java.util.Random;

public class Empleado {
    private int idEmpleado;
    private String dniEmpleado;
    private String codigoEmpleado;
    private String nombreEmpleado;
    private String apellidoPaternoEmpleado;
    private String apellidoMaternoEmpleado;
    private String estadoEmpleado;
    private int horasTrabajo;
    private double salarioEmpleado;
    private String cargoEmpleado;

    public Empleado() {
    }

    public Empleado(String dniEmpleado, String codigoEmpleado, String nombreEmpleado, String apellidoPaternoEmpleado, String apellidoMaternoEmpleado, String estadoEmpleado, int horasTrabajo, double salarioEmpelado, String cargoEmpleado) {
        this.dniEmpleado = dniEmpleado;
        this.codigoEmpleado = codigoEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoPaternoEmpleado = apellidoPaternoEmpleado;
        this.apellidoMaternoEmpleado = apellidoMaternoEmpleado;
        this.estadoEmpleado = estadoEmpleado;
        this.horasTrabajo = horasTrabajo;
        this.salarioEmpleado = salarioEmpelado;
        this.cargoEmpleado = cargoEmpleado;
    }

    public int getIdEmpelado() {
        return idEmpleado;
    }

    public void setIdEmpelado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getDniEmpleado() {
        return dniEmpleado;
    }

    public void setDniEmpleado(String dniEmpleado) {
        this.dniEmpleado = dniEmpleado;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
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

    public String getEstadoEmpleado() {
        return estadoEmpleado;
    }

    public void setEstadoEmpleado(String estadoEmpleado) {
        this.estadoEmpleado = estadoEmpleado;
    }

    public int getHorasTrabajo() {
        return horasTrabajo;
    }

    public void setHorasTrabajo(int horasTrabajo) {
        this.horasTrabajo = horasTrabajo;
    }

    public double getSalarioEmpleado() {
        return salarioEmpleado;
    }

    public void setSalarioEmpleado(double salarioEmpleado) {
        this.salarioEmpleado = salarioEmpleado;
    }

    public String getCargoEmpleado() {
        return cargoEmpleado;
    }

    public void setCargoEmpleado(String cargoEmpleado) {
        this.cargoEmpleado = cargoEmpleado;
    }

    public String generarCodigo(){
        Random random = new Random();
        int numero = random.nextInt(100);
        return String.format("EMP0%02d", numero);
    }
}
