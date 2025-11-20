package com.app.SystemRestaurant.Repository;
import com.app.SystemRestaurant.Model.ClasesGestion.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    long count();
    @Query("SELECT SUM(p.totalPedido) FROM Pedido p")
    Double totalGanancias();
    @Query("SELECT FUNCTION('MONTH', p.fecha) as mes, SUM(p.totalPedido) as total " +
    "FROM Pedido p GROUP BY FUNCTION('MONTH', p.fecha) ORDER BY FUNCTION('MONTH', p.fecha)")
    List<Object[]> ingresosPorMes();
    Pedido findByCodigoPedido(String codigoPedido);
}
