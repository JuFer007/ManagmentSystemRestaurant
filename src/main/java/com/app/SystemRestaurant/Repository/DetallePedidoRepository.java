package com.app.SystemRestaurant.Repository;
import com.app.SystemRestaurant.Model.ClasesGestion.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
    List<DetallePedido> findByIdPedido_IdPedido(int idPedido);
}
