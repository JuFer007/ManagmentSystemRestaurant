package com.app.SystemRestaurant.Model.ClasesGestion;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "detallePedido")

public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDetallePedido;

    @ManyToOne
    @JoinColumn(name = "idPedido")
    private Pedido idPedido;

    @ManyToOne
    @JoinColumn(name = "idPlato")
    private Plato idPlato;

    private int cantidad;
    private double subTotal;
}
