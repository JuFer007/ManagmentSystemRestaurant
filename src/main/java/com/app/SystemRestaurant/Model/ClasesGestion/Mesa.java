package com.app.SystemRestaurant.Model.ClasesGestion;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Mesero;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "mesa")

public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMesa;
    private int numeroMesa;
    private int capacidad;
}
