package Clases.ClasesEmpleados;

public class Cocinero {
    private int idCocinero;
    private int idEmpleado;
    private String especialidad;
    private String experencia;
    private String turno;

    public Cocinero(int idEmpleado, String especialidad, String experencia, String turno) {
        this.idEmpleado = idEmpleado;
        this.especialidad = especialidad;
        this.experencia = experencia;
        this.turno = turno;
    }

    public int getIdCocinero() {
        return idCocinero;
    }

    public void setIdCocinero(int idCocinero) {
        this.idCocinero = idCocinero;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getExperencia() {
        return experencia;
    }

    public void setExperencia(String experencia) {
        this.experencia = experencia;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}
