package Clases.ClasesEmpleados;

import java.util.Random;

public class Mesero extends Empleado {
    private int idMesero;
    private int idEmpleado;
    private String turnoTrabajo;
    private String codigoMesero;

    public Mesero() {
    }

    public Mesero(String dniEmpleado, String codigoEmpleado, String nombreEmpleado, String apellidoPaternoEmpleado, String apellidoMaternoEmpleado, String estadoEmpleado, int horasTrabajo, double salarioEmpelado, String cargoEmpleado, int idEmpleado, String turnoTrabajo, String codigoMesero) {
        super(dniEmpleado, codigoEmpleado, nombreEmpleado, apellidoPaternoEmpleado, apellidoMaternoEmpleado, estadoEmpleado, horasTrabajo, salarioEmpelado, cargoEmpleado);
        this.idEmpleado = idEmpleado;
        this.turnoTrabajo = turnoTrabajo;
        this.codigoMesero = codigoMesero;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getTurnoTrabajo() {
        return turnoTrabajo;
    }

    public void setTurnoTrabajo(String turnoTrabajo) {
        this.turnoTrabajo = turnoTrabajo;
    }

    public String getCodigoMesero() {
        return codigoMesero;
    }

    public void setCodigoMesero(String codigoMesero) {
        this.codigoMesero = codigoMesero;
    }

    public String generarCodigo(){
        Random random = new Random();
        int numero = random.nextInt(100);
        return String.format("MES0%02d", numero);
    }
}
