package com.app.SystemRestaurant.Model.ClasesFInanzas;
import com.app.SystemRestaurant.Model.ClasesGestion.Pedido;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Date;

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
    private Pedido idPedido;
    private double montoPago;
    private String metodoPago;
    private Date fechaPago;
}
