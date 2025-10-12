package com.app.SystemRestaurant.Model.ClasesEmpleados;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mesero")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Mesero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMesero;

    @OneToOne
    @JoinColumn(name = "idEmpleado")
    private Empleado empleado;
    private String turnoTrabajo;
}
