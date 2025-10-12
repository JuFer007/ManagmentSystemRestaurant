package com.app.SystemRestaurant.Model.ClasesEmpleados;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cliente")

public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCliente;

    private String dniCliente;
    private String nombreCliente;
    private String apellidosCliente;
}
