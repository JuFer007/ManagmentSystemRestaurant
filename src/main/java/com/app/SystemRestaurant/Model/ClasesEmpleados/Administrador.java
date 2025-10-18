package com.app.SystemRestaurant.Model.ClasesEmpleados;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "administrador")

public class Administrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAdministrador;

    @OneToOne
    @JoinColumn(name = "idEmpleado")
    private Empleado empleado;

    @Column(unique = true)
    private String telefono;

    @Column(unique = true)
    private String correoElectronico;
}
