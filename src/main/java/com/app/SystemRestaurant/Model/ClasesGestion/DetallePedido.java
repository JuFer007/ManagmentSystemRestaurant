package com.app.SystemRestaurant.Model.ClasesGestion;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Pedido idPedido;

    @ManyToOne
    @JoinColumn(name = "idPlato")
    private Plato idPlato;

    private int cantidad;
    private double subTotal;
}
