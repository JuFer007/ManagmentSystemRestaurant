package com.app.SystemRestaurant.Model.ClasesGestion;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Cliente;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Mesero;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "pedido")

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPedido;

    @OneToOne
    @JoinColumn(name = "idMesa")
    private Mesa idMesa;

    @ManyToOne
    @JoinColumn(name = "idMesero")
    private Mesero idMesero;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente idCliente;

    @OneToMany(mappedBy = "idPedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DetallePedido> detalles;

    private Date fecha;
    private String estadoPedido;
    private String codigoPedido;
    private double totalPedido;
}