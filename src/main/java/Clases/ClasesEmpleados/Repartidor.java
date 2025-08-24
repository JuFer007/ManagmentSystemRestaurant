package Clases.ClasesEmpleados;

public class Repartidor extends Empleado{
    private int idRepartidor;
    private String codigoRepartidor;
    private String zonaRepartidor;
    private String placaVehiculo;

    public Repartidor(String nombreEmpleado, String apellidoPaternoEmpleado, String apellidoMaternoEmpleado, double salarioEmpleado, String dniEmpleado, String cargoEmpleado,
    String correoElectronico, String numeroTelefono, String estadoEmpleado, String codigoRepartidor, String zonaRepartidor, String placaVehiculo) {
        super(nombreEmpleado, apellidoPaternoEmpleado, apellidoMaternoEmpleado, salarioEmpleado, dniEmpleado, cargoEmpleado, correoElectronico, numeroTelefono, estadoEmpleado);
        this.codigoRepartidor = codigoRepartidor;
        this.zonaRepartidor = zonaRepartidor;
        this.placaVehiculo = placaVehiculo;
    }

    public int getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(int idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public String getCodigoRepartidor() {
        return codigoRepartidor;
    }

    public void setCodigoRepartidor(String codigoRepartidor) {
        this.codigoRepartidor = codigoRepartidor;
    }

    public String getZonaRepartidor() {
        return zonaRepartidor;
    }

    public void setZonaRepartidor(String zonaRepartidor) {
        this.zonaRepartidor = zonaRepartidor;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }
}
