package com.app.SystemRestaurant.Model.ClasesGestion;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "plato")

public class Plato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPlato;

    @Column(unique = true, nullable = false)
    private String nombrePlato;
    private Double precioPlato;
    private String disponibilidad;
    private String imagenURL;
}
