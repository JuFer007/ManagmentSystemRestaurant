package com.app.SystemRestaurant.Repository;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Mesero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeseroRepository extends JpaRepository<Mesero, Integer> {
    Optional<Mesero> EncontrarMeseroById(String idMesero);
}
