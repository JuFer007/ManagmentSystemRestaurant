package Clases.ClasesEmpleados;

public class Mesero extends Empleado {
    private int idRepartidor;
    private int idMesaAsignada;
    private String turno;
    private String codigoMesero;

    public Mesero(String nombreEmpleado, String apellidoPaternoEmpleado, String apellidoMaternoEmpleado, double salarioEmpleado, String dniEmpleado,
    String cargoEmpleado, String correoElectronico, String numeroTelefono, String estadoEmpleado, int idMesaAsignada, String turno, String codigoMesero) {
        super(nombreEmpleado, apellidoPaternoEmpleado, apellidoMaternoEmpleado, salarioEmpleado, dniEmpleado, cargoEmpleado, correoElectronico, numeroTelefono, estadoEmpleado);
        this.idMesaAsignada = idMesaAsignada;
        this.turno = turno;
        this.codigoMesero = codigoMesero;
    }

    public int getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(int idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public int getIdMesaAsignada() {
        return idMesaAsignada;
    }

    public void setIdMesaAsignada(int idMesaAsignada) {
        this.idMesaAsignada = idMesaAsignada;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getCodigoMesero() {
        return codigoMesero;
    }

    public void setCodigoMesero(String codigoMesero) {
        this.codigoMesero = codigoMesero;
    }
}
