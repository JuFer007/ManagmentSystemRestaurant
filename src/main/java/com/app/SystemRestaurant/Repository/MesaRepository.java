package com.app.SystemRestaurant.Repository;

import com.app.SystemRestaurant.Model.ClasesGestion.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MesaRepository extends JpaRepository<Mesa, Integer> {
}