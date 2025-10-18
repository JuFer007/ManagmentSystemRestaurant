package com.app.SystemRestaurant.Model.ClasesEmpleados;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    @OneToOne
    @JoinColumn(name = "idEmpleado", nullable = false)
    private Empleado idEmpleado;

    @Column(unique = true)
    private String nombreUsuario;
    private String contraseñaUsuario;
}
