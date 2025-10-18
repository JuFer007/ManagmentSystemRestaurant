package com.app.SystemRestaurant.Model.ClasesEmpleados;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name = "empleado")
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEmpleado;

    @Column(unique = true)
    private String dniEmpleado;

    @Column(unique = true)
    private String codigoEmpleado;
    private String nombreEmpleado;
    private String apellidoPaternoEmpleado;
    private String apellidoMaternoEmpleado;
    private String estadoEmpleado;
    private int horasTrabajo;
    private double salarioEmpleado;
    private String cargoEmpleado;
}
