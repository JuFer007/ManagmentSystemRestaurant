package com.app.SystemRestaurant.Repository;
import com.app.SystemRestaurant.Model.ClasesGestion.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PlatoRepository extends JpaRepository<Plato, Integer> {
    long count();

    @Query(value = "SELECT p.nombre_plato, SUM(d.cantidad) " +
            "FROM detalle_pedido d INNER JOIN plato p ON d.id_plato = p.id_plato " +
            "GROUP BY p.nombre_plato " +
            "ORDER BY SUM(d.cantidad) DESC",
            nativeQuery = true)
    List<Object[]> platosMayormentVendidos();
}
