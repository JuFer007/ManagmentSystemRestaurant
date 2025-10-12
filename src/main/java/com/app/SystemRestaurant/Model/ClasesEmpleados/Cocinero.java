package com.app.SystemRestaurant.Model.ClasesEmpleados;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cocinero")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Cocinero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCocinero;

    @OneToOne
    @JoinColumn(name = "idEmpleado")
    private Empleado empleado;
    private String especialidad;
}
