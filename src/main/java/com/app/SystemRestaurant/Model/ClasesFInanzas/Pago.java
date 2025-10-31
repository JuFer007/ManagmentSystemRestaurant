package com.app.SystemRestaurant.Model.ClasesFInanzas;
import com.app.SystemRestaurant.Model.ClasesGestion.Pedido;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "pago")

public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPago;

    @OneToOne
    @JoinColumn(name = "idPedido")
    @JsonIgnore
    private Pedido idPedido;
    private double montoPago;
    private String metodoPago;
    private LocalDate fechaPago;
    private String estadoPago;
}
