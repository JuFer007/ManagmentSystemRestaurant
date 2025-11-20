package com.app.SystemRestaurant.Model.ClasesEmpleados;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

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

    @Column(unique = true)
    private String dniCliente;

    private String nombreCliente;
    private String apellidosCliente;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(dniCliente, cliente.dniCliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dniCliente);
    }
}
