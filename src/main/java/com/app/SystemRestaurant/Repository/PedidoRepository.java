package com.app.SystemRestaurant.Repository;
import com.app.SystemRestaurant.Model.ClasesGestion.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
